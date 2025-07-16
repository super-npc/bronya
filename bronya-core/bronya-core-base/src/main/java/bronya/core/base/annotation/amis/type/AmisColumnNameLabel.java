package bronya.core.base.annotation.amis.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmisColumnNameLabel {
    private String name;
    private String label;
}
