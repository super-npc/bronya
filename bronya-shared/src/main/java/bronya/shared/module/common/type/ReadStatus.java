package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReadStatus implements AmisEnum {
    READ("已读", Color.靛青), UNREAD("未读", Color.浅灰色);

    private final String desc;
    private final Color color;
}