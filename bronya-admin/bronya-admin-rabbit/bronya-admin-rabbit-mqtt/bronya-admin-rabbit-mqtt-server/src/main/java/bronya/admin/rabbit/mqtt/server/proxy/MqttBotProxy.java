//package bronya.admin.rabbit.mqtt.server.proxy;
//
//import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
//import bronya.admin.rabbit.mqtt.core.MqttCli;
//import bronya.shared.module.util.Md;
//import bronya.admin.rabbit.mqtt.server.callback.MqttBotCallback;
//import bronya.admin.rabbit.mqtt.server.domain.MqttBot;
//import bronya.admin.rabbit.mqtt.server.domain.MqttBot.MqttBotExt;
//import bronya.admin.rabbit.mqtt.server.repository.MqttBotRepository;
//import bronya.core.base.dto.DataProxy;
//import bronya.admin.rabbit.mqtt.server.service.MqttBotBizService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.steppschuh.markdowngenerator.text.quote.Quote;
//import org.dromara.hutool.core.bean.BeanUtil;
//import org.eclipse.paho.mqttv5.common.MqttMessage;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MqttBotProxy extends DataProxy<MqttBot> {
//    private final MqttBotRepository mqttBotRepository;
//    private final MqttBotBizService mqttBotBizService;
//
//    @Override
//    public void afterAdd(MqttBot mqttBot) {
//        super.afterAdd(mqttBot);
//        mqttBotBizService.createInstance();
//    }
//
//    @Override
//    public void afterUpdate(MqttBot mqttBot) {
//        super.afterUpdate(mqttBot);
//        mqttBotBizService.createInstance();
//    }
//
//    @Override
//    public void table(Map<String, Object> map) {
//        MqttBot mqttBot = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), MqttBot.class);
//        MqttBotExt mqttBotExt = new MqttBotExt();
//        Md md = new Md();
//        Optional<MqttCli> register = mqttBotBizService.getInstance(mqttBot);
//        md.appendLn(new Quote("注册"));
//        md.appendLn(STR." - 实例化： \{Md.emojiBool(register.isPresent())}");
//        if (register.isPresent()) {
//            MqttCli mqttCli = register.get();
//            md.appendLn(STR." - 连接状态: \{Md.emojiBool(mqttCli.getClient().isConnected())}");
//                md.appendLn(STR." - 订阅: \{mqttCli.getTopics()}");
//                    md.appendLn(this.printMqttCli(mqttCli));
//        }
//        mqttBotExt.setRunning(md.toString());
//        // 配置拓展属性信息
//        map.putAll(BronyaAdminBaseAmisUtil.obj2map(MqttBot.class, mqttBotExt));
//    }
//
//    private String printMqttCli(MqttCli mqttCli) {
//        Md md = new Md();
//        md.appendLn(new Quote("数据"));
//        if (mqttCli.getCallback() instanceof MqttBotCallback back) {
//            Map<String, MqttMessage> lastMessages = back.getLastMessages();
//            lastMessages.forEach((k, v) -> {
//                md.appendLn(STR." - \{k} : \{new String(v.getPayload())}");
//            });
//        }
//        return md.toString();
//    }
//}
