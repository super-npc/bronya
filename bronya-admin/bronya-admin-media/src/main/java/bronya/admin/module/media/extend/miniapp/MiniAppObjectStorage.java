package bronya.admin.module.media.extend.miniapp;

import java.io.InputStream;
import java.util.function.Consumer;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.InputStreamPlus;
import org.dromara.x.file.storage.core.UploadPretreatment;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.dromara.x.file.storage.core.platform.FileStorageClientFactory;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;

import bronya.shared.module.common.type.FilePlatformType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.CharUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class MiniAppObjectStorage implements FileStorage {
    private final String envId;
    private final String bucketName;
    private final String basePath;
    private final FileStorageClientFactory<COSClient> clientFactory;

    @SneakyThrows
    @Override
    public boolean save(FileInfo fileInfo, UploadPretreatment pre) {
        fileInfo.setBasePath(basePath);
        InputStreamPlus inputStream = pre.getInputStreamPlus();
        String key = STR."\{fileInfo.getBasePath()}backstage/\{fileInfo.getPath()}\{fileInfo.getFilename()}";
        fileInfo.setUrl(key);
        // 创建存储对象的请求
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, new ObjectMetadata());
        // 执行上传
        PutObjectResult putObjectResult = clientFactory.getClient().putObject(putObjectRequest);
        fileInfo.setUrl(fileInfo.getUrl());
//        cloud://prod-5g3l0m5je193306f.7072-prod-5g3l0m5je193306f-1259198184/bulinke-demo/image/jpeg/b00f6b10ac7bcdf6fb2b92a2403cde05.jpg
        fileInfo.setObjectId(STR."cloud://\{envId}.\{bucketName}\{key}");
        log.info("upload success:{}", putObjectResult);
        return true;
    }

    @Override
    public void download(FileInfo fileInfo, Consumer<InputStream> consumer) {
        // 创建存储对象的请求
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileInfo.getUrl());
        COSObject object = clientFactory.getClient().getObject(getObjectRequest);
        COSObjectInputStream in = object.getObjectContent();
        consumer.accept(in);
    }

    @Override
    public boolean delete(FileInfo fileInfo) {
        clientFactory.getClient().deleteObject(bucketName, fileInfo.getUrl());
        return true;
    }

    @Override
    public boolean exists(FileInfo fileInfo) {
        return clientFactory.getClient().doesObjectExist(bucketName, fileInfo.getUrl());
    }

    @Override
    public void downloadTh(FileInfo fileInfo, Consumer<InputStream> consumer) {

    }

    @Override
    public String getPlatform() {
        return FilePlatformType.MINI_APP_CLOUD.name();
    }

    @Override
    public void setPlatform(String s) {

    }

    /**
     * 获取对象的元数据
     */
    public ObjectMetadata getObjectMetadata(FileInfo fileInfo) {
        ObjectMetadata metadata = new ObjectMetadata();
        if (fileInfo.getSize() != null)
            metadata.setContentLength(fileInfo.getSize());
        metadata.setContentType(fileInfo.getContentType());
        fileInfo.getUserMetadata().forEach(metadata::addUserMetadata);
        if (CollUtil.isNotEmpty(fileInfo.getMetadata())) {
            CopyOptions copyOptions = CopyOptions.create().ignoreCase()
                .setFieldNameEditor(name -> NamingCase.toCamelCase(name, CharUtil.DASHED));
            BeanUtil.copyProperties(fileInfo.getMetadata(), metadata, copyOptions);
        }
        return metadata;
    }

    /**
     * 获取缩略图对象的元数据
     */
    public ObjectMetadata getThObjectMetadata(FileInfo fileInfo) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileInfo.getThSize());
        metadata.setContentType(fileInfo.getThContentType());
        fileInfo.getThUserMetadata().forEach(metadata::addUserMetadata);
        if (CollUtil.isNotEmpty(fileInfo.getThMetadata())) {
            CopyOptions copyOptions = CopyOptions.create().ignoreCase()
                .setFieldNameEditor(name -> NamingCase.toCamelCase(name, CharUtil.DASHED));
            BeanUtil.copyProperties(fileInfo.getThMetadata(), metadata, copyOptions);
        }
        return metadata;
    }
}
