package bronya.admin.module.rabbit.module.work;

import com.rabbitmq.client.Delivery;

import bronya.admin.module.rabbit.dto.MqDtoBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class IWorkMq<T> {
    public abstract void deliverCallback(String consumerTag, Delivery delivery, MqDtoBase<T> t);

    public void deliverCallbackException(MqDtoBase<T> t, Exception e) {
        log.error("回调方法deliverCallback,处理结果异常", e);
    }

    /**
     * 处理取消的回调
     */
    public void cancelCallback(String consumerTag) {

    }
}
