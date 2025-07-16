package bronya.core.base.annotation.amis.type;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import npc.bulinke.core.module.amis.annotation.field.inputdata.AmisSelect;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmisSelectAutoComplete implements Serializable {
    /**
     * 用于展示, ps 这个key要怎么写,选中后会展示在下拉框中
     */
    private String label;
    private String value;
    /**
     * 对应字段名 {@link AmisSelect.Select#tr()}
     */
    private String columnName;
}
