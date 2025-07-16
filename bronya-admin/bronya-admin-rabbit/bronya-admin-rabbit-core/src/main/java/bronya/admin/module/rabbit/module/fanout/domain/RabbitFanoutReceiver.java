package bronya.admin.module.rabbit.module.fanout.domain;

import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "发布订阅接收")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "rabbit", menu = "发布订阅"),
    btns = @Btns(add = false, edit = false, delete = false))
public class RabbitFanoutReceiver {

    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField(search = true)
    @Column(comment = "实例")
    private String entityClass;

    @Column(notNull = true, comment = "关联发布订阅")
    @BindMany2One(entity = RabbitFanout.class, valueField = RabbitFanout.Fields.id, labelField = RabbitFanout.Fields.entityClass)
    @AmisField
    private Long rabbitFanoutId;

}
