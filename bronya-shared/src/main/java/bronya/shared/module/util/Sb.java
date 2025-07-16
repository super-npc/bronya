package bronya.shared.module.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrPool;
import org.dromara.hutool.core.text.StrUtil;

@Slf4j
@Data
@RequiredArgsConstructor
public class Sb {
    private final StringBuilder sb = StrUtil.builder();

    public StringBuilder append(String message, Object... args) {
        String formattedMessage = StrUtil.format(message, args);
        if (StrUtil.isBlank(formattedMessage) || StrUtil.isNullOrUndefined(formattedMessage)) {
            return sb;
        }
        log.info(formattedMessage);
        sb.append(formattedMessage).append(StrPool.CRLF);
        return sb;
    }
}
