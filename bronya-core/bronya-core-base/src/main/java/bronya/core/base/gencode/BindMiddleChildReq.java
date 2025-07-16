package bronya.core.base.gencode;

import bronya.core.base.gencode.RelationshipManagementReq.RelationshipType;
import lombok.Data;

@Data
public class BindMiddleChildReq {
    private String middleEntity;
    private String selfField;
    private String joinField;
    private String selfFieldVal;
    private RelationshipType relationshipType;
}
