package npc.bulinke.external.interceptor;

import java.util.Objects;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okio.Buffer;

@Slf4j
public class ApiClientLoggingInterceptor implements Interceptor {
    @SneakyThrows
    @Override
    public okhttp3.Response intercept(Chain chain) {
        Request request = chain.request();
        okhttp3.Response response = null;
        Buffer buffer = new Buffer();
        Objects.requireNonNull(request.body()).writeTo(buffer);
        String reqBody = buffer.readUtf8();
        String url = request.url().toString();
        try {
            log.error("--> url:{},body:{}", url, reqBody);
            response = chain.proceed(request);
        } catch (Exception var35) {
            log.error(STR."异常!--> url:\{url},body:\{reqBody}", var35);
            throw var35;
        } finally {
            if (response != null) {
                int code = response.code();
                log.error("<-- code:{}", code);
            }
        }
        return response;
    }
}