package bronya.admin.module.db.env.domain;

import java.util.Date;

import org.dromara.autotable.annotation.IndexField;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexSortTypeEnum;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import bronya.core.base.dto.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.db.env.proxy.SysEnvObjFieldProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

// https://juejin.cn/post/7332708703400312873
@Data
@Table(comment = "变量属性")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis(extBean = SysEnvObjField.EnvPropertyExt.class, dataProxy = SysEnvObjFieldProxy.class)
@AmisPage(menu = @Menu(show = false, module = "系统", group = "数据管理", menu = "系统变量"),
        autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
        operation = @Operation(optBtns = {@Operation.OptBtn(name = "刷新数据", level = Operation.BtnLevelType.info,
                method = Method.GET, batch = true, url = "/admin/")}),
        btns = @Btns(add = false, delete = false,detail = false))
@TableIndexes({@TableIndex(
        indexFields = {@IndexField(field = SysEnvObjField.Fields.envObjId, sort = IndexSortTypeEnum.DESC),
                @IndexField(field = SysEnvObjField.Fields.dataKey, sort = IndexSortTypeEnum.DESC)},
        type = IndexTypeEnum.UNIQUE)})
public class SysEnvObjField extends BaseEntity<Long, Date> {

    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "键")
    @AmisField(edit = @AmisFieldView(disabled = true))
    private String dataKey;

    @Column(notNull = true, comment = "值")
    @AmisField
    private String dataValue;

    @Column(comment = "描述")
    @AmisField(edit = @AmisFieldView(disabled = true))
    private String description;

    @Column(notNull = true, comment = "对象")
    @BindMany2One(entity = SysEnvObj.class, valueField = SysEnvObj.Fields.id, labelField = SysEnvObj.Fields.description)
    @AmisField(edit = @AmisFieldView(disabled = true))
    private Long envObjId;

    @Data
    public static class EnvPropertyExt {
        @AmisField(comment = "状态")
        private EnvStatus status;
    }

    @Getter
    @AllArgsConstructor
    public enum EnvStatus implements AmisEnum {
        SYNC("同步", Color.纯绿), NON_EXIST("不存在", Color.深红色), DIFF("差异", Color.靛青);

        private final String desc;
        private final Color color;
    }


}
