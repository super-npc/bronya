package bronya.core.base.annotation.amis.db.req;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class bindMany2ManyViewReq {
    /**
     * 绑定多对多的实体 字段
     */
    private String bindMany2ManyClassField;
}
