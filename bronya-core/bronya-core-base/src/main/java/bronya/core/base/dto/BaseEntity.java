package bronya.core.base.dto;

import lombok.Getter;
import lombok.Setter;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.mpe.autofill.annotation.InsertFillTime;
import org.dromara.mpe.autofill.annotation.InsertUpdateFillTime;

import java.io.Serializable;

/**
 * @author don
 */
@Getter
@Setter
public class BaseEntity<ID_TYPE extends Serializable, TIME_TYPE> {

    @ColumnComment("创建人")
    protected ID_TYPE createBy;
    @ColumnComment("最后更新人")
    protected ID_TYPE updateBy;
    @InsertFillTime
    @ColumnComment("创建时间")
    protected TIME_TYPE createTime;
    @InsertUpdateFillTime
    @ColumnComment("最后更新时间")
    protected TIME_TYPE updateTime;
}
