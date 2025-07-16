package bronya.admin.module.media;

import java.util.concurrent.CopyOnWriteArrayList;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.FileStorageServiceBuilder;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.dromara.x.file.storage.core.platform.FileStorageClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.region.Region;

import bronya.admin.module.media.extend.miniapp.MiniAppObjectStorage;
import bronya.admin.module.media.extend.miniapp.MiniAppObjectStorageClientFactory;
import bronya.admin.module.media.extend.miniapp.SysFileStorageRecorder;
import bronya.admin.module.media.extend.miniapp.dto.MiniAppStorageConfig;
import bronya.shared.module.common.type.FilePlatformType;
import bronya.shared.module.util.SystemKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StorageRegister {
    private final SysFileStorageRecorder sysFileStorageRecorder;
    public final static String bucketName = "7072-prod-5g3l0m5je193306f-1259198184";

    @Bean
    @Lazy
    public MiniAppObjectStorage miniAppObjectStorage() {
        // 默认使用微信.云托管.对象存储
        MiniAppStorageConfig config = new MiniAppStorageConfig();
        config.setSecretId("AKIDLXYMwJWXPTL1Wr21nNlxxmQs7Bl9LKAn");
        config.setSecretKey("Xs1yiFaidLqxteFnKi3FKGAvXzIRRGuQ");
        config.setToken("gpt_dfsflj2djdj");
        config.setRegion(new Region("ap-shanghai"));

        String envId = "prod-5g3l0m5je193306f";
        FileStorageClientFactory<COSClient> clientFactory = new MiniAppObjectStorageClientFactory(config);

        String profilesActive = SystemKit.getProfilesActive();
        MiniAppObjectStorage miniAppObjectStorage = new MiniAppObjectStorage(envId, bucketName,
            STR."/\{profilesActive}/\{SpringUtil.getApplicationName()}/", clientFactory);
        miniAppObjectStorage.setPlatform(FilePlatformType.MINI_APP_CLOUD.name());
        return miniAppObjectStorage;
    }

    /**
     * 小程序云托管 存储 Bean ，注意返回值必须是个 List
     */
    @Bean
    @Lazy
    public FileStorageService myFileStorageService(MiniAppObjectStorage miniAppObjectStorage) {
        FileStorageProperties properties = new FileStorageProperties();
        properties.setDefaultPlatform(FilePlatformType.LOCAL.name());
        properties.setThumbnailSuffix(".min.jpg"); // 缩略图后缀

        FileStorageProperties.LocalPlusConfig localPlusConfig = new FileStorageProperties.LocalPlusConfig();
        localPlusConfig.setPlatform(FilePlatformType.LOCAL.name()); // 必填
        localPlusConfig.setStoragePath("D:\\upload-file"); // 存储路径,必填
        localPlusConfig.setBasePath("/my-base/"); // 基础路径,可以使用项目名称进行区分,必填
        // localPlusConfig.setDomain("http://abc.obs.com/");
        properties.setLocalPlus(Lists.newArrayList(localPlusConfig));

        FileStorageService fileStorageService = FileStorageServiceBuilder.create(properties)
            .addFileStorage(miniAppObjectStorage).useDefault().setFileRecorder(sysFileStorageRecorder).build();

        CopyOnWriteArrayList<FileStorage> fileStorageList = fileStorageService.getFileStorageList();
        return fileStorageService;
    }
}
