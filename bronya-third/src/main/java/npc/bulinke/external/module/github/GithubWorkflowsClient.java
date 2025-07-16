package npc.bulinke.external.module.github;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import bronya.shared.module.rpc.ApiClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

@Data
public class GithubWorkflowsClient {
    private final GithubWorkflowsApi api;

    public GithubWorkflowsClient() {
        GithubAuthInterceptor authInterceptor = new GithubAuthInterceptor(
            "");
        this.api = new ApiClient("https://api.github.com/", authInterceptor).createService(GithubWorkflowsApi.class);
    }

    public interface GithubWorkflowsApi {
        @POST("repos/{project}/dispatches")
        Call<ResponseBody> runAction(@Path(value = "project", encoded = true) String project,
            @Body GithubWorkflowsParam param);
    }

    public String runAction(String project, GithubWorkflowsParam param) {
        return ApiClient.executeSync(api.runAction(project, param), String.class);
    }

    @Slf4j
    @RequiredArgsConstructor
    public static class GithubAuthInterceptor implements Interceptor {
        private final String token;

        @NotNull
        @SneakyThrows
        @Override
        public okhttp3.Response intercept(Chain chain) {
            Request.Builder requestBuilder = chain.request().newBuilder()
                .header("Accept", "application/vnd.github.v3+json").header("Authorization", STR."Bearer \{token}");
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    @Data
    public static class GithubWorkflowsParam {
        private String event_type;
        private Map<String, String> client_payload;
    }
}
