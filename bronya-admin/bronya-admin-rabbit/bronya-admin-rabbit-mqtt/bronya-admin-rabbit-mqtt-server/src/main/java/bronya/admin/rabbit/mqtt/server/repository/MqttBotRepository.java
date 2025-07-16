package bronya.admin.rabbit.mqtt.server.repository;

import java.util.Optional;

import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.rabbit.mqtt.server.domain.MqttBot;
import bronya.admin.rabbit.mqtt.server.mapper.MqttBotMapper;
import lombok.RequiredArgsConstructor;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MqttBotRepository extends BaseRepository<MqttBotMapper, MqttBot> {

    public boolean createDevice(String deviceId, MqttLiveStatus status) {
        MqttBot mqttBot = new MqttBot();
        mqttBot.setDeviceId(deviceId);
        mqttBot.setStatus(status);
        mqttBot.setTopic("");
        boolean save = this.save(mqttBot);
        log.info("创建新设备:{}", mqttBot);
        return save;
    }

    public Optional<MqttBot> findByDeviceId(String deviceId) {
        LambdaQueryWrapper<MqttBot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MqttBot::getDeviceId, deviceId);
        return this.getOneOpt(queryWrapper);
    }

    public boolean updateStatus(String deviceId, MqttLiveStatus status) {
        LambdaUpdateWrapper<MqttBot> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MqttBot::getDeviceId, deviceId);

        MqttBot mqttBot = new MqttBot();
        mqttBot.setStatus(status);
        boolean update = this.update(mqttBot, updateWrapper);
        log.info("更新状态:{} = {}", deviceId, status);
        return update;
    }
}
