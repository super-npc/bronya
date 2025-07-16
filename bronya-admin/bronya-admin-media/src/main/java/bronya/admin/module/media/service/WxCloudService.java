package bronya.admin.module.media.service;

import java.io.File;

import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.x.file.storage.core.platform.FileStorageClientFactory;
import org.springframework.stereotype.Service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;

import bronya.admin.base.util.MyFileUtil;
import bronya.admin.module.media.StorageRegister;
import bronya.admin.module.media.extend.miniapp.MiniAppObjectStorage;
import bronya.shared.module.util.WxCloudUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class WxCloudService {
    private final MiniAppObjectStorage miniAppObjectStorage;

    public void delete(String url) {
        String fileId = WxCloudUtil.convertUrlToFileId(url);
        FileStorageClientFactory<COSClient> clientFactory = miniAppObjectStorage.getClientFactory();
        COSClient wxCos = clientFactory.getClient();
        WxCloudUtil.CloudPathInfo cloudPathInfo = WxCloudUtil.parseCloudPath(fileId);
        String bucketName = cloudPathInfo.getBucketName();
        String standardPath = this.convertWxCloudUrlToStandardPath(fileId);
        log.debug("删除文件的变量: bucketName={}, standardPath={}", bucketName, standardPath);
        wxCos.deleteObject(bucketName, standardPath);
        log.info("删除文件完成: {}", url);
    }

    /**
     * 将WxCloud URL转换为标准路径格式
     *
     * @param wxCloudUrl WxCloud URL
     * @return 标准路径格式
     */
    public String convertWxCloudUrlToStandardPath(String wxCloudUrl) {
        // 使用正则表达式匹配WxCloud URL的路径部分
        String regex = "cloud://[^/]+/([^/]+/.+)";
        return wxCloudUrl.replaceAll(regex, "/$1");
    }

    /**
     * 下载文件
     *
     * @param url https://7072-prod-5g3l0m5je193306f-1259198184.tcb.qcloud.la/dev/melody-app-service-app/backstage/image/png/3da2fa5d6b37b9acc3b739e859ad91a7
     *            或者:
     *            cloud://prod-5g3l0m5je193306f.7072-prod-5g3l0m5je193306f-1259198184/dev/nobita/miniapp/wx13754984438a1b4d/general/5f8d521b4c828e1dd5d6fb1b87d865a4.png
     * @return file
     */
    public File download(String url) {
        String cloudUrl = WxCloudUtil.convertUrlToFileId(url);
        WxCloudUtil.CloudPathInfo cloudPathInfo = WxCloudUtil.parseCloudPath(cloudUrl);
        GetObjectRequest getObjectRequest = new GetObjectRequest(StorageRegister.bucketName, cloudPathInfo.getFileKey());
        COSClient client = miniAppObjectStorage.getClientFactory().getClient();
        COSObject object = client.getObject(getObjectRequest);
        COSObjectInputStream in = object.getObjectContent();
        File tempFile = MyFileUtil.createTemplateFile(RandomUtil.randomString(10));
        FileUtil.copy(in, tempFile);
        return tempFile;
    }
}
