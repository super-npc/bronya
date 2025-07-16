package bronya.core.base.annotation.amis.db.resp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AmisSiteGroup {
    // 标签
    private String label;
    // URL
    private String url;
    // 重定向URL
    private String redirect;
    private String icon;
    // 子节点列表
    private List<AmisSiteMenu> children;

    @NoArgsConstructor
    @Data
    public static class AmisSiteMenu {
        // 标签
        private String label;
        // URL
        private String url;
        // 模式定义
        private AmisSiteMenu.SchemaDTO schema;
        private String schemaApi;
        // 子节点列表
        private List<AmisSiteMenu> children;
        // 徽章数量
        private Integer badge;
        // 徽章样式类名
        private String badgeClassName;
        // URL重写
        private String rewrite;
        // 图标
        private String icon;

        @NoArgsConstructor
        @Data
        public static class SchemaDTO {
            // 类型
            private String type;
            // 标题
            private String title;
            // 内容主体
            private String body;
        }
    }
}
