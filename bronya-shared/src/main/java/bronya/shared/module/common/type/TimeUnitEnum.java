package bronya.shared.module.common.type;

import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeUnitEnum implements AmisEnum {
    nanoseconds("纳秒", Color.紫罗兰), microseconds("微秒", Color.酸橙绿), milliseconds("毫秒", Color.桃色),
    seconds("秒", Color.午夜的蓝色), minutes("分", Color.深兰花紫), hours("时", Color.适中的板岩暗蓝灰色), days("天", Color.草坪绿);

    private final String desc;
    private final Color color;

    public TimeUnit toJdkTimeUnit() {
        return toJdkTimeUnit(this);
    }

    public static TimeUnitEnum parse(TimeUnit timeUnit) {
        return switch (timeUnit) {
            case NANOSECONDS -> TimeUnitEnum.nanoseconds;
            case MICROSECONDS -> TimeUnitEnum.microseconds;
            case MILLISECONDS -> TimeUnitEnum.milliseconds;
            case SECONDS -> TimeUnitEnum.seconds;
            case MINUTES -> TimeUnitEnum.minutes;
            case HOURS -> TimeUnitEnum.hours;
            case DAYS -> TimeUnitEnum.days;
        };
    }

    public static TimeUnit toJdkTimeUnit(TimeUnitEnum timeUnit) {
        return switch (timeUnit) {
            case nanoseconds -> TimeUnit.NANOSECONDS;
            case microseconds -> TimeUnit.MICROSECONDS;
            case milliseconds -> TimeUnit.MILLISECONDS;
            case seconds -> TimeUnit.SECONDS;
            case minutes -> TimeUnit.MINUTES;
            case hours -> TimeUnit.HOURS;
            case days -> TimeUnit.DAYS;
        };
    }
}
