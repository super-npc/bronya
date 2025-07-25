package npc.bulinke.external.module.namesilo.resp;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DnsUpdateRecordResp implements Serializable {

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
            private String record_id;
        }
    }
}
