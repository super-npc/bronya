package bronya.admin.module.db.amis.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmisIdsDto {
    private List<Long> ids;
}
