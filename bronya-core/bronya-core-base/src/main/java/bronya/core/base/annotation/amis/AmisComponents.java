package bronya.core.base.annotation.amis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Setter
@Getter
@AllArgsConstructor
@FieldNameConstants
public abstract class AmisComponents {
    private Boolean required;
    private String type;
    private String name;
    private String label;

    /**
     * 只读模式
     */
    private Boolean disabled;
    private String width;
    private String remark;

    public AmisComponents headSearch() {
        return null;
    }
}
