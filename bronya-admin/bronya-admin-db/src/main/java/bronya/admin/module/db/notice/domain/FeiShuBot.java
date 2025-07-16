package bronya.admin.module.db.notice.domain;

import java.util.Date;
import java.util.List;

import org.dromara.autotable.annotation.IndexField;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexSortTypeEnum;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.annotation.Exclude;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindOne2Many;
import bronya.core.base.annotation.amis.gencode.table.JoinCondition;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "飞书Bot")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis
@AmisPage(menu = @Menu(module = "系统", group = "消息中心", menu = "飞书"),
    autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
    operation = @Operation(optBtns = {@Operation.OptBtn(name = "验证发送", level = Operation.BtnLevelType.info,
        method = Method.GET, batch = true, url = "/admin/fei-shu/send-test")}))
@TableIndexes({@TableIndex(indexFields = {@IndexField(field = FeiShuBot.Fields.token, sort = IndexSortTypeEnum.DESC)},
    type = IndexTypeEnum.UNIQUE)})
public class FeiShuBot extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "机器人名称")
    @AmisField
    private String name;

    @Column(notNull = true, comment = "token")
    @AmisField
    private String token;

    @Column(notNull = true, comment = "签名")
    @AmisField
    private String secret;

    @Exclude
    @BindOne2Many(entity = FeiShuRecord.class, condition = @JoinCondition(selfField = FeiShuRecord.Fields.id,
        joinField = FeiShuRecord.Fields.feiShuId, joinFieldLabel = FeiShuRecord.Fields.levelType))
    private List<FeiShuRecord> feiShuRecordList;
}
