package bronya.shared.module.rpc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.dromara.hutool.core.date.StopWatch;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.http.meta.Method;
import org.dromara.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;

import com.alibaba.cola.exception.BizException;
import com.google.common.collect.Lists;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
public class ApiClient {
    private Retrofit.Builder builder;
    private Retrofit retrofit;
    private Proxy proxy;

    private final List<Interceptor> interceptors = Lists.newArrayList();

    public ApiClient(String baseUrl, Interceptor... interceptor) {
        super();
        this.init(baseUrl, interceptor);
    }

    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        // gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        return gsonBuilder.create();
    }

    private void init(String baseUrl, Interceptor... interceptor) {
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(getGson());
        builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory);
        retrofit = builder.build();
        // interceptors.add(new TLogOkHttpInterceptor());
        interceptors.addAll(Arrays.asList(interceptor));
    }

    public ApiClient(String baseUrl, Proxy proxy, Interceptor... interceptor) {
        super();
        this.proxy = proxy;
        this.init(baseUrl, interceptor);
    }

    public <S> S createService(Class<S> serviceClass) {
        OkHttpClient.Builder buildOkHttp = new OkHttpClient.Builder();
        interceptors.forEach(buildOkHttp::addInterceptor);
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NotNull String s) {
                log.info(s);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        buildOkHttp.addNetworkInterceptor(logInterceptor);

        if (proxy != null) {
            buildOkHttp.proxy(proxy);
//            buildOkHttp.setProxy$okhttp(proxy);
        }
        buildOkHttp.connectTimeout(120, TimeUnit.SECONDS);
        buildOkHttp.readTimeout(120, TimeUnit.SECONDS);
        builder.client(buildOkHttp.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    public static <T> List<T> executeSyncList(Call<ResponseBody> call, Class<T> t) {
        Request request = call.request();
        StopWatch stopWatch = StopWatch.of();
        stopWatch.start();
        Response<ResponseBody> response = null;
        List<T> obj = null;
        try {
            if (!Method.GET.name().equals(request.method())) {
                Buffer buffer = new Buffer();
                Objects.requireNonNull(request.body()).writeTo(buffer);
                String reqBody = buffer.readUtf8();
            }

            response = call.execute();
            // 204 也是成功,但是没有结果返回
            if (response.isSuccessful() && response.code() == 200) {
                // thirdPartyCallRecord.setSuccess(true);
                try (ResponseBody body = response.body()) {
                    Assert.notNull(body, () -> new BizException("返回body为空"));
                    String respBody = body.string();
                    log.info("resp.body:{}", respBody);
                    // thirdPartyCallRecord.setRespBody(respBody);
                    Assert.isTrue(JSONUtil.isTypeJSON(respBody), () -> new BizException("返回body非json格式"));
                    // Type connectionListType = new TypeToken<List<T>>() {
                    // }.getType();
                    // obj = getGson().fromJson(respBody, connectionListType);
                    obj = JSONUtil.parseArray(respBody).toList(t);
                }
            }
        } catch (Exception e) {
            log.error("调用http请求异常", e);
        } finally {
            stopWatch.stop();
            String s = stopWatch.prettyPrint();
            log.info("请求耗时:{}", s);
        }
        return obj;
    }

    public static <T> T executeSync(Call<ResponseBody> call, Class<T> t) {
        Request request = call.request();
        String url = request.url().toString();
        // ThirdPartyCallRecord thirdPartyCallRecord = new ThirdPartyCallRecord();
        // thirdPartyCallRecord.setUrl(url);
        StopWatch stopWatch = StopWatch.of();
        stopWatch.start();
        Response<ResponseBody> response = null;
        T obj = null;
        try {
            if (!Method.GET.name().equals(request.method())) {
                Buffer buffer = new Buffer();
                Objects.requireNonNull(request.body()).writeTo(buffer);
                String reqBody = buffer.readUtf8();
                // thirdPartyCallRecord.setReqBody(reqBody);
            }

            response = call.execute();

            int code = response.code();
            // thirdPartyCallRecord.setRespCode(code);

            // thirdPartyCallRecord.setSuccess(false);
            // 204 也是成功,但是没有结果返回
            if (response.isSuccessful() && response.code() == 200) {
                // thirdPartyCallRecord.setSuccess(true);
                try (ResponseBody body = response.body()) {
                    Assert.notNull(body, () -> new BizException("返回body为空"));
                    String respBody = body.string();
                    log.info("resp.body:{}", respBody);
                    if (t == String.class) {
                        return (T) respBody;
                    }
                    // thirdPartyCallRecord.setRespBody(respBody);
                    Assert.isTrue(JSONUtil.isTypeJSON(respBody), () -> new BizException("返回body非json格式"));
                    obj = getGson().fromJson(respBody, t);
                }
            }
        } catch (Exception e) {
            log.error("调用http请求异常", e);
        } finally {
            stopWatch.stop();

            // thirdPartyCallRecord.setTookMs(tookMs);
            // ThreadPoolUtil.getPool(GlobalPoolStarter.GLOBAL_POOL_HTTP_API_CLIENT).execute(() -> {
            // // 记录接口日志
            // // actionLogService.save(actionLog);
            // final ThirdPartyCallRecordService thirdPartyCallRecordService =
            // SpringUtil.getBean(ThirdPartyCallRecordService.class);
            // thirdPartyCallRecordService.save(thirdPartyCallRecord);
            // });
        }
        return obj;
    }

}