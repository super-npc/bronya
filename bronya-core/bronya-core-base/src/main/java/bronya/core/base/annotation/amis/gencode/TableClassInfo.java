package bronya.core.base.annotation.amis.gencode;

import java.lang.reflect.Field;
import java.util.List;

import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleChild;
import bronya.core.base.annotation.amis.gencode.table.BindOne2Many;
import bronya.core.base.constant.AmisPage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TableClassInfo {
    private final Class<?> tableClass;
    private final String beanName;
    private final Table table;
    private final AmisPage amisPage;
    private final PrimaryField primaryField;
    private final List<CommonField> commonField;
    private final List<BindMany2OneField> bindMany2OneFields;
    private final List<BindOne2ManyField> bindOne2ManyFields;
    /**
     * 绑定多对多
     */
    private final List<BindBindMiddleChild> bindBindMiddleChildren;


    public record BindBindMiddleChild(Field field, BindMiddleChild annotation) {
    }

    public record BindMany2OneField(Field field, BindMany2One annotation) {
    }

    public record BindOne2ManyField(Field field, BindOne2Many annotation) {
    }

    public record CommonField(Field field, Column annotation, AmisField amisField) {
    }

    public record PrimaryField(Field field, ColumnId annotation, AmisField amisField) {
    }
}