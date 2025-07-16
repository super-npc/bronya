package bronya.core.base.annotation.amis.type;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public class TransferPickerOptionsVo implements Serializable {
    private String label;
    private String value;
    private List<TransferPickerOptionsVo> children;

    public TransferPickerOptionsVo(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
