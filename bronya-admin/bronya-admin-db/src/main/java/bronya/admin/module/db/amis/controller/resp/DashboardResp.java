package bronya.admin.module.db.amis.controller.resp;

import bronya.admin.base.cfg.git.GitBronyaCfg;
import bronya.admin.base.cfg.git.GitProjectCfg;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DashboardResp {
    private GitBronyaCfg gitBase;
    private GitProjectCfg gitProjectConf;
}
