package bronya.admin.module.rabbit.module.delayed.proxy;

import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayed;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayed.RabbitDelayedExpandFields;
import bronya.admin.module.rabbit.module.delayed.repository.RabbitDelayedRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitDelayedProxy extends DataProxy<RabbitDelayed> {
    private final RabbitDelayedRepository rabbitDelayedRepository;

    @Override
    public void table(Map<String, Object> map) {
        RabbitDelayed rabbitDelayed = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), RabbitDelayed.class);
        RabbitDelayedExpandFields rabbitDelayedExpandFields = new RabbitDelayedExpandFields();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(RabbitDelayed.class, rabbitDelayedExpandFields));
    }
}
