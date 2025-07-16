package bronya.admin.module.rabbit.sdk.resp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RabbitQueueResp {
    private int filteredCount;
    private int itemCount;
    private List<ItemsDTO> items;
    private int page;
    private int pageCount;
    private int pageSize;
    private int totalCount;

    @NoArgsConstructor
    @Data
    public static class ItemsDTO {
        /**
         * 队列名
         */
        private String name;

        /**
         * 消费者连接数
         */
        private Integer consumers;

        /**
         * 是否持久化
         */
        private Boolean durable;

        /**
         * 队列状态
         */
        public String state;

        /**
         * 队列消息数量
         */
        public Integer messages;

        /**
         * 队列大小 单位byte
         */
        public Long queueSize;
        private ArgumentsDTO arguments;
        private boolean autoDelete;
        private int consumerCapacity;
        private int consumerUtilisation;
        private EffectivePolicyDefinitionDTO effectivePolicyDefinition;
        private boolean exclusive;
        private int memory;
        private int messageBytes;
        private int messageBytesPagedOut;
        private int messageBytesPersistent;
        private int messageBytesRam;
        private int messageBytesReady;
        private int messageBytesUnacknowledged;
        private MessagesDetailsDTO messagesDetails;
        private int messagesPagedOut;
        private int messagesPersistent;
        private int messagesRam;
        private int messagesReady;
        private MessagesReadyDetailsDTO messagesReadyDetails;
        private int messagesReadyRam;
        private int messagesUnacknowledged;
        private MessagesUnacknowledgedDetailsDTO messagesUnacknowledgedDetails;
        private int messagesUnacknowledgedRam;
        private String node;
        private int reductions;
        private ReductionsDetailsDTO reductionsDetails;
        private int storageVersion;
        private String type;
        private String vhost;

        @NoArgsConstructor
        @Data
        public static class ArgumentsDTO {}

        @NoArgsConstructor
        @Data
        public static class EffectivePolicyDefinitionDTO {}

        @NoArgsConstructor
        @Data
        public static class MessagesDetailsDTO {
            private double rate;
        }

        @NoArgsConstructor
        @Data
        public static class MessagesReadyDetailsDTO {
            private double rate;
        }

        @NoArgsConstructor
        @Data
        public static class MessagesUnacknowledgedDetailsDTO {
            private double rate;
        }

        @NoArgsConstructor
        @Data
        public static class ReductionsDetailsDTO {
            private double rate;
        }
    }
}
