package bronya.admin.module.rabbit.module.delayed;


import bronya.admin.module.rabbit.type.MqRes;

public abstract class IDelayedMq<T> {
    /**
     * 到点执行
     */
    public abstract MqRes execute(T t);

    public void exception(T t, Exception e) {

    }

    public void finalExec(T t) {

    }
}