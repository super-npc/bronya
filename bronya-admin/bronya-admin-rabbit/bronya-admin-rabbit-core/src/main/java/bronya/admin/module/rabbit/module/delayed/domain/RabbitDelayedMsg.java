package bronya.admin.module.rabbit.module.delayed.domain;

import java.util.Date;

import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.annotation.InsertFillTime;
import org.dromara.mpe.annotation.InsertUpdateFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.rabbit.type.MqStatus;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "延迟消息")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "rabbit", menu = "延迟"),
    btns = @Btns(add = false, edit = false, delete = false))
public class RabbitDelayedMsg {

    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField
    @Column(notNull = true, comment = "状态")
    private MqStatus status;

    @AmisField(toggled = false)
    @Column(comment = "broker失败原因")
    private String brokerCause;

    @AmisField
    @Column(notNull = true, comment = "参数", type = MysqlTypeConstant.TEXT)
    private String json;

    @AmisField
    @Column(notNull = true, comment = "预定时间")
    private Date delayedTime;

    @AmisField
    @Column(comment = "执行时间")
    private Date execTime;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @AmisField(sort = 101)
    @InsertUpdateFillTime
    @Column(notNull = true, comment = "更新时间")
    private Date updateTime;

    @Column(notNull = true, comment = "关联延迟任务")
    @BindMany2One(entity = RabbitDelayed.class, valueField = RabbitDelayed.Fields.id,
        labelField = RabbitDelayed.Fields.entityClass)
    @AmisField
    private Long rabbitDelayedId;
}
