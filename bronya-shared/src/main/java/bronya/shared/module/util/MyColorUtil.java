package bronya.shared.module.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.split.SplitUtil;

import java.util.List;

@UtilityClass
public class MyColorUtil {

    public Rgb parseAmisRgb(String rgb) {
        String s = StrUtil.subBetween(rgb, "(", ")");
        List<Integer> rgbList = SplitUtil.split(s, ",").stream().map(Integer::parseInt).toList();
        return new Rgb(rgbList.get(0), rgbList.get(1), rgbList.get(2));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Rgb {
        int r, g, b;
    }
}
