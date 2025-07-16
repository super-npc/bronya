package npc.bulinke.external.module.namesilo.req;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnsBaseReq implements Serializable {
    private String key;
    private String domain;
}
