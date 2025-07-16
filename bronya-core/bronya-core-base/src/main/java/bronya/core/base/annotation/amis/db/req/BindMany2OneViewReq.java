package bronya.core.base.annotation.amis.db.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BindMany2OneViewReq {
    /**
     * 绑定多对一的实体 字段
     */
    private String bindMany2OneClassField;
}
