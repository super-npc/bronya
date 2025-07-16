package bronya.admin.module.db.forwarder.domain;

import java.util.Date;

import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

/**
 * 远程端口 → SSH服务器 → 本地端口
 * 主要用于将远程请求转发到本地主机，适合将内网服务暴露到公网。
 */
@Data
@Table(comment = "远程端口转发")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis
@AmisPage(interval = 5000,menu = @Menu(module = "系统", group = "配置", menu = "系统配置"))
public class RemotePortForwarderDo extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField
    private Long id;
}
