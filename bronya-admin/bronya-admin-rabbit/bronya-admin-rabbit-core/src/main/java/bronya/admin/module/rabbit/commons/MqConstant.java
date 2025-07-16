package bronya.admin.module.rabbit.commons;

import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import bronya.admin.module.rabbit.module.fanout.IFanoutMq;
import bronya.admin.module.rabbit.module.work.IWorkMq;

public interface MqConstant {
    /**
     * mq 延迟注解
     */
    Set<Class<?>> RABBIT_DELAYED = Sets.newHashSet();

    /**
     * mq, 订阅发布
     * <p>
     * 发送实体/多个接收实现类
     */
    Multimap<Class<?>, IFanoutMq<?>> RABBIT_FANOUT = HashMultimap.create();

    /**
     * mq 工作模式
     */
    Multimap<Class<?>, IWorkMq<?>> RABBIT_WORK = HashMultimap.create();
}
