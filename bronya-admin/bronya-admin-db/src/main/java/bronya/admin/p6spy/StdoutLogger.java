package bronya.admin.p6spy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {
    @Override
    public void logText(String text) {
        log.info(text);
    }
}