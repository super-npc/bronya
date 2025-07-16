package bronya.admin.module.db.togglz;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import bronya.admin.module.db.togglz.cfg.EnvFeature;


@RestController
@RequestMapping("/api/features")
@RequiredArgsConstructor
public class FeatureToggleController {
    private final FeatureManager featureManager;

    /**
     * 获取当前所有特性状态
     *
     * @return 特性状态的映射
     */
    @GetMapping
    public Map<String, Boolean> getFeatures() {
        Map<String, Boolean> features = new HashMap<>();
        features.put("featureOne", featureManager.isActive(EnvFeature.DEV));
        features.put("featureTwo", featureManager.isActive(EnvFeature.UAT));
        return features;
    }

//    /**
//     * 更新特性开关状态
//     *
//     * @param featureStates 包含所有特性状态的映射
//     * @return 更新结果
//     */
//    @PostMapping("/toggle")
//    public Map<String, String> toggleFeatures(@RequestBody Map<String, Boolean> featureStates) {
//        try {
//            for (Map.Entry<String, Boolean> entry : featureStates.entrySet()) {
//                String featureName = entry.getKey();
//                boolean enabled = entry.getValue();
//
//                Feature feature = getFeature(featureName);
//                if (feature != null) {
//                    featureManager.setFeatureState(new FeatureState(feature, enabled));
//                } else {
//                    return Map.of("status", "error", "message", "未找到特性 " + featureName);
//                }
//            }
//            return Map.of("status", "success", "message", "所有特性状态已更新");
//        } catch (Exception e) {
//            return Map.of("status", "error", "message", "更新特性状态时发生错误");
//        }
//    }
//
//    private Feature getFeature(String featureName) {
//        if ("featureOne".equalsIgnoreCase(featureName)) {
//            return MyFeatures.FEATURE_ONE;
//        } else if ("featureTwo".equalsIgnoreCase(featureName)) {
//            return MyFeatures.FEATURE_TWO;
//        }
//        return null;
//    }
}