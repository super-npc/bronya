package bronya.admin.module.rabbit.module;

import org.springframework.stereotype.Service;

import bronya.admin.module.rabbit.module.dead.impl.RabbitEventDead;
import bronya.admin.module.rabbit.module.delayed.impl.RabbitEventDelayed;
import bronya.admin.module.rabbit.module.fanout.impl.RabbitEventFanout;
import bronya.admin.module.rabbit.module.rpc.impl.RabbitEventRpc;
import bronya.admin.module.rabbit.module.work.impl.RabbitEventWork;
import bronya.admin.module.rabbit.type.MsgType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitEventSwitch {
    private final RabbitEventWork rabbitEventWork;
    private final RabbitEventDelayed rabbitEventDelayed;
    private final RabbitEventFanout rabbitEventFanout;
    private final RabbitEventRpc rabbitEventRpc;
    private final RabbitEventDead rabbitEventDead;

    public IRabbitEvent switchService(final MsgType msgType) {
        return switch (msgType) {
            case delayed -> rabbitEventDelayed;
            case work -> rabbitEventWork;
            case fanout -> rabbitEventFanout;
            case rpc -> rabbitEventRpc;
            case dead -> rabbitEventDead;
        };
    }
}
