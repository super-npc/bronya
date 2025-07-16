package npc.bulinke.external.module.speeder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SpeederBrowserTest {

    @Test
    public void login() {
        SpeederBrowser speederClient = new SpeederBrowser("https://45.62.117.198:10520");
        speederClient.initBrowserAndOpenIndex();
        speederClient.login("lianlianyi@vip.qq.com", "Temp12138.");
        boolean checkInHtmlContent = speederClient.getCheckInHtmlContent();
        if (!checkInHtmlContent) {
            // 已经签到过了
            return;
        }
        log.info("进行领取");
    }

}