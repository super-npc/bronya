package bronya.admin.module.media.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.tika.Tika;
import org.dromara.hutool.core.date.DateField;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.crypto.SecureUtil;
import org.dromara.x.file.storage.core.Downloader;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.upload.UploadPretreatment;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.BizException;
import com.google.common.collect.Lists;

import bronya.admin.cfg.ProjectYaml;
import bronya.admin.module.media.domain.SysFile;
import bronya.admin.module.media.repository.SysFileRepository;
import bronya.admin.module.media.util.XFileUtil;
import bronya.shared.module.common.type.FilePlatformType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileBizService {
    private final SysFileRepository fileRepo;
    private final FileStorageService fileService;
    private final SysFileRepository fileRepository;
    private final ProjectYaml projectYaml;

    public SysFile upload(File file) {
        FilePlatformType filePlatformType = projectYaml.getMediaCnf().getFilePlatformType();
        return this.upload(filePlatformType, file, DateField.YEAR, 5);
    }

    /**
     * 默认5年过期
     */
    public SysFile upload(FilePlatformType platform, File file) {
        return this.upload(platform, file, DateField.YEAR, 5);
    }

    @SneakyThrows
    public SysFile upload(FilePlatformType platform, File file, DateField dateField, int offset) {
        String mimeType = new Tika().detect(file);
        String fileNameMd5 = SecureUtil.md5(file);
        UploadPretreatment uploadPretreatment = fileService.of(file).setHashCalculatorMd5() // 计算 MD5
            .setHashCalculatorSha256() // 计算 SHA256
            .setPlatform(platform.name()).setPath(STR."\{mimeType}/") // 加上类型
            .setSaveFilename(fileNameMd5); // 图片名称
        List<String> ignore = Lists.newArrayList("application/"); // 忽略缩略图// "image/webp",
        if (ignore.stream().noneMatch(mimeType::startsWith)) {
            uploadPretreatment.thumbnail(th -> th.size(200, 200));
        }
//        if (!ignore.contains(mimeType) && !mimeType.startsWith()) {
//        }
        SysFile sysFile = fileRepo.findByFileName(fileNameMd5);
        if (sysFile != null) {
            FileInfo fileInfo = XFileUtil.toFileInfo(sysFile);
            if (!fileService.exists(fileInfo)) {
                log.info("数据库存在,目录文件被删除,需要重新上传:{}", file.getName());
                return XFileUtil.toFileDetail(uploadPretreatment.upload());
            }
            return sysFile;
        }
        // 不存在则上传并记录
        FileInfo fileInfo = uploadPretreatment.upload();
        sysFile = XFileUtil.toFileDetail(fileInfo);
        sysFile.setExpirationTime(DateUtil.offset(new Date(), dateField, offset));
        fileRepo.save(sysFile);
        return sysFile;
    }

    public Downloader download(Long fileId) {
        SysFile sysFile = fileRepository.getById(fileId);
        FileInfo fileInfo = XFileUtil.toFileInfo(sysFile);
        return fileService.download(fileInfo);
    }

    public Downloader downloadByFileName(String fileName) {
        SysFile sysFile = fileRepository.findByFileName(fileName);
        if (sysFile == null) {
            // 使用默认图片
            sysFile = fileRepository.findByFileName("ad98351f41d6f118c164aebea1d61138");
        }
        Assert.notNull(sysFile, () -> new BizException("未找到图片"));
        FileInfo fileInfo = XFileUtil.toFileInfo(sysFile);
        return fileService.download(fileInfo);
    }

}
