package npc.bulinke.external.module.namesilo.req;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import npc.bulinke.external.module.namesilo.type.RrType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnsAddRecordReq implements Serializable {
    private DnsBaseReq dnsBase;

    private RrType rrtype;
    private String rrhost;
    private String rrvalue;
}
