package bronya.admin.module.db.amis.controller;

import java.util.Map;

import org.dromara.hutool.db.PageResult;

import com.alibaba.cola.exception.BizException;
import com.google.common.collect.Maps;

import bronya.core.base.annotation.amis.db.req.AmisDeleteBatchReq;
import bronya.core.base.annotation.amis.db.req.AmisViewReq;
import bronya.shared.module.platform.dto.AmisBeanDto;

public abstract class ICurd<T> {
    public PageResult<T> page(AmisBeanDto amisBeanDto, Integer currentPage, Integer perPage, Map<String, Object> map) {
        return new PageResult<>();
    }

    public Boolean create(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        return false;
    }

    public Map<String, Object> view(AmisBeanDto amisBeanDto, AmisViewReq req) {
        return Maps.newHashMap();
    }

    public Boolean update(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        return false;
    }

    public Boolean deleteBatch(AmisBeanDto amisBeanDto, AmisDeleteBatchReq req) {
        return false;
    }

    public Boolean quickSaveApi(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        throw new BizException("不支持快速编辑");
    }
}
