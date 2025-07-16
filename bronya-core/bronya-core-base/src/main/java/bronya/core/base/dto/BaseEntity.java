package bronya.core.base.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/**
 * 主键的雪花算法不要在这里配置,会导致更新报错,在审计设置id {@link npc.bulinke.core.autoconfig.db.listener.BaseEntityListener}
 */
@Getter
@Setter
@ToString
@FieldNameConstants
public abstract class BaseEntity {
    private Long id;
}