package bronya.admin.module.media.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.tika.Tika;
import org.apache.tika.mime.MimeTypes;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.crypto.SecureUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.hash.HashInfo;
import org.dromara.x.file.storage.core.upload.UploadPretreatment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import bronya.admin.base.util.MyFileUtil;
import bronya.admin.module.media.domain.SysFile;
import bronya.shared.module.common.type.MediaFileType;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.coobird.thumbnailator.Thumbnails;

@UtilityClass
public class XFileUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Tika tika = Singleton.get(Tika.class);
    // 已知的拓展名
    private final static List<String> knownExtList = Lists.newArrayList("ts", "onnx");
    private final FileStorageService fileStorageService = SpringUtil.getBean(FileStorageService.class);

    @SneakyThrows
    public File compressToWebp(File file) {
        String mimeType = new Tika().detect(file);
        if ("image/webp".equals(mimeType)) {
            return file;
        }
        if (!mimeType.startsWith("image/")) {
            return file;
        }
        // 转非webp
        File webp = MyFileUtil.createTemplateFile(FileNameUtil.mainName(file), "webp");
        Thumbnails.of(file)
            // 设置输出格式
            .outputFormat("webp").scale(1)
            // 设置图片质量（0.0 - 1.0）
            .outputQuality(0.8)
            // 保存到指定路径
            .toFile(webp);
        return webp;
    }

    @SneakyThrows
    public FileInfo upload(String platform, String newPath, File file, String saveFileMainName) {
        // 检测文件MIME类型
        String mimeType = tika.detect(file);
        // 如果是已知的拓展名则不需要获取
        String extension = FileUtil.extName(file);
        // todo onnx 不生效
        if (knownExtList.contains(extension)) {
            extension = STR.".\{extension}";
        } else {
            // 不知道的拓展名才需要计算
            MimeTypes mimeTypes = MimeTypes.getDefaultMimeTypes();
            extension = mimeTypes.forName(mimeType).getExtension();
        }
        if (StrUtil.isNotBlank(saveFileMainName)) {
            saveFileMainName = STR."\{saveFileMainName}\{extension}";
        } else {
            saveFileMainName = STR."\{SecureUtil.md5(file)}\{extension}";
        }

        // 构建文件路径和名称
                String path = STR."\{mimeType}/";
        if (StrUtil.isNotBlank(newPath)) {
            path = newPath;
        }

        // 准备上传
        UploadPretreatment uploadPretreatment = fileStorageService.of(file).setHashCalculatorMd5() // 计算 MD5
            .setHashCalculatorSha256() // 计算 SHA256
            .setPlatform(platform).setPath(path).setSaveFilename(saveFileMainName);
        MediaFileType fileTypeByMimeType = XFileUtil.getFileTypeByMimeType(mimeType);
        if (fileTypeByMimeType == MediaFileType.IMAGE && !"image/webp".equals(mimeType)) { // webp无法生成缩略图,跳过
            uploadPretreatment.thumbnail(th -> th.size(200, 200)); // 非图片生成缩略图会失败
            // 如果是图片放在微信云托管
            uploadPretreatment.setPlatform(platform);
        }
        return uploadPretreatment.upload();
    }

    /**
     * 将 FileInfo 转为 FileDetail
     */
    public SysFile toFileDetail(FileInfo info) {
        SysFile detail = BeanUtil.copyProperties(info, SysFile.class, "metadata", "userMetadata", "thMetadata",
            "thUserMetadata", "attr", "hashInfo");
        detail.setPath(info.getPath());
        detail.setFilename(info.getFilename());

        // 这里手动获 元数据 并转成 json 字符串，方便存储在数据库中
        detail.setMetadata(valueToJson(info.getMetadata()));
        detail.setUserMetadata(valueToJson(info.getUserMetadata()));
        detail.setThMetadata(valueToJson(info.getThMetadata()));
        detail.setThUserMetadata(valueToJson(info.getThUserMetadata()));
        // 这里手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
        detail.setAttr(valueToJson(info.getAttr()));
        // 这里手动获 哈希信息 并转成 json 字符串，方便存储在数据库中
        detail.setHashInfo(valueToJson(info.getHashInfo()));
        return detail;
    }

    /**
     * 将 FileDetail 转为 FileInfo
     */
    public FileInfo toFileInfo(SysFile detail) {
        FileInfo info = BeanUtil.copyProperties(detail, FileInfo.class, "metadata", "userMetadata", "thMetadata",
            "thUserMetadata", "attr", "hashInfo");

        // 这里手动获取数据库中的 json 字符串 并转成 元数据，方便使用
        info.setMetadata(jsonToMetadata(detail.getMetadata()));
        info.setUserMetadata(jsonToMetadata(detail.getUserMetadata()));
        info.setThMetadata(jsonToMetadata(detail.getThMetadata()));
        info.setThUserMetadata(jsonToMetadata(detail.getThUserMetadata()));
        // 这里手动获取数据库中的 json 字符串 并转成 附加属性字典，方便使用
        info.setAttr(jsonToDict(detail.getAttr()));
        // 这里手动获取数据库中的 json 字符串 并转成 哈希信息，方便使用
        // info.setHashInfo(jsonToHashInfo(detail.getHashInfo()));
        return info;
    }

    /**
     * 将指定值转换成 json 字符串
     */
    @SneakyThrows
    public String valueToJson(Object value) {
        if (value == null)
            return null;
        return objectMapper.writeValueAsString(value);
    }

    /**
     * 将 json 字符串转换成元数据对象
     */
    @SneakyThrows
    public Map<String, String> jsonToMetadata(String json) {
        if (StrUtil.isBlank(json))
            return null;
        return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {});
    }

    /**
     * 将 json 字符串转换成字典对象
     */
    @SneakyThrows
    public Dict jsonToDict(String json) {
        if (StrUtil.isBlank(json))
            return null;
        return objectMapper.readValue(json, Dict.class);
    }

    /**
     * 将 json 字符串转换成哈希信息对象
     */
    @SneakyThrows
    public HashInfo jsonToHashInfo(String json) {
        if (StrUtil.isBlank(json))
            return null;
        return objectMapper.readValue(json, HashInfo.class);
    }

    /**
     * 根据 MIME 类型前缀返回文件类型
     */
    public MediaFileType getFileTypeByMimeType(String mimeType) {
        if (mimeType.startsWith("image/")) {
            return MediaFileType.IMAGE;
        } else if (mimeType.startsWith("video/")) {
            return MediaFileType.VIDEO;
        } else if (mimeType.startsWith("audio/")) {
            return MediaFileType.AUDIO;
        }
        return MediaFileType.OTHER;
    }
}
