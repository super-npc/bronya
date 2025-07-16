package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordStatus implements AmisEnum {
    ENABLE("启用", Color.纯绿), DISABLE("禁用", Color.纯红);

    private final String desc;
    private final Color color;
}