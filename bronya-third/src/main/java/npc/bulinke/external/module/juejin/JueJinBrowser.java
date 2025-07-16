package npc.bulinke.external.module.juejin;

import jodd.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JueJinBrowser {
    private final HttpSession session = new HttpSession();
    private final String cookie;

    public void checkIn(){

    }


}
