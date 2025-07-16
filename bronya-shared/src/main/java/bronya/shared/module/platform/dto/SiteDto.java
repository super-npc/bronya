package bronya.shared.module.platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class SiteDto {
    private SitePage sitePage;
    private Map<String, Object> cookies;
}
