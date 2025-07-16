package npc.bulinke.external.module.namesilo.req;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnsUpdateRecordReq implements Serializable {
    private DnsBaseReq dnsBase;

    /**
     * // {@link DnsListRecordsResp.Namesilo.Reply.ResourceRecord}
     */
    private String rrid;
    private String rrhost;
    private String rrvalue;
}
