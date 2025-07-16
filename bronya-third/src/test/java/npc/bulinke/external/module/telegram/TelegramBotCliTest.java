package npc.bulinke.external.module.telegram;


import npc.bulinke.external.module.telegram.req.SendMessageReq;
import npc.bulinke.external.module.telegram.resp.GetMeResp;
import npc.bulinke.external.module.telegram.resp.SendMessageResp;
import npc.bulinke.external.module.telegram.resp.TelegramRespBase;
import npc.bulinke.external.module.telegram.resp.GetUpdateResp;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class TelegramBotCliTest {
    final String token = "7997679214:AAFoge4soYHriX0QNMXMhA-2atxD2EWXs_4";
    final Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1",55131));
    final TelegramBotCli telegramBotCli = new TelegramBotCli(proxy);

    @Test
    public void getMe() {
        TelegramRespBase<GetMeResp> resp = telegramBotCli.getMe(token);
        GetMeResp result = resp.getResult();
        System.out.println(STR."result = \{result}");
    }

    @Test
    public void sendMessage() {
        SendMessageReq sendMessageReq = new SendMessageReq("-1002571018421","你好1");
        TelegramRespBase<SendMessageResp> sendMessageRespTelegramRespBase = telegramBotCli.sendMessage(token, sendMessageReq);
    }

    @Test
    public void getUpdates() {
        TelegramRespBase<GetUpdateResp[]> updates = telegramBotCli.getUpdates(token);
        System.out.println("updates = " + updates);
    }
}