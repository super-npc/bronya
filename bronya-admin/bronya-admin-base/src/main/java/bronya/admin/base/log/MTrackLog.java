package bronya.admin.base.log;

import java.io.Serializable;

import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.net.url.UrlDecoder;

import lombok.Data;

@Data
public class MTrackLog implements Serializable {

    private String url;
    private String method;
    private long start;
    private long end;
    private String remoteIp;
    private String parameter;

    @Override
    public String toString() {
        long cost = end - start;
        String level = cost >= 1500 ? "SERIOUS" : (cost > 300 ? "NORMAL" : "FAST");
        String decodedPath = UrlDecoder.decode(url);
        return STR."\{level},\{method}:\{decodedPath}, cost:\{DateUtil.formatBetween(cost)}, ip:\{remoteIp}, params:\{parameter}}";
    }
}
