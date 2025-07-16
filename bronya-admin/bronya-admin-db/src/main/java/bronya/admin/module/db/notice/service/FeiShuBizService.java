package bronya.admin.module.db.notice.service;

import java.util.concurrent.TimeUnit;

import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.util.concurrent.FutureCallback;

import bronya.admin.module.db.notice.domain.FeiShuBot;
import bronya.admin.module.db.notice.domain.FeiShuRecord;
import bronya.admin.module.db.notice.repository.FeiShuRecordRepository;
import bronya.admin.module.db.threadpool.GlobalThreadPool;
import bronya.admin.module.db.threadpool.service.SysThreadPoolService;
import bronya.shared.module.common.type.LevelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.feishu.FeishuResp;
import npc.bulinke.external.module.feishu.FeishuRotClient;
import npc.bulinke.external.module.feishu.req.MarkdownReq;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeiShuBizService {
    private final FeiShuRecordRepository feiShuMsgRepository;
    private final SysThreadPoolService threadPoolService;

    public void send(FeiShuBot feiShu, LevelType levelType, MarkdownReq markdown) {
        threadPoolService.callback(GlobalThreadPool.FEI_SHU_SENDER, () -> {
            FeiShuRecord feiShuMsg = new FeiShuRecord();
            feiShuMsg.setLevelType(levelType);
            feiShuMsg.setMsg(JSONObject.toJSONString(markdown));
            feiShuMsg.setFeiShuId(feiShu.getId());
            try {
                FeishuRotClient feishuRotClient = new FeishuRotClient(feiShu.getToken(), feiShu.getSecret());
                FeishuResp feishuResp = feishuRotClient.sendMarkdownReq(markdown);
                feiShuMsg.setRes(JSONObject.toJSONString(feishuResp));
            } catch (Exception e) {
                feiShuMsg.setRes(StrUtil.format("发送异常", e.getMessage()));
            }
            feiShuMsgRepository.save(feiShuMsg);
            log.info("成功保存飞书消息: {}", feiShuMsg);
            ThreadUtil.sleep(10, TimeUnit.SECONDS); // 简单限流
            return feiShuMsg;
        }, new FutureCallback<>() {
            @Override
            public void onSuccess(FeiShuRecord feiShuMsg) {

            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                log.error("保存飞书消息失败", t);
            }
        });

    }
}
