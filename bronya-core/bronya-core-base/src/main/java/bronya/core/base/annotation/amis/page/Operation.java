package bronya.core.base.annotation.amis.page;


import org.dromara.hutool.http.meta.Method;

public @interface Operation {
    OptBtn[] optBtns() default {};

    @interface OptBtn {
        Type type() default Type.button;

        /**
         * 支持批量
         */
        boolean batch() default false;

        String name();

        Method method();

        String url();

        BtnLevelType level() default BtnLevelType.light;

        /**
         * 隐藏表格的操作按钮
         */
        boolean hiddenOnHover() default true;

        /**
         * 是否为下载链接
         * "responseType": "blob"
         *
         * java端 使用 ServletUtil.write 下载文件
         */
        boolean download() default false;
    }

    enum Type {
        button, service;
    }

    enum BtnLevelType {
        link, primary, enhance, secondary, info, success, warning, danger, light, dark
    }
}