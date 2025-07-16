package cc.bronya;

import java.util.concurrent.TimeUnit;

import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.WebSocket;
import cn.zhxu.okhttps.gson.GsonMsgConvertor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WsClient {
    private final String baseUrl;
    public HTTP genWsHttpClient() {
        HTTP.Builder builder = HTTP.builder().addMsgConvertor(new GsonMsgConvertor());
        builder.baseUrl(baseUrl);
        final HTTP.OkConfig okConfig = proxyBuilder -> {
            // 配置 WebSocket 心跳间隔（默认没有心跳）
            proxyBuilder.pingInterval(10, TimeUnit.SECONDS);
            // proxyBuilder.proxy(robotYamlService.getProxy());
        };
        builder.config(okConfig);
        return builder.build();
    }

    public boolean handleWebSocketStatus(int status, String streamName) {
        boolean needReconnect = false;

        switch (status) {
            case WebSocket.STATUS_CONNECTING:
                log.debug("[{}] WebSocket正在连接", streamName);
                break;
            case WebSocket.STATUS_CONNECTED:
                // 连接正常，不需要处理
                break;
            case WebSocket.STATUS_DISCONNECTED:
            case WebSocket.STATUS_CANCELED:
            case WebSocket.STATUS_TIMEOUT:
            case WebSocket.STATUS_NETWORK_ERROR:
            case WebSocket.STATUS_EXCEPTION:
                log.error("[{}] WebSocket连接状态异常: {}", streamName, status);
                needReconnect = true;
                break;
            default:
                log.warn("[{}] 未知的WebSocket状态: {}", streamName, status);
                break;
        }

        return needReconnect;
    }
}
