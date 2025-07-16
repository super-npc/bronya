package bronya.shared.module.util;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrPool;

import net.steppschuh.markdowngenerator.MarkdownBuilder;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.TextBuilder;
import org.dromara.hutool.core.text.StrUtil;

import java.util.List;

@Slf4j
public class Md {
    private final MarkdownBuilder<TextBuilder, Text> md = new TextBuilder();

    public Md appendLns(List<Object> objs) {
        for (Object obj : objs) {
            md.append(obj).append(StrPool.CRLF);
        }
//        log.info(StrUtil.toString(obj));
        return this;
    }

    public Md appendLn(Object obj) {
//        log.info(StrUtil.toString(obj));
        md.append(obj).append(StrPool.CRLF);
        return this;
    }

    public Md append(Object obj) {
//        log.info(StrUtil.toString(obj));
        md.append(obj);
        return this;
    }

    public static String emojiBool(boolean flag) {
        return flag ? "✅ " : "❌ ";
    }

    @Override
    public String toString() {
        return StrUtil.removeSuffix(md.toString(), StrPool.CRLF);
    }
}
