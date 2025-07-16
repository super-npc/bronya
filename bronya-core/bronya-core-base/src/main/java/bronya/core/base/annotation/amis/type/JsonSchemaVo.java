package bronya.core.base.annotation.amis.type;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <a href="https://aisuda.bce.baidu.com/amis/zh-CN/components/form/json-schema">json组件</a>
 */
@NoArgsConstructor
@Data
public class JsonSchemaVo {
    private final String type = "json-schema";
    private String name;
    private String label;
    private SchemaVo schema;

    @NoArgsConstructor
    @Data
    public static class SchemaVo {
        /** 字段类型 对象为Object,String,Number,列表Array **/
        private String type;
        private Boolean additionalProperties;
        /** 必填字段 **/
        private List<String> required;
        /** 字段详情 **/
        private Map<String, PropertiesDTO> properties;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class PropertiesDTO {
            private String type;
            private String title;
            private String value;
        }

        // @NoArgsConstructor
        // @Data
        // public static class PropertiesDTO {
        // private DateDTO date;
        // private TagDTO tag;
        //
        // @NoArgsConstructor
        // @Data
        // public static class DateDTO {
        // private String type;
        // private String title;
        // private Boolean additionalProperties;
        // private List<String> required;
        // private PropertiesDTO properties;
        //
        // }
        //
        // @NoArgsConstructor
        // @Data
        // public static class TagDTO {
        // private String type;
        // private String title;
        // private ItemsDTO items;
        // private Integer minContains;
        // private Integer maxContains;
        //
        // @NoArgsConstructor
        // @Data
        // public static class ItemsDTO {
        // private String type;
        // }
        // }
        // }
    }
}
