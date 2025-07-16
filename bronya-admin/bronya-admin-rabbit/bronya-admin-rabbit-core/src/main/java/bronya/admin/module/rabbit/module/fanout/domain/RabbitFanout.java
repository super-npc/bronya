package bronya.admin.module.rabbit.module.fanout.domain;

import java.util.Date;
import java.util.List;

import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.annotation.Exclude;
import org.dromara.mpe.annotation.InsertFillTime;
import org.dromara.mpe.annotation.InsertUpdateFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.rabbit.module.fanout.proxy.RabbitFanoutProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindOne2Many;
import bronya.core.base.annotation.amis.gencode.table.JoinCondition;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.ActivationStatus;
import bronya.shared.module.common.type.BeanRegisterStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;


@Data
@Table(comment = "发布订阅")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@Amis( extBean = RabbitFanout.RabbitFanoutExpandFields.class,dataProxy = RabbitFanoutProxy.class)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "rabbit", menu = "发布订阅"),
        btns = @Btns(add = false, edit = false, delete = false))
@TableIndexes({@TableIndex(fields = {RabbitFanout.Fields.entityClass}, type = IndexTypeEnum.UNIQUE)})
public class RabbitFanout {

    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField(comment = "激活", table = @AmisFieldView(type = AmisFieldView.ViewType.单选))
    private ActivationStatus activationStatus;

    @AmisField( search = true)
    @Column(comment = "实例")
    private String entityClass;

    @Exclude
    @BindOne2Many(entity = RabbitFanoutReceiver.class, condition = @JoinCondition(selfField = RabbitFanout.Fields.id,
            joinField = RabbitFanoutReceiver.Fields.rabbitFanoutId, joinFieldLabel = RabbitFanoutReceiver.Fields.entityClass))
    private List<RabbitFanoutReceiver> wxAppDetails;

    @Exclude
    @BindOne2Many(entity = RabbitFanoutMsg.class, condition = @JoinCondition(selfField = RabbitFanout.Fields.id,
            joinField = RabbitFanoutMsg.Fields.rabbitFanoutId, joinFieldLabel = RabbitFanoutMsg.Fields.entityClass))
    private List<RabbitFanoutMsg> rabbitFanoutMsgs;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @AmisField(sort = 101)
    @InsertUpdateFillTime
    @Column(notNull = true, comment = "更新时间")
    private Date updateTime;

    @Data
    public static class RabbitFanoutExpandFields {
        @AmisField(comment = "注册状态")
        private BeanRegisterStatus status;
    }
}
