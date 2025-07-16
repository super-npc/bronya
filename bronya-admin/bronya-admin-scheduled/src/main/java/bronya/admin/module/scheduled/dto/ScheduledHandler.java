package bronya.admin.module.scheduled.dto;

import bronya.admin.module.scheduled.type.TriggerType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ScheduledHandler {

    /**
     * 任务处理类
     *
     * @param param 页面配置参数
     * @return 执行结果
     */
    public abstract Object exec(TriggerType triggerType, String param);

    public abstract ScheduledConfig config();

    /**
     * 成功事件回调
     *
     * @param result 执行结果
     */
    public void onSuccess(Object result) {
//        log.info(STR."执行成功,结果:\{result}");
    }

    /**
     * 失败事件回调
     *
     * @param throwable 异常对象
     */
    public void onFailure(Throwable throwable) {
//        log.error("执行失败", throwable);
    }

    public void onFinally(){

    }

}