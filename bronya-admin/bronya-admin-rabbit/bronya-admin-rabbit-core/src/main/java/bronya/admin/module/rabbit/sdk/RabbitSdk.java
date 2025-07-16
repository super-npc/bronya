package bronya.admin.module.rabbit.sdk;


import java.util.List;

import bronya.admin.module.rabbit.sdk.interceptor.RabbitAuthInterceptor;
import bronya.admin.module.rabbit.sdk.resp.RabbitConnectionsResp;
import bronya.admin.module.rabbit.sdk.resp.RabbitQueueResp;
import bronya.shared.module.rpc.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RabbitSdk {
    private final RabbitApi api;

    public RabbitSdk(String host, Integer port, String userName, String password) {
        RabbitAuthInterceptor rabbitAuthInterceptor = new RabbitAuthInterceptor(userName, password);
        this.api = new ApiClient(STR."http://\{host}:\{port}", rabbitAuthInterceptor).createService(RabbitApi.class);
    }

    public interface RabbitApi {
        @GET("/api/queues?name=&use_regex=false&sort=messages&sort_reverse=true&pagination=true")
        Call<ResponseBody> queues(@Query("page") Integer page, @Query("page_size") Integer pageSize);

        @PUT("/api/vhosts/{virtualHost}")
        Call<ResponseBody> addVirtualHost(@Path("virtualHost") String virtualHost);

        @GET("/api/connections")
        Call<ResponseBody> connections();
    }

    public List<RabbitConnectionsResp> connections() {
        return ApiClient.executeSyncList(api.connections(), RabbitConnectionsResp.class);
    }

    public RabbitQueueResp queues(Integer page, Integer pageSize) {
        return ApiClient.executeSync(api.queues(page, pageSize), RabbitQueueResp.class);
    }

    public Void addVirtualHost(String virtualHost) {
        return ApiClient.executeSync(api.addVirtualHost(virtualHost), Void.class);
    }

}
