package bronya.admin.module.db.env.domain;

import bronya.admin.module.db.env.proxy.SysEnvObjProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindOne2Many;
import bronya.core.base.annotation.amis.gencode.table.JoinCondition;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.showdata.AmisJson;
import bronya.core.base.constant.AmisPage;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.IndexField;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexSortTypeEnum;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.annotation.Exclude;
import org.dromara.mpe.annotation.InsertFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import java.util.Date;
import java.util.List;

// https://juejin.cn/post/7332708703400312873

@Data
@Table(comment = "变量对象")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@FieldNameConstants
@Amis(extBean = SysEnvObj.SysEnvObjExt.class, dataProxy = SysEnvObjProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "数据管理", menu = "系统变量"),
        autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
        btns = @Btns(add = false, edit = false, delete = false))
@TableIndexes({@TableIndex(indexFields = {@IndexField(field = SysEnvObj.Fields.objName, sort = IndexSortTypeEnum.DESC)},
        type = IndexTypeEnum.UNIQUE)})
public class SysEnvObj {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "对象名")
    @AmisField
    private String objName;

    @Column(comment = "描述")
    @AmisField
    private String description;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @Exclude
    @BindOne2Many(entity = SysEnvObjField.class, condition = @JoinCondition(selfField = SysEnvObjField.Fields.id,
            joinField = SysEnvObjField.Fields.envObjId, joinFieldLabel = SysEnvObjField.Fields.dataKey))
    private List<SysEnvObjField> sysEnvObjFieldList;

    @Data
    public static class SysEnvObjExt {
        @AmisField(comment = "环境变量", json = @AmisJson.Json(levelExpand = 2, placeholder = "未配置环境变量"),
                table = @AmisFieldView(type = AmisFieldView.ViewType.jsonSchema))
        private String env;
    }
}
