package bronya.admin.module.db.amis.dto;

import java.util.Optional;

import bronya.shared.module.user.SysUserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmisLoginUser {
    private Optional<SysUserVo> loginInfo;
}
