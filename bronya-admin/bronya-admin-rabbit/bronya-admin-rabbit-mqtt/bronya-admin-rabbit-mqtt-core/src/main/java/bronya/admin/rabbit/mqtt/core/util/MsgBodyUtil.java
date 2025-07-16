package bronya.admin.rabbit.mqtt.core.util;

import bronya.admin.rabbit.mqtt.core.req.MsgBody;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@UtilityClass
public class MsgBodyUtil {

    public <T> MsgBody<T> parse(String json, Class<T> dataClass) {
        // 构建泛型类型：MsgBody<dataClass>（如 MsgBody<SubscribesUpdate>）
        Type msgBodyType = new ParameterizedType() {
            @NotNull
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] {dataClass}; // 泛型参数为传入的实际类型
            }

            @NotNull
            @Override
            public Type getRawType() {
                return dataClass; // 原始类型是 MsgBody
            }

            @Override
            public Type getOwnerType() {
                return dataClass;
            }
        };

        // 用 TypeReference 明确类型，避免泛型擦除导致的解析错误
        return JSON.parseObject(json, new TypeReference<>(msgBodyType) {});
    }
}
