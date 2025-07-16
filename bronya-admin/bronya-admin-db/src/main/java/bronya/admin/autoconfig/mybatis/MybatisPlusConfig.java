package bronya.admin.autoconfig.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.dromara.mpe.condition.DynamicConditionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * congfig中注册动态条件拦截器【1.3.0之前的版本（不包括1.3.0）可以忽略，不注册该Bean】
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(this.createDynamicTableNameInnerInterceptor());

        // 添加非法SQL拦截器
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        // 禁止全表操作
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 添加动态条件，若同时添加了其他的拦截器，继续添加即可
        interceptor.addInnerInterceptor(new DynamicConditionInterceptor());
        // 如果使用了分页，请放在DynamicConditionInterceptor之后
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());

        return interceptor;
    }

    private DynamicTableNameInnerInterceptor createDynamicTableNameInnerInterceptor(){
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName) -> {
            // 获取参数方法
//            Map<String, Object> paramMap = RequestDataHelper.getRequestData();
//            paramMap.forEach((k, v) -> System.err.println(k + "----" + v));

            String year = "_2018";
//            int random = new Random().nextInt(10);
//            if (random % 2 == 1) {
//                year = "_2019";
//            }
            return tableName + year;
        });
        return dynamicTableNameInnerInterceptor;
    }
}
