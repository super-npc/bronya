package npc.bulinke.external.module.namesilo.resp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DnsListRecordsResp {

    private Namesilo namesilo;

    @NoArgsConstructor
    @Data
    public static class Namesilo {
        private Request request;
        private Reply reply;

        @NoArgsConstructor
        @Data
        public static class Request {
            private String operation;
            private String ip;
        }

        @NoArgsConstructor
        @Data
        public static class Reply {
            private int code;
            private String detail;
            private List<ResourceRecord> resource_record;

            @NoArgsConstructor
            @Data
            public static class ResourceRecord {
                private String record_id;
                private String type;
                private String host;
                private String value;
                private int ttl;
                private int distance;
            }
        }
    }
}
