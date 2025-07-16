package bronya.admin.base.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import bronya.admin.base.log.MTrackLog;
import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.crypto.SignUtil;
import org.dromara.hutool.http.meta.Method;
import org.dromara.hutool.http.server.servlet.ServletUtil;
import org.dromara.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.alibaba.fastjson2.JSONObject;

import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.util.TraceId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 单次执行：OncePerRequestFilter 确保在一次请求的生命周期内，无论请求如何转发（forwarding）或包含（including），过滤器逻辑只执行一次。这对于避免重复处理请求或响应非常有用。
 * <p>
 * 内置支持：它内置了对请求和响应包装器的支持，使得开发者可以方便地对请求和响应进行包装和处理。
 * <p>
 * 简化代码：通过继承 OncePerRequestFilter，开发者可以减少重复代码，因为过滤器的执行逻辑已经由基类管理。
 * <p>
 * 易于扩展：开发者可以通过重写 doFilterInternal 方法来实现自己的过滤逻辑，而不需要关心过滤器的注册和执行次数。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RequestWrapperFilter extends OncePerRequestFilter {
    private final IPlatform platformService;

    private final StandardServletMultipartResolver multipartResolver;
    private static final Logger trackLogger = LoggerFactory.getLogger("TRACK_FILE_NAME");

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain) throws ServletException, IOException {
        MTrackLog mTrackLog = new MTrackLog();
        mTrackLog.setUrl(request.getRequestURI());
        mTrackLog.setMethod(request.getMethod());
        mTrackLog.setStart(System.currentTimeMillis());
        mTrackLog.setRemoteIp(ServletUtil.getClientIP(request));
        mTrackLog.setEnd(System.currentTimeMillis());

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        String trace = request.getHeader(AdminBaseConstant.TRACE_ID);
        if (StrUtil.isBlank(trace)) {
            TraceId.setMdcTraceId(STR."api_\{IdUtil.objectId()}");
        } else {
            // 从feign过来
                TraceId.setMdcTraceId(trace);
            log.debug("获取header中的链路id:{}", trace);
        }
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            this.response(responseWrapper);
            mTrackLog.setParameter(this.getParams(requestWrapper));
            trackLogger.info(mTrackLog.toString()); // 记录独立访问日志
            TraceId.remove();
        }
    }

    // https://juejin.cn/post/7500425545563471909
    private String getParams(ContentCachingRequestWrapper requestWrapper) {
        String parameter = null;
        if (Method.GET.name().equalsIgnoreCase(requestWrapper.getMethod())) {
            parameter = requestWrapper.getQueryString();
        } else if (multipartResolver.isMultipart(requestWrapper)) {
            parameter = "multipart";
        } else {
            // 需要验证参数
            parameter = requestWrapper.getContentAsString();
        }
        return parameter;
    }

    /**
     * ps: ExternalEncodeSignFeignInterceptor openfeign的header需要在这个类设置
     */
    private void response(ContentCachingResponseWrapper responseWrapper) throws IOException {
        byte[] body = responseWrapper.getContentAsByteArray(); // 可以在这里处理响应数据
        String json = StrUtil.str(body, Charset.defaultCharset());

        responseWrapper.addHeader(AdminBaseConstant.LOGIN_REQUIRED,
            ConvertUtil.toStr(platformService.isLoginRequired()));
        responseWrapper.addHeader(AdminBaseConstant.TRACE_ID, TraceId.getMdcTradeId());
        responseWrapper.addHeader(AdminBaseConstant.TRACE_ID, TraceId.getMdcTradeId());
        responseWrapper.addHeader(AdminBaseConstant.BODY_TIME, ConvertUtil.toStr(System.currentTimeMillis()));
        if (JSONUtil.isTypeJSONObject(json)) {
            String bodySign = SignUtil.signParamsMd5(JSONObject.parseObject(json), "my_password");
            responseWrapper.addHeader(AdminBaseConstant.BODY_SIGN, bodySign);
        }
        responseWrapper.copyBodyToResponse(); // 必须调用此方法以将响应数据发送到客户端
    }
}
