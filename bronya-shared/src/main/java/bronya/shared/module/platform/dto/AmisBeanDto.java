package bronya.shared.module.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmisBeanDto {
    private Class<?> mainClass;
    private String mainClassSimpleName;
}
