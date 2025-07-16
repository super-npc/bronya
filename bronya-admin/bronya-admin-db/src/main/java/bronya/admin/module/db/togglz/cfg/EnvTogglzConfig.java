//package bronya.admin.module.db.togglz.cfg;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.InitializingBean;
//import org.togglz.core.Feature;
//import org.togglz.core.manager.TogglzConfig;
//
///**
// * @see <a href="https://www.togglz.org/documentation/configuration">...</a>
// */
//
//@Slf4j
//@RequiredArgsConstructor
//public class EnvTogglzConfig implements TogglzConfig, InitializingBean {
//
//    private final ObjectProvider<List<StateRepositoryFactory>> listObjectProvider;
//    private final EnvTogglzProperties envTogglzProperties;
//
//    private Map<String,StateRepositoryFactory> stateRepositoryMap;
//    @Override
//    public Class<? extends Feature> getFeatureClass() {
//        // 返回定义功能开关的枚举类
//        return EnvFeature.class;
//    }
//
//    /***
//     * @see <a href="https://www.togglz.org/documentation/repositories"></a>
//     * @return
//     */
//    @Override
//    public StateRepository getStateRepository() {
//        //配置功能开关状态的存储方式
//        if(stateRepositoryMap == null || stateRepositoryMap.isEmpty()){
//            log.warn("stateRepositoryFactories is empty,select inMemoryStateRepository");
//            return new InMemoryStateRepository();
//        }
//
//        return stateRepositoryMap.get(envTogglzProperties.getStateRepositoryType()).create();
//
//    }
//
//    /**
//     * @see <a href="https://www.togglz.org/documentation/authentication"></a>
//     * @return
//     */
//    @Override
//    public UserProvider getUserProvider() {
//        // 提供功能开关的提供者,返回当前登录的用户对象
//      return new SingleUserProvider("lybgeek", true);
//    }
//
//    @Override
//    public void afterPropertiesSet() {
//
//        List<StateRepositoryFactory> stateRepositoryFactories = listObjectProvider.getIfAvailable();
//        if(CollectionUtils.isEmpty(stateRepositoryFactories)){
//            return;
//        }
//        stateRepositoryMap = new HashMap<>(stateRepositoryFactories.size());
//        stateRepositoryFactories.forEach(stateRepositoryFactory -> stateRepositoryMap.put(stateRepositoryFactory.supportType(),stateRepositoryFactory));
//
//
//    }
//}
