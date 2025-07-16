package bronya.admin.module.media.started;

import java.io.File;
import java.io.InputStream;

import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.io.resource.ResourceUtil;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.module.media.service.SysFileBizService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediaInitDataService implements ApplicationListener<ApplicationReadyEvent> {
    private final SysFileBizService bizService;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        InputStream stream = ResourceUtil.getResource("static/img/ad98351f41d6f118c164aebea1d61138.jpg").getStream();
        @Cleanup("deleteOnExit")
        File tempFile = FileUtil.createTempFile();
        File file = FileUtil.writeFromStream(stream, tempFile,true);
        bizService.upload(file);
    }
}