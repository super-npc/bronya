package bronya.admin.module.media.domain;

import java.util.Date;

import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.media.proxy.SysFileProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.inputdata.AmisInputFile;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.FilePlatformType;
import bronya.shared.module.common.type.FileStatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "媒体中心")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@Amis(extBean = SysFile.SysFileExt.class, dataProxy = SysFileProxy.class)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "数据管理", menu = "存储管理"), btns = @Btns(edit = false, detail = false))
@TableIndexes({@TableIndex(fields = {SysFile.Fields.filename}, type = IndexTypeEnum.UNIQUE)})
public class SysFile extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "平台")
    private FilePlatformType platform;

    @AmisField(inputFile = @AmisInputFile.InputFile(accept = "*"),
        table = @AmisFieldView(type = AmisFieldView.ViewType.上传图片),
        add = @AmisFieldView(type = AmisFieldView.ViewType.上传图片))
    @Column(notNull = true, comment = "文件")
    private String filename;

    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "原始文件名")
    private String originalFilename;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "基础存储路径")
    private String basePath;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "存储路径")
    private String path;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "文件扩展名")
    private String ext;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "MIME类型")
    private String contentType;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图URL")
    private String thUrl;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图名称")
    private String thFilename;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图字节")
    private String thSize;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图MIME")
    private String thContentType;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "objectId")
    private String objectId;

    // @AmisField(titleDesc = "例如用户头像，评价图片")
    // @Column(comment = "对象类型")
    // private String objectType;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "文件ACL")
    private String fileAcl;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图文件ACL")
    private String thFileAcl;

    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "字节")
    private Long size;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "url")
    private String url;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "文件元数据", type = MysqlTypeConstant.TEXT)
    private String metadata;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "文件用户元数据", type = MysqlTypeConstant.TEXT)
    private String userMetadata;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图元数据", type = MysqlTypeConstant.TEXT)
    private String thMetadata;

    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "缩略图用户元数据")
    private String thUserMetadata;

    // 这里手动获 取附加属性字典 并转成 json 字符串，方便存储在数据库中
    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "附加属性")
    private String attr;

    // 这里手动获 哈希信息 并转成 json 字符串，方便存储在数据库中
    @AmisField(toggled = false, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "哈希")
    private String hashInfo;

    @AmisField(toggled = false, remark = "仅在手动分片上传时使用", add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "上传ID")
    private String uploadId;

    @AmisField(toggled = false, remark = "仅在手动分片上传时使用，1：初始化完成，2：上传完成",
        add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏), edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "上传状态")
    private Integer uploadStatus;

    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "到期时间")
    private Date expirationTime;

    @Data
    public static class SysFileExt {
        @AmisField(comment = "状态")
        private FileStatusType fileStatus;
    }
}
