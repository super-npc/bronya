package bronya.shared.module.util;

import com.alibaba.cola.exception.SysException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.split.SplitUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class WxCloudUtil {
    private static final Pattern CLOUD_PATH_PATTERN = Pattern.compile("cloud://([^.]+)\\.([^/]+)/(.*)");

    /**
     * 解析云存储路径
     *
     * @param cloudPath 云存储路径
     * @return 包含环境ID、存储桶名称和文件键的对象
     */
    public CloudPathInfo parseCloudPath(String cloudPath) {
        if (cloudPath == null) {
            log.warn("云存储路径为空");
            return null;
        }
        Matcher matcher = CLOUD_PATH_PATTERN.matcher(cloudPath);
        if (matcher.find()) {
            String envId = matcher.group(1);
            String bucketName = matcher.group(2);
            String fileKey = STR."/\{matcher.group(3)}";
                log.info("解析成功: envId={}, bucketName={}, fileKey={}", envId, bucketName, fileKey);
            return new CloudPathInfo(cloudPath, envId, bucketName, fileKey);
        }
        throw new SysException(STR."无法解析云存储路径: \{cloudPath}");
    }

    /**
     * 将云文件ID转换为URL
     *
     * @param cloudPath 云文件ID
     * @return 转换后的URL
     */
    public String convertFileIdToUrl(String cloudPath) {
        if (cloudPath == null || !cloudPath.startsWith("cloud://")) {
            return cloudPath;
        }
        String[] arr = cloudPath.split("/");
        arr[0] = "https:";
        arr[2] = STR."\{arr[2].split("\\.")[1]}.tcb.qcloud.la";
        //        log.info("转换云文件ID为URL: {}", url);
        return String.join("/", arr);
    }

    /**
     * 将URL转换为云文件ID
     *
     * @param url 文件URL
     * @return 转换后的云文件ID
     */
    public String convertUrlToFileId(String url) {
        if (url == null || !url.startsWith("https://")) {
            return url;
        }
        String main = StrUtil.subBetween(url, "https://", "/");
        main = StrUtil.removeAll(main, ".tcb.qcloud.la");
        List<String> split = SplitUtil.split(main, "-");
        String first = STR."\{split.get(1)}-\{split.get(2)}";
            String last = SplitUtil.split(url, ".tcb.qcloud.la").getLast();
        //        log.info("转换URL为云文件ID: {}", fileId);
        return STR."cloud://\{first}.\{main}\{last}";
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CloudPathInfo {
        private String objectId;
        private String envId;
        private String bucketName;
        private String fileKey;
    }

    public static void main(String[] args) {
        String s = WxCloudUtil.convertUrlToFileId(
            "https://7072-prod-5g3l0m5je193306f-1259198184.tcb.qcloud.la/dev/nobita/backstage/image/png/17842bd1f37ca5a651c2ed4ce0cd0011.png");
        log.info("转换结果: {}", s);
    }
}
