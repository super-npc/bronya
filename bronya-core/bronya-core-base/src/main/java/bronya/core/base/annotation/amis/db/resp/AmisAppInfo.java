package bronya.core.base.annotation.amis.db.resp;

import lombok.Data;

@Data
public class AmisAppInfo {
    private String appName;
    /**
     * 基线版本
     */
    private String gitBaseVersion;
    /**
     * 应用项目版本
     */
    private String gitProjectVersion;
}