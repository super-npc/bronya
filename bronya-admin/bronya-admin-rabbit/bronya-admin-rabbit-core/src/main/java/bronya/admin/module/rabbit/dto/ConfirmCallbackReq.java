package bronya.admin.module.rabbit.dto;

import bronya.admin.module.rabbit.type.MsgType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmCallbackReq {
    private String tradeId;
    private MsgType msgType;
    /**
     * 数据库主键
     */
    private Long primaryId;
}
