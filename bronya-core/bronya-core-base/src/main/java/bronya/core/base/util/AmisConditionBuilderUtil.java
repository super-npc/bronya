package bronya.core.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.EnumUtil;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.autotable.annotation.Column;

import com.google.common.collect.Maps;

import bronya.core.base.annotation.amis.AmisComponentsRegister;
import bronya.core.base.annotation.amis.AmisConditionField;
import bronya.core.base.annotation.amis.AmisConditionField.Number.NumberOperator;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.inputdata.AmisConditionBuilder;
import bronya.core.base.annotation.amis.inputdata.AmisConditionBuilder.ConditionColumn;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.constant.IConditionBuilderTarget;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.common.type.ActionType;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.util.JsFieldUtil;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AmisConditionBuilderUtil {

    public AmisConditionBuilder genCb(Class<?> tableClass, Field field, ActionType actionType) {
        AmisField amisField = AnnotationUtil.getAnnotation(field, AmisField.class);
        Column column = AnnotationUtil.getAnnotation(field, Column.class);
        Boolean required = RequiredUtil.isRequired(column);
        AmisConditionBuilder.ConditionBuilder condition = amisField.conditionBuilder();
        String javaFieldName = field.getName();

        String comment = amisField.comment();
        if (StrUtil.isBlank(comment)) {
            // 优先使用amis的注释名称,没有再使用数据库的
            Assert.notNull(column, "字段未设置comment: {}.{}", tableClass.getSimpleName(), field.getName());
            comment = column.comment();
        }

        AmisFieldView fieldView = amisField.table();
        if (actionType == ActionType.table) {
            fieldView = amisField.table();
        } else if (actionType == ActionType.add) {
            fieldView = amisField.add();
        } else if (actionType == ActionType.edit) {
            fieldView = amisField.edit();
        }
        Boolean disabled = fieldView.disabled() ? true : null;
        AmisComponentsRegister tableDetail = fieldView.type().getTableDetail();
        String componentsName = tableDetail.getComponentsName();
        // AmisConditionBuilder.ConditionBuilder condition = amisField.conditionBuilder();
        AmisConditionBuilder change =
            AmisConditionBuilder.change(required, condition, componentsName, javaFieldName, comment, disabled, null,null);
        change.setRequired(required);
        String amisFieldName = JsFieldUtil.toJsField(tableClass.getSimpleName(), change.getName());
        change.setName(amisFieldName);

        Class<?> aClass = condition.targetDynamic();
        if (aClass != IConditionBuilderTarget.class) {
            String url =
                STR."/admin/base/amis/condition-builder-target?field=\{javaFieldName}&id=$id&\{condition.url()}";
            if (StrUtil.isNotBlank(condition.url())) {
                url = condition.url();
            }
            AmisApi amisApi = new AmisApi(tableClass, Method.GET, url);
            amisApi.setData(Maps.newHashMap());
            change.setSource(amisApi);
            change.setSelectMode("chained"); // 指定chained表示使用source
            return change;
        }

        // 指定风控组合条件的对象,解析所有字段作为条件fields
        Class<?> fieldsClass = condition.target();
        Assert.isFalse(fieldsClass == Void.class, "组合条件必须指定 conditionBuilder = @ConditionBuilder(fields)");

        List<ConditionColumn> conditionColumns = getConditionColumns(fieldsClass);
        change.setFields(conditionColumns);
        return change;
    }

    /**
     * 生成条件组合字段
     * 
     * @param fieldsClass 这个类里面的字段是 @AmisConditionField(label = "xxx")
     */
    public List<ConditionColumn> getConditionColumns(Class<?> fieldsClass) {
        return Arrays.stream(FieldUtil.getFields(fieldsClass)).map(fieldTemp -> {
            AmisConditionField annotation = AnnotationUtil.getAnnotation(fieldTemp, AmisConditionField.class);
            if (annotation == null) {
                return null;
            }
            String name = fieldTemp.getName();
            String label = annotation.label();
            ConditionColumn col = new ConditionColumn();
            col.setLabel(label);
            col.setName(name);
            Class<?> typeClass = fieldTemp.getType();
            if (ClassUtil.isJdkClass(typeClass)) {
                if (typeClass == String.class) {
                    col.setType("text");
                } else if (typeClass == Boolean.class) {
                    col.setType("boolean");
                } else if (typeClass == Date.class || typeClass == LocalDateTime.class) {
                    col.setType("datetime");
                } else if (typeClass == LocalDate.class) {
                    col.setType("date");
                } else if (typeClass == LocalTime.class) {
                    col.setType("time");
                } else if (AdminBaseConstant.numberClass.contains(typeClass)) {
                    col.setType("number");
                    AmisConditionField.Number num = annotation.number();
                    col.setDefaultOp(num.defaultOp() != NumberOperator.equal ? num.defaultOp().name() : null);
                    col.setOperators(num.operators().length > 0
                        ? Arrays.stream(num.operators()).map(NumberOperator::name).toList() : null);
                    col.setStep(num.step() > 0 ? num.step() : null);
                    col.setPrecision(num.precision() > 0 ? num.precision() : null);
                    col.setMaximum(BigDecimal.valueOf(num.maximum()).toPlainString());
                    col.setMinimum(BigDecimal.valueOf(num.minimum()).toPlainString());
                } else {
                    throw new RuntimeException(STR."没有找到匹配的类型\{typeClass}");
                }
            } else if (EnumUtil.isEnum(fieldTemp.getType())) {
                // 枚举
                col.setType("select");
                List<ConditionColumn.OptionsDTO> options =
                    Arrays.stream(fieldTemp.getType().getEnumConstants()).map(e -> {
                        if (e instanceof AmisEnum amisEnum) {
                            String value = amisEnum.toString();
                            String desc = amisEnum.getDesc();
                            return new ConditionColumn.OptionsDTO(desc, value);
                        }
                        return null;
                    }).filter(Objects::nonNull).toList();
                col.setOptions(options);
            } else {
                // todo 还有一种选择远程读取配置, select 配置 source
            }
            return col;
        }).filter(Objects::nonNull).toList();
    }
}
