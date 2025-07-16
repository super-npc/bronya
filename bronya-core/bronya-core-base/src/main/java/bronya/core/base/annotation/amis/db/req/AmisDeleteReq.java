package bronya.core.base.annotation.amis.db.req;

import java.io.Serializable;

import lombok.Data;

@Data
public class AmisDeleteReq implements Serializable {
    private String id;
}
