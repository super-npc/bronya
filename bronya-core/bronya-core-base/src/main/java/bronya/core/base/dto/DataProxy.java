package bronya.core.base.dto;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DataProxy<T> {
    public abstract void table(Map<String, Object> map);

    public T beforeAdd(T t) {
        log.info("新增前:{}", t);
        return t;
    }

    public void afterAdd(T t) {
        log.info("新增数据:{}", t);
    }

    public T beforeUpdate(T t) {
        log.info("更新前:{}", t);
        return t;
    }

    public void afterUpdate(T t) {
        log.info("更新后:{}", t);
    }

    public void aroundUpdate(T before,T after){
        log.info("更新前:{},更新后:{}", before,after);
    }

    public void beforeDelete(Long id) {

    }

    public void afterDelete(Long id) {

    }
}