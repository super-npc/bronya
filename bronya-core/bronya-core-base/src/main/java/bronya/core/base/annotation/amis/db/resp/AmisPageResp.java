package bronya.core.base.annotation.amis.db.resp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmisPageResp {
    private Long total;
    private List<Object> rows;
}
