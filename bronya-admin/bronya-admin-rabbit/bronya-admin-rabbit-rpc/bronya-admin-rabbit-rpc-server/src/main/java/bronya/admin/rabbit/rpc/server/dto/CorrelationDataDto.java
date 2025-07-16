package bronya.admin.rabbit.rpc.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CorrelationDataDto {
    private String msgType;
    private Long primaryId;
    private String tradeId;
}
