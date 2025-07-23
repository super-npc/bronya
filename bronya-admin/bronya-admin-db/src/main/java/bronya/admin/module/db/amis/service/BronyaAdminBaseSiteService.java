package bronya.admin.module.db.amis.service;

import java.util.List;
import java.util.Map;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.tree.MapTree;
import org.dromara.hutool.core.tree.TreeNode;
import org.dromara.hutool.core.tree.TreeUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.hutool.extra.pinyin.PinyinUtil;
import org.dromara.mpe.autotable.annotation.Table;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.BizException;
import com.google.common.collect.*;

import bronya.admin.base.util.SiteNodeUtil;
import bronya.admin.base.util.SiteUtil;
import bronya.admin.module.db.badge.mapper.dto.BeanCountsDto;
import bronya.admin.module.db.badge.service.BadgeService;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.util.AmisPathUtil;
import bronya.shared.module.common.constant.AdminBaseConstant;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class BronyaAdminBaseSiteService {
    private final BadgeService badgeService;

    public String findFirstSite() {
        HashBasedTable<String, String, List<Class<?>>> table = SiteUtil.getTables(AdminBaseConstant.AMIS_TABLES);
        return CollUtil.getFirst(table.rowKeySet());
    }

    public Map<String, List<Class<?>>> findMenuTreeBySite(String site) {
        HashBasedTable<String, String, List<Class<?>>> table = SiteUtil.getTables(AdminBaseConstant.AMIS_TABLES);
        Assert.isTrue(table.containsRow(site), () -> new BizException("切换系统菜单非法:{}", site));
        return table.row(site);
    }

    /**
     * @param tableClasses Map<group,menus>
     * @return
     */
    public List<MapTree<String>> buildTree(Map<String, List<Class<?>>> tableClasses, List<TreeNode<String>> topMenus,
        List<TreeNode<String>> bottomMenus) {
        List<BeanCountsDto> beanCounts = badgeService.findBeanCounts();
        List<String> icons = Lists.newArrayList(this.listIcon());
        // Collections.shuffle(icons);

        List<TreeNode<String>> treeNodes = Lists.newArrayList();
        treeNodes.addAll(topMenus);
        tableClasses.forEach((group, menus) -> {
            // 组名称作为主键
            String idGroup = StrUtil.toUnderlineCase(StrUtil.replace(PinyinUtil.getPinyin(group), " ", "_"));
            String groupUrl = STR."/\{idGroup}";
            // 将组加入节点,并设置idGroup作为绑定菜单
            TreeNode<String> nodeGroup = new TreeNode<>(idGroup, "0", group, idGroup);
            treeNodes.add(nodeGroup);
            // 还有一层一级菜单,进行菜单分组
            Multimap<String, Class<?>> amisPageMap = ArrayListMultimap.create();
            for (Class<?> menu : menus) {
                AmisPage amisPage = AnnotationUtil.getAnnotation(menu, AmisPage.class);
                String menuOne = amisPage.menu().menu();
                amisPageMap.put(menuOne, menu);
            }
            // 对每个菜单进行叶子生成
            amisPageMap.asMap().forEach((menuStr, leafClass) -> {
                String idMenu = StrUtil.toUnderlineCase(StrUtil.replace(PinyinUtil.getPinyin(menuStr), " ", "_"));
                List<Integer> counts = Lists.newArrayList();
                for (Class<?> tableClass : leafClass) {
                    // 最小的叶子
                    Table table = AnnotationUtil.getAnnotation(tableClass, Table.class);
                    String tableComment = table.comment();
                    String idLeaf = StrUtil.toUnderlineCase(tableClass.getSimpleName());
                    TreeNode<String> node = new TreeNode<>(idLeaf, idMenu, tableComment, idLeaf);
                    Map<String, Object> extra = Maps.newHashMap();
                    String menuUrl = StrUtil.toUnderlineCase(tableClass.getSimpleName());
                    String level1url = STR."\{groupUrl}/\{idMenu}/\{menuUrl}";
                    extra.put("url", level1url);
                    String icon = RandomUtil.randomEle(icons);
                    extra.put("icon", STR."/icon/\{icon}");
                    beanCounts.stream().filter(temp -> temp.getBean().equals(tableClass.getSimpleName())).findFirst()
                        .ifPresent(temp -> {
                            extra.put("badge", temp.getCount());
                            extra.put("badgeClassName", this.getBadgeClassName(temp.getCount()));
                            counts.add(temp.getCount());
                        });
                    extra.put("schemaApi", STR."get:\{AmisPathUtil.getStaticDomainFile(tableClass)}");
                        node.setExtra(extra);
                    treeNodes.add(node);
                }
                // 设置一级菜单
                TreeNode<String> nodeMenu = new TreeNode<>(idMenu, idGroup, menuStr, idMenu);
                Map<String, Object> extra = Maps.newHashMap();
                String icon = RandomUtil.randomEle(icons);
                extra.put("icon", STR."/icon/\{icon}");
                int sum = counts.stream().mapToInt(Integer::intValue).sum();
                if (sum > 0) {
                    extra.put("badge", sum);
                    extra.put("badgeClassName", this.getBadgeClassName(sum));
                }
                nodeMenu.setExtra(extra);
                treeNodes.add(nodeMenu);
            });
        });
        treeNodes.addAll(bottomMenus);
        // 转换器 (含义:找出父节点为字符串零的所有子节点, 并递归查找对应的子节点, 深度最多为 3)
        return TreeUtil.<TreeNode<String>, String>build(treeNodes, "0", SiteNodeUtil.getTreeNodeConfig(),
            (treeNode, treeConf) -> {
                treeConf.setId(treeNode.getId());
                treeConf.setParentId(treeNode.getParentId());
                treeConf.setWeight(SiteNodeUtil.getAsciiSum(treeNode.getWeight().toString()));
                treeConf.setName(treeNode.getName());
                if (treeNode.getExtra() != null) {
                    treeNode.getExtra().forEach(treeConf::putExtra);
                }
            });
    }

    private String getBadgeClassName(int count) {
        if (count < 10) {
            return "bg-info";
        } else if (count < 30) {
            return "bg-success";
        } else if (count < 100) {
            return "bg-warning";
        }
        return "bg-danger";
    }

    @SneakyThrows
    public List<String> listIcon() {
        List<Resource> resources =
            Lists.newArrayList(new PathMatchingResourcePatternResolver().getResources("static/icon/*.svg"));
        return resources.stream().map(Resource::getFilename).toList();
    }
}
