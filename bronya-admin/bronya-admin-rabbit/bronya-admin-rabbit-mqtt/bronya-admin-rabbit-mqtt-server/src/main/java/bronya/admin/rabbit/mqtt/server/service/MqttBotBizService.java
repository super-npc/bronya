//package bronya.admin.rabbit.mqtt.server.service;
//
//import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
//import bronya.admin.rabbit.mqtt.core.MqttCli;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import bronya.admin.rabbit.mqtt.server.callback.MqttBotCallback;
//import bronya.admin.rabbit.mqtt.server.callback.MqttServerListener;
//import bronya.admin.rabbit.mqtt.server.domain.MqttBot;
//import bronya.admin.rabbit.mqtt.server.repository.MqttBotRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.dromara.hutool.core.text.StrPool;
//import org.dromara.hutool.core.text.split.SplitUtil;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MqttBotBizService {
//    private final MqttCfg mqttCfg;
//    private final MqttBotRepository mqttBotRepository;
//
//    public static final Map<String, MqttCli> mqttCliMap = Maps.newHashMap();
//
//    public Optional<MqttCli> getInstance(MqttBot bot) {
//        if (!mqttCliMap.containsKey(bot.getDeviceId())) {
//            return Optional.empty();
//        }
//        return Optional.of(mqttCliMap.get(bot.getDeviceId()));
//    }
//
//    public void createInstance() {
//        List<MqttBot> bots = mqttBotRepository.list();
//        for (MqttBot bot : bots) {
//            String clientId = bot.getDeviceId();
//            MqttCli mqttCli = mqttCliMap.get(clientId);
//            if(mqttCli == null){
//                mqttCli = new MqttCli(clientId, mqttCfg.getHost(), mqttCfg.getUserName(),
//                        mqttCfg.getPassword(), mqttCfg.getTimeout(),
//                        mqttCfg.getKeepAlive(),
//                        new MqttBotCallback(bot), new MqttServerListener());
//                mqttCliMap.put(clientId, mqttCli);
//                mqttCli.createMqttCliInstance();
//            }
//            // 统一刷新所有的订阅
//            mqttCli.forkTopic(Sets.newCopyOnWriteArraySet(SplitUtil.split(bot.getTopic(), StrPool.COMMA)));
//            log.info("register mqtt bot: {}", bot);
//        }
//    }
//}
