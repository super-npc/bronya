package bronya.shared.module.util;

import java.util.HashMap;
import java.util.Map;

import org.dromara.hutool.core.text.StrPool;
import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.fastjson2.JSONObject;

import lombok.experimental.UtilityClass;
import org.dromara.hutool.json.JSONUtil;

/**
 * 遵循javascript的字段命名规则
 */
@UtilityClass
public class JsFieldUtil {
    public static final String JS_FIELD_NAME_JOIN = "__"; // ps: 禁止使用. 表示对象,导致amis的前端无法获取到属性

    public String toJsField(String ObjName, String objField) {
        return STR."\{ObjName}\{JS_FIELD_NAME_JOIN}\{objField}";
    }

    public String toObjField(String jsField) {
        return StrUtil.replace(jsField, JS_FIELD_NAME_JOIN, StrPool.DOT);
    }

    public Map<String, Object> toObjField(Map<String, Object> map) {
        JSONObject originalJson = new JSONObject(map);
        // 创建一个Map来存储动态对象
        Map<String, JSONObject> dynamicObjects = new HashMap<>();

        // 遍历原始JSON对象的键
        for (String key : originalJson.keySet()) {
            if (key.contains(JS_FIELD_NAME_JOIN)) {
                // 分割键名
                String[] parts = key.split("__");
                if (parts.length == 2) {
                    String objectName = parts[0];
                    String newKey = parts[1];

                    // 如果对象不存在，则创建一个新的JSONObject
                    dynamicObjects.putIfAbsent(objectName, new JSONObject());
                    dynamicObjects.get(objectName).put(newKey, originalJson.get(key));
                }
            } else {
                // 如果键名不包含 "__"，直接处理为普通键值对
                String value = originalJson.getString(key);
                if (JSONUtil.isTypeJSONObject(value)) {
                    dynamicObjects.putIfAbsent(key, new JSONObject());
                    // 拆分第二层的值
                    JSONObject jsonObject = JSONObject.parseObject(value);
                    dynamicObjects.get(key).putAll(jsonObject);
                }else{
                    dynamicObjects.putIfAbsent("root", new JSONObject());
                    dynamicObjects.get("root").put(key, originalJson.get(key));
                }
            }
        }

        // 将原始JSON中的嵌套对象也加入到动态对象中
        for (String key : originalJson.keySet()) {
            if (!key.contains(JS_FIELD_NAME_JOIN)) {
                if (originalJson.get(key) instanceof JSONObject) {
                    dynamicObjects.putIfAbsent(key, originalJson.getJSONObject(key));
                }
            }
        }

        // 合并嵌套的Strategy对象
        for (String key : originalJson.keySet()) {
            if (!key.contains(JS_FIELD_NAME_JOIN) && originalJson.get(key) instanceof JSONObject) {
                JSONObject nestedObject = originalJson.getJSONObject(key);
                for (String nestedKey : nestedObject.keySet()) {
                    dynamicObjects.get(key).put(nestedKey, nestedObject.get(nestedKey));
                }
            }
        }

        // 创建最终的JSON对象
        JSONObject finalJson = new JSONObject();
        for (Map.Entry<String, JSONObject> entry : dynamicObjects.entrySet()) {
            if (!entry.getKey().equals("root")) {
                finalJson.put(entry.getKey(), entry.getValue());
            } else {
                for (String rootKey : entry.getValue().keySet()) {
                    finalJson.put(rootKey, entry.getValue().get(rootKey));
                }
            }
        }
        return finalJson;
    }
}
