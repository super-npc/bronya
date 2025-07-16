package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum LevelType implements AmisEnum {
    info("普通", Color.军校蓝), success("成功", Color.绿宝石),warning("告警",Color.纯黄),danger("异常",Color.印度红);

    private final String desc;
    private final Color color;
}