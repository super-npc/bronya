package npc.bulinke.external.module.telegram;

import bronya.shared.module.rpc.ApiClient;
import com.alibaba.cola.exception.BizException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.telegram.req.SendMessageReq;
import npc.bulinke.external.module.telegram.resp.GetMeResp;
import npc.bulinke.external.module.telegram.resp.SendMessageResp;
import npc.bulinke.external.module.telegram.resp.TelegramRespBase;
import npc.bulinke.external.module.telegram.resp.GetUpdateResp;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.date.StopWatch;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.http.html.HtmlUtil;
import org.dromara.hutool.http.meta.Method;
import org.dromara.hutool.json.JSONUtil;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TelegramBotCli {
    private final TelegramApi api;

    public TelegramBotCli(Proxy proxy) {
        this.api = new ApiClient("https://api.telegram.org/", proxy).createService(TelegramApi.class);
    }

    public interface TelegramApi {
        @FormUrlEncoded
        @POST("/bot{token}/sendMessage")
        Call<ResponseBody> sendMessage(@Path("token") String token, @FieldMap Map<String, Object> fields);

        @GET("/bot{token}/getMe")
        Call<ResponseBody> getMe(@Path("token") String token);

        @GET("/bot{token}/getUpdates")
        Call<ResponseBody> getUpdates(@Path("token") String token);
    }

    public TelegramRespBase<GetUpdateResp[]> getUpdates(String token) {
        return this.executeSync(api.getUpdates(token), GetUpdateResp[].class);
    }

    public TelegramRespBase<GetMeResp> getMe(String token) {
        return this.executeSync(api.getMe(token), GetMeResp.class);
    }

    public TelegramRespBase<SendMessageResp> sendMessage(String token, SendMessageReq req) {
        Map<String, Object> fields = BeanUtil.beanToMap(req, true, true);
        return this.executeSync(api.sendMessage(token, fields), SendMessageResp.class);
    }

    private <T> TelegramRespBase<T> executeSync(Call<ResponseBody> call, Class<T> t) {
        Request request = call.request();
        String url = request.url().toString();
        StopWatch stopWatch = StopWatch.of();
        stopWatch.start();
        Response<ResponseBody> response = null;
        TelegramRespBase<T> result = new TelegramRespBase<>(); // 初始化默认结果对象
        try {
            if (!Method.GET.name().equals(request.method())) {
                Buffer buffer = new Buffer();
                Objects.requireNonNull(request.body()).writeTo(buffer);
                String reqBody = buffer.readUtf8();
            }
            response = call.execute();
            // 204 也是成功,但是没有结果返回
            if (response.isSuccessful() && response.code() == 200) {
                try (ResponseBody body = response.body()) {
                    Assert.notNull(body, () -> new BizException("返回body为空"));
                    String respBody = body.string();
                    log.info("resp.body:{}", respBody);
                    Assert.isTrue(JSONUtil.isTypeJSON(respBody), () -> new BizException("返回body非json格式"));
                    Type type = new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() {
                            return new Type[]{t};
                        }

                        @Override
                        public Type getRawType() {
                            return TelegramRespBase.class;
                        }

                        @Override
                        public Type getOwnerType() {
                            return null;
                        }
                    };
                    result = getGson().fromJson(respBody, type);
                }
            } else {
                result.setOk(false);
            }
        } catch (Exception e) {
            log.error("调用http请求异常", e);
            result.setOk(false);
        } finally {
            stopWatch.stop();
        }
        return result;
    }

    private static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        // gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        return gsonBuilder.create();
    }

}
