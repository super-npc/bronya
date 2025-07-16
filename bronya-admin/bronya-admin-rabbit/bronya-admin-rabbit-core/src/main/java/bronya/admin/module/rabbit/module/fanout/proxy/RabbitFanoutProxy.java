package bronya.admin.module.rabbit.module.fanout.proxy;

import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanout;
import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanout.RabbitFanoutExpandFields;
import bronya.admin.module.rabbit.module.fanout.repository.RabbitFanoutRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitFanoutProxy extends DataProxy<RabbitFanout> {
    private final RabbitFanoutRepository rabbitFanoutRepository;

    @Override
    public void table(Map<String, Object> map) {
        RabbitFanout rabbitFanout = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), RabbitFanout.class);
        RabbitFanoutExpandFields rabbitFanoutExpandFields = new RabbitFanoutExpandFields();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(RabbitFanout.class, rabbitFanoutExpandFields));
    }
}
