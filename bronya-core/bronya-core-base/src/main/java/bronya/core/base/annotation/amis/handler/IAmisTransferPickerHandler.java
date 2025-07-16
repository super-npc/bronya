package bronya.core.base.annotation.amis.handler;

import java.util.List;

import bronya.core.base.annotation.amis.type.TransferPickerOptionsVo;


public interface IAmisTransferPickerHandler {
    /**
     * 加载选项数据
     */
    List<TransferPickerOptionsVo> transferPickerOptions();
}
