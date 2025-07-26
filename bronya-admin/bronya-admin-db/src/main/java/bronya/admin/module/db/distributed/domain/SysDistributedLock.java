package bronya.admin.module.db.distributed.domain;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autofill.annotation.InsertFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import java.util.Date;

@Data
@Table(comment = "分布式锁")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "配置", menu = "系统配置"), btns = @Btns(add = false, edit = false))
@TableIndexes({@TableIndex(fields = {SysDistributedLock.Fields.lockKey}, type = IndexTypeEnum.UNIQUE)})
public class SysDistributedLock {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField
    @Column(notNull = true, comment = "名称")
    private String lockKey;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;
}
