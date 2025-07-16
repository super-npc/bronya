package bronya.core.base.annotation.amis.db.resp;

import java.util.List;

import bronya.core.base.annotation.amis.page.Operation;
import bronya.shared.module.user.SysUserVo;
import lombok.Data;

@Data
public class TopRightHeaderResp {
    /**
     * 子系统按钮
     */
    private final List<SubSystemButton> subSystemButtons;
    private SysUserVo loginUser;

    @Data
    public static class SubSystemButton{
        private final String label;
        private Operation.BtnLevelType level;
    }
}
