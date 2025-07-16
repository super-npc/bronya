package bronya.core.base.annotation.amis.db.resp;

import java.util.List;

import bronya.core.base.annotation.amis.page.Operation.BtnLevelType;
import bronya.core.base.annotation.amis.type.AmisApi;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * {
 * "type": "button-group",
 * "buttons": [
 * {
 * "type": "button",
 * "label": "按钮1222",
 * "actionType": "ajax",
 * "api": "/amis/api/mock2/form/saveForm"
 * }
 * ]
 * }
 */
@Data
@RequiredArgsConstructor
public class ButtonGroup {
    private final String type = "button-group";
    private final BtnLevelType btnLevel = BtnLevelType.light;
    private final List<Button> buttons;

    @Data
    @RequiredArgsConstructor
    public static class Button {
        private final String type = "button";
        private final String actionType = "ajax";
        private final BtnLevelType level;
        private final String label;
        private final AmisApi api;
    }
}
