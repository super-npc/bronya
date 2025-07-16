package npc.bulinke.external.module.sdk.telegram;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okex.open.api.utils.DateUtils;
import lombok.SneakyThrows;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://rubenlagus.github.io/TelegramBotsDocumentation/lesson-2.html#let-s-respect-telegram-s-servers" />
 */
public class MyAmazingBotTest {
    @SneakyThrows
    @Test
    public void consume() {
        OkHttpClient okHttpClient = this.okClientHttp();
        TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication(ObjectMapper::new, () -> okHttpClient);
        // Register our bot
        String botToken = "7997679214:AAFoge4soYHriX0QNMXMhA-2atxD2EWXs_4";
        TelegramClient telegramClient = new OkHttpTelegramClient(okHttpClient, botToken);
        BotSession botSession = botsApplication.registerBot(botToken, new MyAmazingBot(telegramClient));

        System.out.println("MyAmazingBot successfully started!");
        ThreadUtil.sleep(20, TimeUnit.HOURS);
    }

    public OkHttpClient okClientHttp() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 55131));
        return new TelegramOkHttpClientFactory.SocksProxyOkHttpClientCreator(
                () -> proxy).get();
    }

    public OkHttpClient client() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 55131));
        final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(3, TimeUnit.SECONDS);
        clientBuilder.readTimeout(3, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(3, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);
        clientBuilder.proxy(proxy);
        clientBuilder.addInterceptor((Interceptor.Chain chain) -> {
            final Request.Builder requestBuilder = chain.request().newBuilder();
            final String timestamp = DateUtils.getUnixTime();
            //打印首行时间戳
//            System.out.println("时间戳timestamp={" + timestamp + "}");
//              设置模拟盘请求头
//            String simulated = "1";
//            requestBuilder.headers(this.headers(chain.request(), timestamp));
            final Request request = requestBuilder.build();
//            if (this.config.isPrint()) {
//                this.printRequest(request, timestamp);
//            }
            return chain.proceed(request);
        });
        return clientBuilder.build();
    }

}