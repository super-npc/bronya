package bronya.admin.base.util;

import lombok.experimental.UtilityClass;
import org.dromara.hutool.core.tree.TreeNodeConfig;

@UtilityClass
public class SiteNodeUtil {

    // 计算字符串的 ASCII 码和
    public int getAsciiSum(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += c;
        }
        return sum;
    }

    public TreeNodeConfig getTreeNodeConfig() {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都有默认值的
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("label");
        // 最大递归深度
        // treeNodeConfig.setDeep(3);

        return treeNodeConfig;
    }
}
