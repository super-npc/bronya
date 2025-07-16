package bronya.admin.module.media.extend.miniapp.dto;

import com.qcloud.cos.region.Region;

import bronya.shared.module.common.type.FilePlatformType;
import lombok.Data;

@Data
public class MiniAppStorageConfig {
    private final String platform = FilePlatformType.MINI_APP_CLOUD.name();
    private String secretId;
    private String secretKey;
    private String token;
    private Region region;
}
