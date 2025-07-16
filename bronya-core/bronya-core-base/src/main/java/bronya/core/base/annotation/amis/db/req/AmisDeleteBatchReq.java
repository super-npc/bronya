package bronya.core.base.annotation.amis.db.req;

import java.io.Serializable;

import lombok.Data;

@Data
public class AmisDeleteBatchReq implements Serializable {
    private String ids;
}
