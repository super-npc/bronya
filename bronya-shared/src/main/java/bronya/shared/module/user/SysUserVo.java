package bronya.shared.module.user;

import lombok.Data;

import java.util.List;

@Data
public class SysUserVo {
    private Long id;
    private String userAvatar;
    private String userName;
    private List<SysRoleVo> roles;
}
