package bronya.core.base.annotation.amis.db.req;

import lombok.Data;

@Data
public class One2ManyReq {
    private String entity;
    private String entityField;
    private String entityFieldVal;
}
