package bronya.admin.rabbit.mqtt.core;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.thread.ThreadFactoryBuilder;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

import com.google.common.collect.Sets;

import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import bronya.admin.rabbit.mqtt.core.util.MsgStatusUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MqttCli {
    private final String host;
    private final String deviceId;
    private final String username;
    private final String password;
    private final int timeout;
    private final int keepAlive;
    private final MqttCallback callback;
    private final MqttActionListener listener;

    private ScheduledExecutorService scheduler;

    private final String topicStatus = MqttServerTopic.LIVE.getTopic();
    private final Set<String> topics = Sets.newCopyOnWriteArraySet(); // 订阅的主题
    private final Set<String> topicsSubscribed = Sets.newCopyOnWriteArraySet(); // 已经订阅的主题
    private MqttAsyncClient client;

    @SneakyThrows
    public void createMqttCliInstance() {
        if (client != null) {
            // 创建过一次客户端，通过调用disconnect()方法关闭客户端，但是实例还在，重启连接并重新扫描订阅主题
            log.info("MQTT客户端已经创建,无法重新创建");
            if (!client.isConnected()) {
                client.connect();
                log.info("MQTT客户端重新连接");
            }
            if (scheduler.isShutdown()) {
                scheduler = new ScheduledThreadPoolExecutor(5,
                    new ThreadFactoryBuilder().setNamePrefix("scheduler-mqtt-topic-%d").build());
                scheduler.scheduleAtFixedRate(this::reSubTopics, 3, 5, TimeUnit.SECONDS);
                log.info("启动定时器");
            }
            return;
        }
        scheduler = new ScheduledThreadPoolExecutor(5,
            new ThreadFactoryBuilder().setNamePrefix("scheduler-mqtt-topic-%d").build());
        // client = new MqttClient(host, clientID, new MemoryPersistence());
        client = new MqttAsyncClient(host, deviceId, new MemoryPersistence());
        MqttConnectionOptions options = new MqttConnectionOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 把配置里的 cleanSession 设为false，客户端掉线后 服务器端不会清除session，
        // 当重连后可以接收之前订阅主题的消息。当客户端上线后会接受到它离线的这段时间的消息
        options.setCleanStart(false);
        options.setUserName(username);
        options.setPassword(password.getBytes(Charset.defaultCharset()));
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepAlive);
        options.setConnectionTimeout(60); // 设置为60秒
        options.setAutomaticReconnect(true); // 启用自动重连

        // 立下断开遗嘱
        options.setWill(topicStatus, MsgStatusUtil.getStatusMsg(deviceId, MqttLiveStatus.offline)); // 遗嘱

        // 设置回调类
        client.setCallback(callback);
        org.springframework.util.Assert.notNull(client, "MQTT客户端为空");
        IMqttToken connect = client.connect(options, null, listener);

        // 开启定时器监控订阅的变化
        scheduler.scheduleAtFixedRate(this::reSubTopics, 3, 5, TimeUnit.SECONDS);
    }

    @SneakyThrows
    public void disconnect() {
        scheduler.shutdown();
        client.disconnect(); // 实例是不释放的，有可能手动启动
        log.info("断开连接");
    }

    @SneakyThrows
    public IMqttToken publish(int qos, boolean retained, String topic, String msg) {
        MqttMessage mqttMessage = new MqttMessage(msg.getBytes(), qos, retained, new MqttProperties());
        IMqttToken publish = client.publish(topic, mqttMessage, null, new MqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                log.info("发送成功");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                log.warn("发送失败", exception);
            }
        });
        publish.waitForCompletion();
        return publish;
    }

    // 查漏补缺
    public void forkTopic(Set<String> topic) {
        Collection<String> subtractAdd = CollUtil.subtract(topic, topics);// 新增的
        this.addTopic(Sets.newCopyOnWriteArraySet(subtractAdd));

        Collection<String> subtractRemove = CollUtil.subtract(topics, topic);// 删除
        for (String s : subtractRemove) {
            this.removeTopic(s);
        }
        log.info("新增订阅:{},删除订阅:{}", subtractAdd, subtractRemove);
    }

    public void addTopic(String topic) {
        topics.add(topic);
    }

    public void addTopic(Set<String> topic) {
        topics.addAll(topic);
    }

    public void removeTopic(String topic) {
        topics.remove(topic);
    }

    private void reSubTopics() {
        log.debug("检查订阅");
        // 正向订阅
        for (String topic : topics) {
            this.subscribe(topic);
        }
        // 方向订阅, 已订阅，但用户删除订阅
        for (String subscribed : topicsSubscribed) {
            if (!topics.contains(subscribed)) {
                try {
                    client.unsubscribe(subscribed);
                    topicsSubscribed.remove(subscribed);
                } catch (MqttException e) {
                    log.warn("取消订阅失败", e);
                }
            }
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     */
    public void subscribe(String topic) {
        subscribe(topic, 0);
    }

    /**
     * 订阅某个主题
     *
     * @param topic 主题名
     */
    @SneakyThrows
    public void subscribe(String topic, int qos) {
        if (client == null || !client.isConnected()) {
            return;
        }
        if (topicsSubscribed.contains(topic)) {
            return;
        }
        client.subscribe(topic, qos);
        topicsSubscribed.add(topic);
        log.info("订阅主题:{}", topic);
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic 主题名
     * @param pushMessage 消息
     * @return 发送结果
     */
    public IMqttToken publish(String topic, String pushMessage) {
        return publish(0, false, topic, pushMessage);
    }

    public MqttCli(String deviceId, String host, String username, String password, int timeout, int keepAlive,
        MqttCallback callback, MqttActionListener listener) {
        this.host = host;
        this.deviceId = deviceId;
        this.username = username;
        this.password = password;
        this.timeout = timeout;
        this.keepAlive = keepAlive;
        this.callback = callback;
        this.listener = listener;
    }

    public MqttCli(MqttCfg mqttCfg, MqttCallback callback, MqttActionListener listener) {
        this.host = mqttCfg.getHost();
        this.deviceId = mqttCfg.getDeviceId();
        this.username = mqttCfg.getUserName();
        this.password = mqttCfg.getPassword();
        this.timeout = mqttCfg.getTimeout();
        this.keepAlive = mqttCfg.getKeepAlive();
        this.callback = callback;
        this.listener = listener;
    }
}