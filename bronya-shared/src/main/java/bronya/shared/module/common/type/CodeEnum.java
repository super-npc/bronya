package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS(0, "成功"),

    FAIL(-1, "失败"),

    EXCEPTION(999, "接口异常");

    public final int status;
    public final String msg;
}
