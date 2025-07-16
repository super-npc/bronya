package bronya.shared.module.util;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JsFieldUtilTest {

    @Test
    public void toObjField() {
        String json =
            """
                {"aa":"bb","Strategy__enable":true,"Strategy__type":"GRID_TREND","Strategy__volume":1,"Strategy":{"ouyiConfigId":"1"},"Strategy__execStep":"m15","Strategy__instId":"DOGE-USD-SWAP"}
                """;
        // 将字符串解析为JSON对象
        JSONObject originalJson = JSONObject.parse(json);
        Map<String, Object> objField = JsFieldUtil.toObjField(originalJson);
        System.out.println("objField = " + objField);
    }
}