package bronya.admin.module.openfeign.interceptor;

import java.nio.charset.StandardCharsets;

import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import org.dromara.hutool.http.meta.Method;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;


/**
 * 加密
 */
@Component
@RequiredArgsConstructor
public class ExternalEncodeSignFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (!Method.POST.name().equals(requestTemplate.method())) {
            return;
        }
        String body = "";
        if (requestTemplate.body() != null) {
            body = new String(requestTemplate.body(), StandardCharsets.UTF_8);
        }
//        String sortedData = FeignSignConfiguration.sortedData(body);
        // 从配置/文件 获取ak,sk
        final String accessKey = "c26edfc7-6a19-47fb-b08e-76320e78825a";
        final String secretKey = "648c7c2585f6a36acae427e3";
//        String sign = SecureUtil.hmacSha256(secretKey).digestHex(sortedData);
//        requestTemplate.header(SystemExternalAuth.Fields.accessKey, accessKey);
//        requestTemplate.header(Constant.SIGN, sign);

        requestTemplate.header(AdminBaseConstant.TRACE_ID, TraceId.getMdcTradeId());
    }
}