package bronya.admin.module.db.ssh.domain;

import java.util.Date;

import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.ssh.proxy.SshDoProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(comment = "跳板机")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@Amis(extBean = SshDo.SshCfgExt.class, dataProxy = SshDoProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "配置", menu = "系统配置"),
    operation = @Operation(optBtns = {@Operation.OptBtn(name = "验证exec", level = Operation.BtnLevelType.info,
        method = Method.GET, url = "/admin/ssh/exec-echo")}))
@TableIndexes({@TableIndex(fields = {SshDo.Fields.host, SshDo.Fields.port}, type = IndexTypeEnum.UNIQUE)})
public class SshDo extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "状态", defaultValue = "true")
    @AmisField(search = true, sortable = true)
    private Boolean enable;

    @Column(notNull = true, comment = "名称", defaultValue = "自动生成")
    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private String name;

    @Column(notNull = true, comment = "host", defaultValue = "47.130.49.4")
    @AmisField
    private String host;

    @Column(notNull = true, comment = "端口", defaultValue = "57122")
    @AmisField
    private Integer port;

    @Column(notNull = true, comment = "鉴权类型", defaultValue = "NONE")
    @AmisField(sortable = true, search = true)
    private AuthType authType;

    @Column(notNull = true, comment = "用户名", defaultValue = "devops")
    @AmisField
    private String userName;

    @Column(notNull = true, comment = "密码", defaultValue = "****")
    @AmisField(sensitive = @AmisField.Sensitive(type = AmisField.SensitiveType.yes),
        table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private String password;

    @Column(notNull = true, comment = "私钥路径",
        defaultValue = "/home/chenwenxi/npc/git/bronya/bronya-demo/bronya-demo-company/src/main/resources/hotpb/devops.pem")
    @AmisField(table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private String keyFile;

    @Column(notNull = true, comment = "代理", defaultValue = "true")
    @AmisField(table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private Boolean proxyEnable;

    @Column(notNull = true, comment = "关联代理")
    @BindMany2One(entity = ProxyDo.class, valueField = ProxyDo.Fields.id, labelField = ProxyDo.Fields.name)
    @AmisField(table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private Long proxyId;

    @Data
    public static class SshCfgExt {
        @AmisField(comment = "运行信息",
                editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true),
                table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
                detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String running;
    }

    @Override
    public String toString() {
        return STR."ssh客户端 '\{host}:\{port}, authType:\{authType}, userName:\{userName}, 代理:\{proxyEnable}";
    }

    @Getter
    @AllArgsConstructor
    public enum SshStatus implements AmisEnum {
        INIT("初始化", Color.卡其布), NORMAL("正常", Color.纯绿), NOT_AUTHENTICATED("鉴权失败", Color.深红色),
        NOT_CONNECTED("连接失败", Color.深红色);

        private final String desc;
        private final Color color;
    }

    @Getter
    @AllArgsConstructor
    public enum AuthType implements AmisEnum {
        NONE("无", Color.卡其布), AUTH_PUBLIC_KEY("私钥", Color.纯绿), AUTH_PASSWORD("密码", Color.深红色);

        private final String desc;
        private final Color color;
    }
}
