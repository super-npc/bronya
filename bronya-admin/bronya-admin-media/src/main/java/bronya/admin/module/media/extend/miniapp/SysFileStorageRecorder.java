package bronya.admin.module.media.extend.miniapp;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.stereotype.Service;

import bronya.admin.module.media.domain.SysFile;
import bronya.admin.module.media.repository.SysFileRepository;
import bronya.admin.module.media.util.XFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileStorageRecorder implements FileRecorder {
    private final SysFileRepository fileRepository;

    @Override
    public void update(FileInfo fileInfo) {
        System.out.println("fileInfo = " + fileInfo);
    }

    @Override
    public FileInfo getByUrl(String url) {
        SysFile byUrl = fileRepository.findByUrl(url);
        return XFileUtil.toFileInfo(byUrl);
    }

    @Override
    public boolean delete(String url) {
        return false;
    }

    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        System.out.println("filePartInfo = " + filePartInfo);
    }

    @Override
    public void deleteFilePartByUploadId(String s) {
        System.out.println("s = " + s);
    }

    /**
     * 不用这个保存,但需要返回true,不然返回null
     */
    @Override
    @Deprecated
    public boolean save(FileInfo fileInfo) {
        return true;
    }
}
