package bronya.admin.module.db.threadpool.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.threadpool.MonitoredThreadPoolExecutor;
import bronya.admin.module.db.threadpool.domain.SysThreadPool;
import bronya.admin.module.db.threadpool.repository.SysThreadPoolRepository;
import bronya.admin.module.db.threadpool.service.SysThreadPoolService;
import bronya.shared.module.common.type.ActivationStatus;
import bronya.shared.module.common.type.ProcessStatus;
import bronya.core.base.dto.DataProxy;
import bronya.shared.module.util.Md;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.text.quote.Quote;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.SystemUtil;
import org.dromara.hutool.extra.management.oshi.CpuInfo;
import org.dromara.hutool.extra.management.oshi.OshiUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysThreadPoolProxy extends DataProxy<SysThreadPool> {
    private final SysThreadPoolRepository sysThreadPoolRepository;
    private final SysThreadPoolService threadPoolService;

    @Override
    public void afterUpdate(SysThreadPool sysThreadPool) {
        super.afterUpdate(sysThreadPool);
        MonitoredThreadPoolExecutor memoryPoolByPrefixName =
            threadPoolService.getMemoryPoolByPrefixName(sysThreadPool.getPrefixName());
        memoryPoolByPrefixName.setCorePoolSize(sysThreadPool.getCorePoolSize());
        memoryPoolByPrefixName.setMaximumPoolSize(sysThreadPool.getMaximumPoolSize());
        log.info("更新线程池配置完成:{}", sysThreadPool.getPrefixName());
    }

    @Override
    public void table(Map<String, Object> map) {
        SysThreadPool sysThreadPool = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysThreadPool.class);
        SysThreadPool.SysThreadPoolExt sysThreadPoolExt = new SysThreadPool.SysThreadPoolExt();
        sysThreadPoolExt.setStatus(ActivationStatus.DISABLE);
        MonitoredThreadPoolExecutor memoryPoolByPrefixName =
            threadPoolService.getMemoryPoolByPrefixName(sysThreadPool.getPrefixName());
        if (memoryPoolByPrefixName != null) {
            sysThreadPoolExt.setStatus(ActivationStatus.ENABLE);
            if (memoryPoolByPrefixName.getCompletedTaskCount() > 0) {
                sysThreadPoolExt
                    .setProcess(memoryPoolByPrefixName.getCompletedTaskCount() == memoryPoolByPrefixName.getTaskCount()
                        ? ProcessStatus.SUCCESS : ProcessStatus.RUNNING);
            }
        }
//        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        sysThreadPoolExt.setRunning(this.running(sysThreadPool, memoryPoolByPrefixName));

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysThreadPool.class, sysThreadPoolExt));
    }

    private String running(SysThreadPool sysThreadPool, MonitoredThreadPoolExecutor memoryPoolByPrefixName) {
        if (memoryPoolByPrefixName == null) {
            return "";
        }
        Md md = new Md();
        md.appendLn(new Quote("基础信息"));
        md.appendLn(STR." - 名称: \{memoryPoolByPrefixName.getRemark()}");

        md.appendLn(new Quote("线程池黄金配置公式"));

//        md.appendLn(STR." - 推荐corePoolSize: 核心数: \{cpuInfo.getCpuNum()} * 2 = \{cpuInfo.getCpuNum() * 2}");
//        md.appendLn(STR." - 推荐maxPoolSize: corePoolSize: \{memoryPoolByPrefixName.getCorePoolSize()} * 4 = \{memoryPoolByPrefixName.getCorePoolSize() * 4}");

        md.appendLn(new Quote("实时线程数据"));
        md.appendLn(STR." - 队列模型: \{memoryPoolByPrefixName.getQueue().getClass().getSimpleName()}");
        md.appendLn(STR." - 拒绝策略: \{memoryPoolByPrefixName.getRejectedExecutionHandler().getClass().getSimpleName()}");
        md.appendLn(STR." - 实际次数: \{memoryPoolByPrefixName.getCompletedTaskCount() / 2}");
        md.appendLn(STR." - corePoolSize: \{memoryPoolByPrefixName.getCorePoolSize()}");
        md.appendLn(STR." - poolSize: \{memoryPoolByPrefixName.getPoolSize()}");
        md.appendLn(STR." - maxPoolSize: \{memoryPoolByPrefixName.getMaximumPoolSize()}");
        md.appendLn(STR." - 队列容量(待执行): \{memoryPoolByPrefixName.getQueue().size()}");
        md.appendLn(STR." - 活跃量(执行中): \{memoryPoolByPrefixName.getActiveCount()}");
        md.appendLn(STR." - 已执行+未执行(池): \{memoryPoolByPrefixName.getTaskCount()}");
        md.appendLn(STR." - 已完成(池): \{memoryPoolByPrefixName.getCompletedTaskCount()}");
        md.appendLn(STR." - 存活: \{memoryPoolByPrefixName.getKeepAliveTime(TimeUnit.SECONDS)} 秒");

        Md mdErr = new Md();
        if (!sysThreadPool.getCorePoolSize().equals(memoryPoolByPrefixName.getCorePoolSize())) {
            mdErr.appendLn(STR." - 线程核心线程:\{memoryPoolByPrefixName.getCorePoolSize()} ");
        }
        if (!sysThreadPool.getMaximumPoolSize().equals(memoryPoolByPrefixName.getMaximumPoolSize())) {
            mdErr.appendLn(STR." - 线程最大线程:\{memoryPoolByPrefixName.getMaximumPoolSize()} ");
        }
        if (StrUtil.isNotBlank(mdErr.toString())) {
            md.appendLn(new Quote("异常数据"));
            md.appendLn(md.toString());
        }
        return md.toString();
    }
}
