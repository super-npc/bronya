package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.page.Operation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisButtonGroup extends AmisComponents {

    public AmisButtonGroup(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

//    public AmisButtonGroup(String name, String label) {
//        super("button-group", name, label);
//    }

    /** 是否使用垂直模式 */
    private Boolean vertical;
    /** 是否使用平铺模式 */
    private Boolean tiled;
    /** 按钮样式 */
    private Operation.BtnLevelType btnLevel;
    /** 选中按钮样式 */
    private Operation.BtnLevelType btnActiveLevel;
    /** 按钮 */
    private Object buttons;
    /** 外层 Dom 的类名 */
    private String className;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface ButtonGroup {
        /** 是否使用垂直模式 */
        boolean vertical() default false;

        /** 是否使用平铺模式 */
        boolean tiled() default false;

        /** 按钮样式 */
        Operation.BtnLevelType btnLevel() default Operation.BtnLevelType.light;

        /** 选中按钮样式 */
        Operation.BtnLevelType btnActiveLevel() default Operation.BtnLevelType.light;

        /** 外层 Dom 的类名 */
        String className() default "";
    }

    public static AmisButtonGroup change(Boolean required,ButtonGroup annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisButtonGroup temp = new AmisButtonGroup(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        temp.setVertical(annotation.vertical());
        temp.setTiled(annotation.tiled());
        temp.setBtnLevel(annotation.btnLevel());
        temp.setBtnActiveLevel(annotation.btnActiveLevel());
        // temp.setButtons(annotation.buttons());
        temp.setClassName(annotation.className());
        return temp;
    }
}