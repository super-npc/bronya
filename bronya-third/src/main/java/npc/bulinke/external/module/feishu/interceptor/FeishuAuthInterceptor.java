package npc.bulinke.external.module.feishu.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson2.JSONObject;
import org.dromara.hutool.core.codec.binary.Base64;
import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.fastjson2.JSONPath;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

@Slf4j
@RequiredArgsConstructor
public class FeishuAuthInterceptor implements Interceptor {
    private final String secret;

    @SneakyThrows
    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) {
        RequestBody body = chain.request().body();
        Buffer buffer = new Buffer();
        Objects.requireNonNull(body).writeTo(buffer);
        String reqBody = buffer.readUtf8();
        JSONObject json = JSONObject.parseObject(reqBody);

        String timestamp = StrUtil.toString(System.currentTimeMillis() / 1000L);
        JSONPath.set(json, "timestamp", timestamp);
        JSONPath.set(json, "sign", this.genSign(secret, timestamp));

        RequestBody requestBodyNew = RequestBody.create(json.toString(), body.contentType());
        Request.Builder requestBuilder = chain.request().newBuilder().post(requestBodyNew);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

    @SneakyThrows
    private String genSign(String secret, String timestamp) {
        // 把timestamp+"\n"+密钥当做签名字符串
        String stringToSign = STR."\{timestamp}\n\{secret}";
        // 使用HmacSHA256算法计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[] {});
        return Base64.encode(signData);
    }
}