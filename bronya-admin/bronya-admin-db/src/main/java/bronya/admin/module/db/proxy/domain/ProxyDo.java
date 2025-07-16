package bronya.admin.module.db.proxy.domain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Optional;

import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.shared.module.util.Md;
import net.steppschuh.markdowngenerator.text.quote.Quote;
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

import bronya.admin.module.db.proxy.proxy.ProxyDoProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
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
@Table(comment = "代理")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@Amis(extBean = ProxyDo.ProxyExt.class, dataProxy = ProxyDoProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "配置", menu = "系统配置"))
@TableIndexes({@TableIndex(fields = {ProxyDo.Fields.proxyType, ProxyDo.Fields.host, ProxyDo.Fields.port}, type = IndexTypeEnum.UNIQUE)})
public class ProxyDo extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "状态", defaultValue = "true")
    @AmisField(search = true, sortable = true)
    private Boolean enable;

    @Column(notNull = true,comment = "名称", defaultValue = "自动生成")
    @AmisField(add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private String name;

    @Column(notNull = true, comment = "类型", defaultValue = "DIRECT")
    @AmisField(sortable = true, search = true)
    private ProxyType proxyType;

    @Column(notNull = true, comment = "host", defaultValue = "127.0.0.1")
    @AmisField
    private String host;

    @Column(notNull = true, comment = "端口", defaultValue = "55131")
    @AmisField
    private Integer port;

    public ProxyDo() {
    }

    public ProxyDo(ProxyType proxyType, String host, Integer port) {
        this.proxyType = proxyType;
        this.host = host;
        this.port = port;
    }

    @Data
    public static class ProxyExt {
        @AmisField(comment = "运行信息",
                editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true),
                table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
                detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String running;
    }

    @Getter
    @AllArgsConstructor
    public enum ProxyType implements AmisEnum {
        DIRECT("直连", Color.纯绿), HTTP("http", Color.深红色), SOCKS("socks", Color.靛青);

        private final String desc;
        private final Color color;
    }
}
