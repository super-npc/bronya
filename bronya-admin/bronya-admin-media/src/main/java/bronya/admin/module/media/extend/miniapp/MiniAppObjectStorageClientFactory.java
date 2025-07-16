package bronya.admin.module.media.extend.miniapp;

import org.dromara.x.file.storage.core.platform.FileStorageClientFactory;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;

import bronya.admin.module.media.extend.miniapp.dto.MiniAppStorageConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MiniAppObjectStorageClientFactory implements FileStorageClientFactory<COSClient> {
    private volatile COSClient client;
    private final MiniAppStorageConfig storageConfig;

    @Override
    public String getPlatform() {
        return storageConfig.getPlatform();
    }

    @Override
    public COSClient getClient() {
        if (client != null) {
            return client;
        }
        synchronized (this) {
            if (client == null) {
                ClientConfig clientConfig = new ClientConfig(storageConfig.getRegion());
                COSCredentials cosCredentials = new BasicSessionCredentials(storageConfig.getSecretId(),
                        storageConfig.getSecretKey(), storageConfig.getToken());
                // 生成cos客户端
                client = new COSClient(cosCredentials, clientConfig);
            }
        }
        return client;
    }

    @Override
    public void close() {
        client.shutdown();
    }
}
