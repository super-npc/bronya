package npc.bulinke.external.module.namesilo.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RrType {
    A("The IPV4 Address"),

    AAAA("The IPV6 Address"),

    CNAME("The Target Hostname"),

    MX("The Target Hostname"),

    TXT("The Text");

    private String desc;
}
