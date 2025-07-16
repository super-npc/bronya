package npc.bulinke.external.module.speeder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import jodd.jerry.Jerry;
import jodd.util.Wildcard;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.speeder.resp.CheckInResp;
import org.dromara.hutool.core.text.UnicodeUtil;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.HttpSession;
import lombok.RequiredArgsConstructor;
import npc.bulinke.external.module.speeder.resp.LoginResp;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.dromara.hutool.core.util.RandomUtil;

/**
 * 官网主站 https://panel.speeder.one
 *
 * 备用地址一 https://45.62.117.198:10520
 *
 * 备用地址二 https://panel.speeder.cfd
 *
 * 备用地址三 https://panel.speeder.eu.org
 *
 * 备用地址四 https://speeder.mobilegameanalysis.art
 *
 * 备用地址五 https://98.126.19.75:10520
 *
 * 备用地址六 https://45.62.117.198:10521
 *
 * 请收藏 https://speeder.pages.dev 和 https://go.speeder.pp.ua，打开可自动跳转可用网址
 */
@Slf4j
@RequiredArgsConstructor
public class SpeederBrowser {
    private final HttpSession session = new HttpSession();
    private final String host;

    public void initBrowserAndOpenIndex() {
        log.info("访问下首页");
        // 模拟手动点击首页
        HttpRequest request = HttpRequest.get(STR."\{host}/auth/login");
        session.sendRequest(request);
    }

    // /**
    // curl 'https://45.62.117.198:10520/auth/login' \
    // -H 'accept: application/json, text/javascript, */*; q=0.01' \
    // -H 'accept-language: zh-CN,zh;q=0.9' \
    // -H 'content-type: application/x-www-form-urlencoded; charset=UTF-8' \
    // -b 'lang=zh-cn; ip=9e0adc936e55d59f2ad939bd8fb672a8; expire_in=1750837272' \
    // -H 'origin: https://45.62.117.198:10520' \
    // -H 'priority: u=1, i' \
    // -H 'referer: https://45.62.117.198:10520/auth/login' \
    // -H 'sec-ch-ua: "Google Chrome";v="137", "Chromium";v="137", "Not/A)Brand";v="24"' \
    // -H 'sec-ch-ua-mobile: ?0' \
    // -H 'sec-ch-ua-platform: "Linux"' \
    // -H 'sec-fetch-dest: empty' \
    // -H 'sec-fetch-mode: cors' \
    // -H 'sec-fetch-site: same-origin' \
    // -H 'user-agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0
    // Safari/537.36' \
    // -H 'x-requested-with: XMLHttpRequest' \
    // --data-raw 'email=lianlianyi%40vip.qq.com&passwd=Temp12138.&code=' ;
    // 帮我补上这份代码的调用 */
    public LoginResp login(String email, String password) {
        ThreadUtil.sleep(RandomUtil.randomInt(5,20), TimeUnit.SECONDS);
        // create a new request
        Map<String, Object> form = Maps.newHashMap();
        form.put("email", email);
        form.put("passwd", password);
        form.put("code", "");

        HttpRequest newRequest = HttpRequest.post(STR."\{host}/auth/login").form(form);
        HttpResponse httpResponse = session.sendRequest(newRequest);
        String json = UnicodeUtil.toString(httpResponse.bodyText());
        log.info("用户登录:{}", json);
        return JSONObject.parseObject(json, LoginResp.class);
    }

    public boolean getCheckInHtmlContent(){
        ThreadUtil.sleep(RandomUtil.randomInt(5,20), TimeUnit.SECONDS);
        HttpRequest httpRequest = HttpRequest.get(STR."\{host}/user");
        HttpResponse httpResponse = session.sendRequest(httpRequest);
        String html = UnicodeUtil.toString(httpResponse.bodyText());
        Jerry doc = Jerry.of(html);
        boolean match = Wildcard.match(doc.s("div#checkin-div").text(), "*每日签到*");
        log.info("页面检查签到:{}", match);
        return match;
    }

    public CheckInResp checkIn(){
        ThreadUtil.sleep(RandomUtil.randomInt(5,20), TimeUnit.SECONDS);
        // {"msg":"\u4f60\u83b7\u5f97\u4e86 511 MB\u6d41\u91cf","ret":1}
        HttpRequest httpRequest = HttpRequest.post(STR."\{host}/user/checkin");
        HttpResponse httpResponse = session.sendRequest(httpRequest);
        String json = UnicodeUtil.toString(httpResponse.bodyText());
        log.info("签到结果:{}", json);
        return JSONObject.parseObject(json, CheckInResp.class);
    }
}
