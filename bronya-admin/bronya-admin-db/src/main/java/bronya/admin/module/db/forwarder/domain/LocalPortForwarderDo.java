package bronya.admin.module.db.forwarder.domain;

import java.util.Date;

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

import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.showdata.AmisLink;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import bronya.admin.module.db.forwarder.proxy.LocalPortForwarderDoProxy;

/**
 * 本地端口 → SSH服务器 → 远程端口 主要用于将本地请求转发到远程服务器，适合访问远程服务。
 */
@Data
@Table(comment = "本地端口转发")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis(extBean = LocalPortForwarderDo.LocalPortForwarderExt.class, dataProxy = LocalPortForwarderDoProxy.class)
@AmisPage(interval = 5000, menu = @Menu(module = "系统", group = "配置", menu = "系统配置"),
    operation = @Operation(optBtns = {
        @Operation.OptBtn(name = "绑定", level = Operation.BtnLevelType.info, method = Method.GET, batch = true,
            url = "/admin/local-port-forwarder/bind"),
        @Operation.OptBtn(name = "重新绑定", level = Operation.BtnLevelType.info, method = Method.GET, batch = true,
            url = "/admin/local-port-forwarder/re-bind")}))
@TableIndexes({
    @TableIndex(
        fields = {LocalPortForwarderDo.Fields.targetHost, LocalPortForwarderDo.Fields.targetPort,
            LocalPortForwarderDo.Fields.localHost, LocalPortForwarderDo.Fields.localPort},
        type = IndexTypeEnum.UNIQUE)})
public class LocalPortForwarderDo extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField
    private Long id;

    @Column(notNull = true, comment = "状态", defaultValue = "true")
    @AmisField(search = true, sortable = true)
    private Boolean enable;

    @Column(notNull = true, comment = "名称", defaultValue = "true")
    @AmisField(search = true, sortable = true,
        link = @AmisLink.Link(body = "$LocalPortForwarderDo__name",
            href = "http://127.0.0.1:$LocalPortForwarderDo__localPort"),
        table = @AmisFieldView(type = AmisFieldView.ViewType.链接))
    private String name;

    @Column(notNull = true, comment = "跳板机")
    @BindMany2One(entity = SshDo.class, valueField = SshDo.Fields.id, labelField = SshDo.Fields.name)
    @AmisField
    private Long sshId;

    @Column(notNull = true, comment = "目标服务器", defaultValue = "172.31.34.42")
    @AmisField(quickEdit = true, search = true, sortable = true, copyable = true)
    private String targetHost;
    @Column(notNull = true, comment = "目标端口", defaultValue = "3308")
    @AmisField(quickEdit = true, search = true, sortable = true, copyable = true)
    private Integer targetPort;

    @Column(notNull = true, comment = "本地host", defaultValue = "127.0.0.1")
    @AmisField(quickEdit = true, search = true, sortable = true, copyable = true)
    private String localHost;
    @Column(notNull = true, comment = "本地端口", defaultValue = "3308")
    @AmisField(quickEdit = true, search = true, sortable = true, copyable = true)
    private Integer localPort;

    @Data
    public static class LocalPortForwarderExt {
        @Column(comment = "状态")
        @AmisField(search = true)
        private ForwarderStatus status;
    }

    @Getter
    @AllArgsConstructor
    public enum ForwarderStatus implements AmisEnum {
        INIT("初始化", Color.卡其布), SUCCESS("正常", Color.深绿色), SSH_ERROR("跳板机错误", Color.热情的粉红),
        FORWARDER_NOT_RUNNING("未穿透", Color.热情的粉红), SS_NOT_BOUND("socket未绑定", Color.热情的粉红),
        SS_CLOSE("socket关闭", Color.热情的粉红);

        private final String desc;
        private final Color color;
    }

    @Override
    public String toString() {
        return STR."本地端口转发 \{targetHost}:\{targetPort} -> \{localHost}:\{localPort}";
    }
}
