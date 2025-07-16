package bronya.core.base.annotation.amis;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AmisConditionField {
    /**
     * 展示标签
     */
    String label();

    /**
     * 占位符
     */
    String placeholder() default "";

    Text text() default @Text();

    Number number() default @Number();

    Date date() default @Date();

    Datetime datetime() default @Datetime();

    Time time() default @Time();

    Select select() default @Select();

    @interface Select {

        String defaultValue() default "";

        Options[] options() default {};// Array<{label: string, value: any}>

        @interface Options {
            String label();

            String value();
        }

        // source 动态选项，请配置 api。
        String source() default "";

        /**
         * 自动提示补全，每次输入新内容后，将调用接口，根据接口返回更新选项。
         */
        boolean autoComplete() default false;

        boolean searchable() default false;

        SelectOperator defaultOp() default SelectOperator.select_equals;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        SelectOperator[] operators() default {};

        enum SelectOperator {
            select_equals, select_not_equals, select_any_in, select_not_any_in
        }
    }

    @interface Time {
        /**
         * 显示的日期格式
         */
        String inputFormat() default "HH:mm";

        String timeFormat() default "HH:mm";

        String format() default "";

        String defaultValue() default "";

        DateOperator defaultOp() default DateOperator.equal;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        DateOperator[] operators() default {};
    }

    @interface Datetime {
        /**
         * 显示的日期格式
         */
        String inputFormat() default "YYYY-MM-DD HH:mm";

        String timeFormat() default "HH:mm";

        String format() default "";

        String defaultValue() default "";

        DateOperator defaultOp() default DateOperator.equal;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        DateOperator[] operators() default {};
    }

    @interface Date {

        String inputFormat() default "YYYY-MM-DD";

        String format() default "YYYY-MM-DD";

        String defaultValue() default "";

        DateOperator defaultOp() default DateOperator.equal;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        DateOperator[] operators() default {};

    }

    enum DateOperator {
        equal, not_equal, less, less_or_equal, greater, greater_or_equal, between, not_between, is_empty, is_not_empty
    }

    @interface Text {

        TextOperator defaultOp() default TextOperator.equal;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        TextOperator[] operators() default {};

        enum TextOperator {
            equal, not_equal, is_empty, is_not_empty, like, not_like, starts_with, ends_with
        }
    }

    @interface Number {
        /**
         * 小数位
         */
        int precision() default -1;

        double minimum() default -99999999999999999999999999d;

        double maximum() default 99999999999999999999999999d;

        /**
         * 步长
         */
        int step() default -1;

        NumberOperator defaultOp() default NumberOperator.equal;

        /**
         * 如果不要那么多，可以配置覆盖
         */
        NumberOperator[] operators() default {};

        enum NumberOperator {
            equal, not_equal, less, less_or_equal, greater, greater_or_equal, between, not_between, is_empty,
            is_not_empty
        }
    }
}
