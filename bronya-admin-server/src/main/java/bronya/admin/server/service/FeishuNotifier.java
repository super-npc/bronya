package bronya.admin.server.service;

import java.util.Arrays;

import bronya.admin.server.service.FeishuNotifier.NotifyBean.Status;
import bronya.shared.module.common.type.Color;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import npc.bulinke.external.module.feishu.FeishuRotClient;
import npc.bulinke.external.module.feishu.req.MarkdownReq;
import npc.bulinke.external.module.feishu.req.MarkdownReq.CardDTO.Elements;
import org.springframework.stereotype.Component;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FeishuNotifier extends AbstractStatusChangeNotifier {
    private final String[] ignoreChanges = new String[] {"UNKNOWN:UP", "DOWN:UP", "OFFLINE:UP"};

    private final FeishuRotClient feishuRotClient =
        new FeishuRotClient("2924ad1b-28f9-4792-a1db-4198cd5fbe92", "LnfMY3gHRoZJcZsfhgpNLg");

    @Data
    @AllArgsConstructor
    public static class NotifyBean {
        private Status status;

        @Data
        @AllArgsConstructor
        public static class Status {
            private String text;
            private Color color;
        }
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        log.info("收到事件：{},实例:{}", event, instance);
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent changedEvent) {
                String status = changedEvent.getStatusInfo().getStatus();
                NotifyBean notify = switch (status) {
                    // 健康检查没通过
                    case "DOWN" -> new NotifyBean(new Status("健康检查没通过", Color.纯红));
                    case "OFFLINE" -> new NotifyBean(new Status("服务离线", Color.纯红));
                    case "UP" -> new NotifyBean(new Status("服务上线", Color.绿宝石));
                    case "UNKNOWN" -> new NotifyBean(new Status("服务未知异常", Color.纯红));
                    default -> new NotifyBean(new Status(STR."未知状态:\{status}", Color.纯红));
                };
                String notifyMd = STR."""
                    **服务名**：\{instance.getRegistration().getName()}
                    **状态**：<font color="\{notify.getStatus().getColor().getCn()}">\{status}（\{notify.getStatus().getText()}）</font>
                    **服务 IP**：<font color="#00FF00">[\{instance.getRegistration().getServiceUrl()}](\{instance.getRegistration().getServiceUrl()})</font>
                    <at id='all'></at>
                    """;
                // log.info(messageText);
                    MarkdownReq markdownReq = new MarkdownReq("服务状态", Lists.newArrayList(new Elements(notifyMd)));
                feishuRotClient.sendMarkdownReq(markdownReq);
            }
        });
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        if (event instanceof InstanceStatusChangedEvent statusChange) {
            String from = this.getLastStatus(event.getInstance());
            String to = statusChange.getStatusInfo().getStatus();
            return Arrays.binarySearch(this.ignoreChanges, STR."\{from}:\{to}") < 0
                && Arrays.binarySearch(this.ignoreChanges, STR."*:\{to}") < 0
                && Arrays.binarySearch(this.ignoreChanges, STR."\{from}:*") < 0;
        }
        return false;
    }

    public FeishuNotifier(InstanceRepository repository) {
        super(repository);
    }

}
