package npc.bulinke.external.module.feishu;

import bronya.shared.module.rpc.ApiClient;
import npc.bulinke.external.module.feishu.interceptor.FeishuAuthInterceptor;
import npc.bulinke.external.module.feishu.req.MarkdownReq;
import npc.bulinke.external.module.feishu.req.TextReq;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class FeishuRotClient {
    private final FeishuApi api;

    public FeishuRotClient(String token) {
        this.api =
            new ApiClient(STR."https://open.feishu.cn/open-apis/bot/v2/hook/\{token}/").createService(FeishuApi.class);
    }

    public FeishuRotClient(String token, String secret) {
        FeishuAuthInterceptor feishuAuthInterceptor = new FeishuAuthInterceptor(secret);
        this.api = new ApiClient(STR."https://open.feishu.cn/open-apis/bot/v2/hook/\{token}/", feishuAuthInterceptor)
            .createService(FeishuApi.class);
    }

    public interface FeishuApi {
        @POST(".")
        Call<ResponseBody> sendText(@Body TextReq req);

        @POST(".")
        Call<ResponseBody> sendMarkdownReq(@Body MarkdownReq req);
    }

    public FeishuResp sendMarkdownReq(MarkdownReq req) {
        return ApiClient.executeSync(api.sendMarkdownReq(req), FeishuResp.class);
    }

    public FeishuResp sendText(TextReq req) {
        return ApiClient.executeSync(api.sendText(req), FeishuResp.class);
    }
}
