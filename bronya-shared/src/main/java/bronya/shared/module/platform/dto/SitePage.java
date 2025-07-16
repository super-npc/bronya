package bronya.shared.module.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.tree.MapTree;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SitePage {
    private List<MapTree<String>> pages;
}