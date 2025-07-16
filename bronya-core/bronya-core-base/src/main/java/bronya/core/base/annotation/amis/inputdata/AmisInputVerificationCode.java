package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputVerificationCode extends AmisComponents {

    public AmisInputVerificationCode(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 验证码的长度，根据长度渲染对应个数的输入框 */
    private Integer length;
    /** 是否是密码模式 */
    private Boolean masked;
    /** 分隔符，支持表达式, 表达式只可以访问 index、character 变量, 参考自定义分隔符 */
    private String separator;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputVerificationCode {
        /** 验证码的长度，根据长度渲染对应个数的输入框 */
        int length() default 6;

        /** 是否是密码模式 */
        boolean masked() default false;

        /** 分隔符，支持表达式, 表达式只可以访问 index、character 变量, 参考自定义分隔符 */
        String separator() default "";
    }

    public static AmisInputVerificationCode change(Boolean required,InputVerificationCode annotation, String type, String name,
        String label,Boolean disabled,String width,String remark) {
        AmisInputVerificationCode temp = new AmisInputVerificationCode(required,type, name, label,disabled,width,remark);
        temp.setLength(annotation.length());
        temp.setMasked(annotation.masked());
        temp.setSeparator(annotation.separator());
        return temp;
    }
}