package bronya.core.base.annotation.amis.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Level {
    // default_("default"),
    link("link"), primary("primary"), secondary("secondary"), info("info"), success("success"), warning("warning"),
    danger("danger"), light("light"), dark("dark");

    private final String key;
}
