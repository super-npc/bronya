package bronya.admin.base.util;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.dromara.hutool.core.date.DateFormatPool;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.hutool.core.util.SystemUtil;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MyFileUtil {
    @SneakyThrows
    public File convert(MultipartFile file) {
        File tempFile = createTemplateFile(file.getOriginalFilename());
        file.transferTo(tempFile);
        return tempFile;
    }

    public File createTemplateFile(String name) {
        return new File(SystemUtil.getTmpDirPath(),
            STR."\{DateUtil.format(new Date(), DateFormatPool.PURE_DATETIME_PATTERN)}-\{RandomUtil.randomString(4)}-\{name}");
    }

    public File createTemplateFile(String name,String suffix) {
        return new File(SystemUtil.getTmpDirPath(),
                STR."\{DateUtil.format(new Date(), DateFormatPool.PURE_DATETIME_PATTERN)}-\{RandomUtil.randomString(4)}-\{name}.\{suffix}");
    }

    public File convert(InputStream inputStream) {
        File tempFile = FileUtil.createTempFile("temp-file-", true);
        FileUtil.writeFromStream(inputStream, tempFile);
        return tempFile;
    }
}
