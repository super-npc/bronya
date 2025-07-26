package bronya.admin.module.db.amis.controller;

import bronya.admin.annotation.AmisBean;
import bronya.admin.annotation.LoginDto;
import bronya.admin.module.db.amis.controller.resp.DashboardResp;
import bronya.admin.module.db.amis.dto.AmisLoginUser;
import bronya.admin.module.db.amis.service.AsyncTaskService;
import bronya.admin.module.db.amis.service.BronyaAdminBaseAmisService;
import bronya.admin.module.db.amis.util.AmisTableUtil;
import bronya.admin.module.db.amis.util.BaseRepositoryUtil;
import bronya.admin.module.db.antishake.AntiShake;
import bronya.admin.util.SqlInjectionUtil;
import bronya.core.base.annotation.amis.db.req.AmisDeleteBatchReq;
import bronya.core.base.annotation.amis.db.req.AmisViewReq;
import bronya.core.base.annotation.amis.db.req.BindMany2OneViewReq;
import bronya.core.base.annotation.amis.db.resp.AmisPageResp;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.inputdata.AmisConditionBuilder;
import bronya.core.base.annotation.amis.type.AmisSelectAutoComplete;
import bronya.core.base.gencode.RelationshipManagementReq;
import bronya.core.base.gencode.RelationshipManagementReq.RelationshipType;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.platform.dto.AmisBeanDto;
import bronya.shared.module.util.JsFieldUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.split.SplitUtil;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin/base/amis")
@RequiredArgsConstructor
public class BronyaAdminDbAmisController {
    private final BronyaAdminBaseAmisService bronyaAdminBaseAmisService;
    private final IPlatform platformService;
    private final AsyncTaskService asyncTaskService;

    /**
     * 获取规则引擎动态字段
     */
    @GetMapping("/condition-builder-target")
    public Map<String, List<AmisConditionBuilder.ConditionColumn>> conditionBuilderTarget(
        @AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser, @RequestParam("id") Long id,
        @RequestParam("field") String field) {
        List<AmisConditionBuilder.ConditionColumn> conditionColumns =
            bronyaAdminBaseAmisService.conditionBuilderTarget(amisBeanDto, loginUser, id, field);
        return MapUtil.builder("fields", conditionColumns).build();
    }

    @GetMapping("/dashboard")
    public DashboardResp dashboard() {
        // return asyncTaskService.createWebAsyncTask(3000, () -> {
        return bronyaAdminBaseAmisService.dashboard();
        // });
    }

    @GetMapping("/dynamic-table/auto-complete")
    public List<AmisSelectAutoComplete> dynamicTableAutoComplete(@RequestParam("bean") String bean,
        @RequestParam("term") String term) {
        SqlInjectionUtil.checkInjection(term);// 注入监测
        return bronyaAdminBaseAmisService.dynamicTableAutoComplete(bean, term);
    }

    @PostMapping("/middle-relationship")
    // @CatchAndLog // 这个会吞异常
    public Boolean middleRelationship(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody RelationshipManagementReq req) {
        SqlInjectionUtil.checkInjection(req);// 注入监测
        List<String> selectValues = SplitUtil.split(req.getAnOtherMiddleFieldVal(), ",");
        Assert.notEmpty(selectValues, "请选择操作数据记录!");
        // 中间表关系处理
        AmisBeanDto middleDto = AmisTableUtil.find(req.getMiddleClass());
        CrudRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        if (req.getRelationshipType() == RelationshipType.connect) {
            // 已经关联的,需要断开关联
            QueryWrapper<Object> query = new QueryWrapper<>();
            query.eq(StrUtil.toUnderlineCase(req.getThisMiddleFiled()), req.getThisMiddleFiledVal());
            query.in(StrUtil.toUnderlineCase(req.getAnOtherMiddleField()), selectValues);
            return service.remove(query);
        }
        // 未关联的,需要数据表关联
        List<Map<String, Object>> saves = Lists.newArrayList();
        for (String selectValue : selectValues) {
            Map<String, Object> bean = Maps.newHashMap();
            bean.put(req.getThisMiddleFiled(), req.getThisMiddleFiledVal());
            bean.put(req.getAnOtherMiddleField(), selectValue);
            saves.add(bean);
        }

        return service.saveBatch(saves, 10);
    }

    // 多对一字段的查询对应的实体信息
    @PostMapping("/bind-many2-one-view")
    // @CatchAndLog // 这个会吞异常
    public Map<String, Object> bindMany2OneView(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody AmisViewReq req, @RequestHeader Map<String, Object> header) {
        SqlInjectionUtil.checkInjection(req);// 注入监测
        String bindMany2OneClassField = MapUtil.getStr(header, "bind-many2-one-class-field");
        BindMany2OneViewReq bindMany2OneViewReq = new BindMany2OneViewReq(bindMany2OneClassField); // todo 需要检查字段

        // 1. 先查询出id的当前对象
        Object currentObj = BaseRepositoryUtil.getService(amisBeanDto.getMainClass()).getById(req.getId());
        // 2. 从当前对象获取到该对象的多对一关联属性
        Field currentObjMany2OneField =
            FieldUtil.getField(amisBeanDto.getMainClass(), bindMany2OneViewReq.getBindMany2OneClassField());
        BindMany2One bindMany2One = currentObjMany2OneField.getAnnotation(BindMany2One.class);
        Assert.notNull(bindMany2One, "对象不存在多对1的关联属性:{}.{}", amisBeanDto.getMainClassSimpleName(),
            bindMany2OneViewReq.getBindMany2OneClassField());
        Class<?> entity = bindMany2One.entity();
        String field = bindMany2One.valueField();

        // 3. 真正的去查询关联值绑定的对象
        AmisBeanDto bindMany2OneClassDto = AmisTableUtil.find(entity.getSimpleName());
        CrudRepository bindMany2OneClassService = BaseRepositoryUtil.getService(bindMany2OneClassDto.getMainClass());
        QueryWrapper<Object> bindMany2OneClassQueryWrapper = new QueryWrapper<>();
        Object currentObjBindMany2OneClassFieldValue =
            FieldUtil.getFieldValue(currentObj, bindMany2OneViewReq.getBindMany2OneClassField());
        bindMany2OneClassQueryWrapper.eq(StrUtil.toUnderlineCase(field), currentObjBindMany2OneClassFieldValue);
        Object one = bindMany2OneClassService.getOne(bindMany2OneClassQueryWrapper);
        return bronyaAdminBaseAmisService.tableRecord(bindMany2OneClassDto, one);
    }

    @PostMapping("/create")
    @AntiShake(value = 2000)
    // @CatchAndLog // 这个会吞异常
    public Boolean create(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody Map<String, Object> map) {
        SqlInjectionUtil.checkInjection(map);// 注入监测
        platformService.checkPermissionCreate(amisBeanDto);
        Optional<ICurd> curdImplOpt = bronyaAdminBaseAmisService.getCurdImpl(amisBeanDto);
        if (curdImplOpt.isPresent()) {
            ICurd iCurd = curdImplOpt.get();
            return iCurd.create(amisBeanDto, map);
        }
        return bronyaAdminBaseAmisService.create(amisBeanDto, JsFieldUtil.toObjField(map));
    }

    @PostMapping("/page")
    // @CatchAndLog // 这个会吞异常
    public AmisPageResp page(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody Map<String, Object> map) {
        SqlInjectionUtil.checkInjection(map);// 注入监测
        platformService.checkPermissionView(amisBeanDto);
        return bronyaAdminBaseAmisService.page(amisBeanDto, JsFieldUtil.toObjField(map));
    }

    @PostMapping("/view")
    // @CatchAndLog // 这个会吞异常
    public Map<String, Object> view(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody AmisViewReq req) {
        SqlInjectionUtil.checkInjection(req);// 注入监测
        platformService.checkPermissionView(amisBeanDto);
        Optional<ICurd> curdImplOpt = bronyaAdminBaseAmisService.getCurdImpl(amisBeanDto);
        if (curdImplOpt.isPresent()) {
            ICurd iCurd = curdImplOpt.get();
            return iCurd.view(amisBeanDto, req);
        }
        return bronyaAdminBaseAmisService.view(amisBeanDto, req);
    }

    @PostMapping("/update")
    @AntiShake(value = 2000)
    // @CatchAndLog // 这个会吞异常
    public Boolean update(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody Map<String, Object> map) {
        SqlInjectionUtil.checkInjection(map);// 注入监测
        platformService.checkPermissionUpdate(amisBeanDto);
        Optional<ICurd> curdImplOpt = bronyaAdminBaseAmisService.getCurdImpl(amisBeanDto);
        if (curdImplOpt.isPresent()) {
            ICurd iCurd = curdImplOpt.get();
            return iCurd.update(amisBeanDto, map);
        }
        return bronyaAdminBaseAmisService.update(amisBeanDto, JsFieldUtil.toObjField(map), false);
    }

    @PostMapping("/quick-save-api")
    public Boolean quickSaveApi(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody Map<String, Object> map) {
        SqlInjectionUtil.checkInjection(map);// 注入监测
        platformService.checkPermissionUpdate(amisBeanDto);
        return bronyaAdminBaseAmisService.quickSaveApi(amisBeanDto, JsFieldUtil.toObjField(map));
    }

    @PostMapping("/read-all")
    @AntiShake(value = 2000)
    // @CatchAndLog // 这个会吞异常
    public Boolean readAll(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody AmisDeleteBatchReq req) {
        SqlInjectionUtil.checkInjection(req);// 注入监测
        return bronyaAdminBaseAmisService.readAll(amisBeanDto, loginUser, req);
    }

    @PostMapping("/delete-batch")
    @AntiShake(value = 2000)
    // @CatchAndLog // 这个会吞异常
    public Boolean deleteBatch(@AmisBean AmisBeanDto amisBeanDto, @LoginDto AmisLoginUser loginUser,
        @RequestBody AmisDeleteBatchReq req) {
        SqlInjectionUtil.checkInjection(req);// 注入监测
        platformService.checkPermissionDelete(amisBeanDto);
        Optional<ICurd> curdImplOpt = bronyaAdminBaseAmisService.getCurdImpl(amisBeanDto);
        if (curdImplOpt.isPresent()) {
            ICurd iCurd = curdImplOpt.get();
            return iCurd.deleteBatch(amisBeanDto, req);
        }
        return bronyaAdminBaseAmisService.deleteBatch(amisBeanDto, req);
    }
}
