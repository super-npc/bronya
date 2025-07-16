package bronya.admin.module.openfeign.interceptor;

import java.lang.reflect.Type;

import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Component;

import com.alibaba.cola.exception.BizException;
import com.alibaba.fastjson2.JSONObject;

import bronya.shared.module.common.vo.ResultVO;
import feign.Response;
import feign.codec.Decoder;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class RObjResResponseInterceptor implements Decoder {

    @SneakyThrows
    @Override
    public Object decode(Response response, Type type) {
        String json = IoUtil.readUtf8(response.body().asInputStream()); // 基础类型string json有问题
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = response.status(); // 200
        Assert.isTrue(status == 200, () -> new BizException(jsonObject.getString(ResultVO.Fields.msg)));

        ResultVO.DataDetail dataDetail =
            jsonObject.getJSONObject(ResultVO.Fields.dataDetail).toJavaObject(ResultVO.DataDetail.class);
        Class<?> dataClass = Class.forName(dataDetail.getBeanType()); // 远程调用必须用对象来承接,不要使用基础类型,后续拓展不方便
        if (dataDetail.getIsArray()) {
            return ConvertUtil.convert(dataClass, jsonObject.get(ResultVO.Fields.data));
        } else {
            if (ClassUtil.isJdkClass(dataClass)) {
                return ConvertUtil.convert(dataClass, jsonObject.get(ResultVO.Fields.data));
            }
            return jsonObject.getJSONObject(ResultVO.Fields.data).toJavaObject(dataClass);
        }
    }

    private Tuple2<Boolean, String> getType(Type type) {
        String typeName = type.getTypeName();
        if (StrUtil.startWith(typeName, "java.util.List")) {
            return Tuple.of(true, StrUtil.subBetween(typeName, "<", ">"));
        }
        return Tuple.of(false, typeName);
    }
}
