package bronya.core.base.annotation.amis.gencode;

import java.lang.reflect.Field;
import java.util.List;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.google.common.collect.Lists;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.TableClassInfo.*;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleChild;
import bronya.core.base.annotation.amis.gencode.table.BindOne2Many;
import bronya.core.base.constant.AmisPage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TableExtract {

    public TableClassInfo getTableInfoAndFields(Class<?> tableClass) {
        List<PrimaryField> primaryFields = Lists.newArrayList();
        List<CommonField> commonFields = Lists.newArrayList();
        List<BindMany2OneField> bindOneToManyFields = Lists.newArrayList();
        List<BindBindMiddleChild> bindManyToMidFields = Lists.newArrayList();
        List<BindOne2ManyField> bindManyToOneFields = Lists.newArrayList();
        List<Field> fields = Lists.newArrayList(FieldUtil.getFields(tableClass));
        for (Field field : fields) {
            ColumnId columnId = AnnotationUtil.getAnnotation(field, ColumnId.class);
            if (columnId != null) {
                primaryFields.add(new PrimaryField(field, columnId, null));
            }
            Column column = AnnotationUtil.getAnnotation(field, Column.class);
            if (column != null) {
                AmisField amisField = AnnotationUtil.getAnnotation(field, AmisField.class);
                commonFields.add(new CommonField(field, column, amisField));
            }
            BindOne2Many bindManyToOne = AnnotationUtil.getAnnotation(field, BindOne2Many.class);
            if (bindManyToOne != null) {
                bindManyToOneFields.add(new BindOne2ManyField(field, bindManyToOne));
            }
            BindMany2One oneToMany = AnnotationUtil.getAnnotation(field, BindMany2One.class);
            if (oneToMany != null) {
                bindOneToManyFields.add(new BindMany2OneField(field, oneToMany));
            }
            BindMiddleChild manyToMid = AnnotationUtil.getAnnotation(field, BindMiddleChild.class);
            if (manyToMid != null) {
                bindManyToMidFields.add(new BindBindMiddleChild(field, manyToMid));
            }
        }
        Assert.isTrue(primaryFields.size() == 1, "未配置主键/多个主键:{}", tableClass);
        Table table = AnnotationUtil.getAnnotation(tableClass, Table.class);
        AmisPage amisPage = AnnotationUtil.getAnnotation(tableClass, AmisPage.class);
        return new TableClassInfo(tableClass, tableClass.getSimpleName(), table, amisPage, primaryFields.getFirst(), commonFields,
                bindOneToManyFields, bindManyToOneFields, bindManyToMidFields);
    }
}
