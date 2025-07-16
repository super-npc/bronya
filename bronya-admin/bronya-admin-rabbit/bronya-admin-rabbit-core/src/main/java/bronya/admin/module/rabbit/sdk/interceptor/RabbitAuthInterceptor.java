package bronya.admin.module.rabbit.sdk.interceptor;

import org.jetbrains.annotations.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;

@Slf4j
@RequiredArgsConstructor
public class RabbitAuthInterceptor implements Interceptor {
    private final String userName;
    private final String password;

    @NotNull
    @SneakyThrows
    @Override
    public okhttp3.Response intercept(Interceptor.Chain chain) {
        String credentials = Credentials.basic(userName, password);
        Request.Builder requestBuilder = chain.request().newBuilder().header("Authorization", credentials);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}