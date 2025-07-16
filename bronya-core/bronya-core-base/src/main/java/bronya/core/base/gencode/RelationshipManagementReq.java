package bronya.core.base.gencode;

import java.io.Serializable;

import lombok.Data;

@Data
public class RelationshipManagementReq implements Serializable {
    private String middleClass;
    private String thisMiddleFiled;
    private String thisMiddleFiledVal;
    private String anOtherMiddleField;
    private String anOtherMiddleFieldVal;
    private RelationshipType relationshipType;

    public enum RelationshipType{
        connect, disconnect
    }
}
