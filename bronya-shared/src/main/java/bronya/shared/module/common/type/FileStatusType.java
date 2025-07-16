package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStatusType implements AmisEnum {
    EXIST("存在", Color.绿宝石), LOSE("丢失", Color.深红色);

    private final String desc;
    private final Color color;

}