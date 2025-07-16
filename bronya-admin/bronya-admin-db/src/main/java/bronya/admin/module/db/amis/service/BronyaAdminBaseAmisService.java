package bronya.admin.module.db.amis.service;

import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrPool;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.split.SplitUtil;
import org.dromara.hutool.db.Db;
import org.dromara.hutool.db.Entity;
import org.dromara.hutool.db.Page;
import org.dromara.hutool.db.PageResult;
import org.dromara.hutool.db.ds.DSWrapper;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.json.JSONUtil;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mybatisflex.core.query.QueryWrapper;

import bronya.admin.base.cfg.git.GitBronyaCfg;
import bronya.admin.base.cfg.git.GitProjectCfg;
import bronya.admin.base.util.AmisFieldUtil;
import bronya.admin.module.db.amis.controller.ICurd;
import bronya.admin.module.db.amis.controller.resp.DashboardResp;
import bronya.admin.module.db.amis.dto.AmisLoginUser;
import bronya.admin.module.db.amis.service.field.converter.AmisFiledConvertSwitch;
import bronya.admin.module.db.amis.util.AmisTableUtil;
import bronya.admin.module.db.amis.util.BaseRepositoryUtil;
import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.amis.util.DynamicTableUtil;
import bronya.admin.module.db.audit.service.SysDataAuditBizService;
import bronya.admin.module.db.badge.service.BadgeService;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisField.Sensitive;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.db.req.AmisDeleteBatchReq;
import bronya.core.base.annotation.amis.db.req.AmisViewReq;
import bronya.core.base.annotation.amis.db.req.One2ManyReq;
import bronya.core.base.annotation.amis.db.resp.AmisPageResp;
import bronya.core.base.annotation.amis.gencode.TableClassInfo;
import bronya.core.base.annotation.amis.gencode.TableClassInfo.BindBindMiddleChild;
import bronya.core.base.annotation.amis.gencode.TableClassInfo.BindMany2OneField;
import bronya.core.base.annotation.amis.gencode.TableExtract;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleChild;
import bronya.core.base.annotation.amis.inputdata.AmisConditionBuilder.ConditionColumn;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.type.AmisSelectAutoComplete;
import bronya.core.base.annotation.amis.type.OrderByType;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.constant.BulinkeModelConstant;
import bronya.core.base.constant.IConditionBuilderTarget;
import bronya.core.base.dto.DataProxy;
import bronya.core.base.gencode.BindMiddleChildReq;
import bronya.core.base.gencode.RelationshipManagementReq;
import bronya.shared.module.common.type.ActionType;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.platform.dto.AmisBeanDto;
import bronya.shared.module.rule.ConditionGroup;
import bronya.shared.module.user.SysUserVo;
import bronya.shared.module.util.JsFieldUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
// @CatchAndLog // 这个会吞异常
public class BronyaAdminBaseAmisService {
    private final AmisFiledConvertSwitch amisFiledConvertSwitch;
    private final IPlatform platformService;
    private final SysDataAuditBizService auditBizService;
    private final DatasourceService datasourceService;

    private final BadgeService badgeService;

    private final GitBronyaCfg gitBaseConf;
    private final GitProjectCfg gitProjectConf;

    public Boolean readAll(AmisBeanDto amisBeanDto, AmisLoginUser loginUser,
                           AmisDeleteBatchReq req) {
        return badgeService.toReadAll(amisBeanDto.getMainClass());
    }

    public List<ConditionColumn> conditionBuilderTarget( AmisBeanDto amisBeanDto, AmisLoginUser loginUser,
                                        Long id,  String field){
        Field declaredField = FieldUtil.getDeclaredField(amisBeanDto.getMainClass(), field);
        Assert.notNull(declaredField,"字段不存在");
        AmisField amisField = declaredField.getAnnotation(AmisField.class);
        Assert.notNull(amisField,"非amisField字段");
        Class<? extends IConditionBuilderTarget> targetDynamicHandel = amisField.conditionBuilder().targetDynamic();
        Assert.isTrue(targetDynamicHandel != IConditionBuilderTarget.class,"未实现targetDynamic");
        IConditionBuilderTarget conditionBuilderTarget = SpringUtil.getBean(targetDynamicHandel);

        BaseRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        Object record = service.getById(id);
        return conditionBuilderTarget.getConditionColumns(record);
    }

    public Optional<ICurd> getCurdImpl(AmisBeanDto amisBeanDto) {
        AmisPage amisPage = AnnotationUtil.getAnnotation(amisBeanDto.getMainClass(), AmisPage.class);
        Class<?> curd = amisPage.virtual().curd();
        if (curd == Void.class) {
            return Optional.empty();
        }
        ICurd curdImpl = SpringUtil.getBean(StrUtil.lowerFirst(curd.getSimpleName()));
        return Optional.of(curdImpl);
    }

    public DashboardResp dashboard() {
        // 基础的组件信息
        DashboardResp dashboardResp = new DashboardResp().setGitBase(gitBaseConf).setGitProjectConf(gitProjectConf);

        Optional<IDashboardApp> dashboardAppImplOpt = this.getDashboardAppImpl();
        dashboardAppImplOpt.ifPresent(app -> {
            // 有实现附加的dashboard
        });

        return dashboardResp;
    }

    /**
     * 在运行的app中需要对该接口的实现
     */
    private Optional<IDashboardApp> getDashboardAppImpl() {
        try {
            // app端实现
            return Optional.of(SpringUtil.getBean(IDashboardApp.class));
        } catch (Exception e) {
            log.info("应用未实现dashboard自定义组件");
        }
        return Optional.empty();
    }

    public boolean deleteBatch(AmisBeanDto amisBeanDto, AmisDeleteBatchReq req) {
        List<Long> ids = Arrays.stream(StringUtils.split(req.getIds(), ",")).map(Long::parseLong).toList();
        Optional<DataProxy> dataProxyService = this.findDataProxyService(amisBeanDto.getMainClass());
        for (Long id : ids) {
            dataProxyService.ifPresent(dataProxy -> {
                dataProxy.beforeDelete(id);
            });
        }
        BaseRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        boolean b = service.removeByIds(Lists.newArrayList(ids));
        for (Long id : ids) {
            dataProxyService.ifPresent(dataProxy -> {
                dataProxy.afterDelete(id);
            });
        }
        return b;
    }

    public Boolean quickSaveApi(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        Long userId = platformService.getLoginInfo().map(SysUserVo::getId).orElse(-1L);
        JSONObject root = new JSONObject(map);
        JSONArray rowsDiffs = root.getJSONArray("rowsDiff");

        for (int i = 0; i < rowsDiffs.size(); i++) {
            JSONObject rowsDiff = rowsDiffs.getJSONObject(i);
            Object o = BronyaAdminBaseAmisUtil.map2obj(rowsDiff);
            Map<String, Object> newBean = amisFiledConvertSwitch.request(amisBeanDto.getMainClass(), o);
            newBean.put("updateBy", userId);
            newBean.put("updateTime", DateUtil.formatDateTime(new Date()));
            this.update(amisBeanDto, newBean, true);
        }
        return true;
    }

    public Boolean create(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        Object o = map.get(amisBeanDto.getMainClassSimpleName());
        Map<String, Object> newBean = amisFiledConvertSwitch.request(amisBeanDto.getMainClass(), o);
        newBean.put("createBy", platformService.getLoginInfo().map(SysUserVo::getId).orElse(-1L));
        newBean.put("createTime", DateUtil.formatDateTime(new Date()));

        List<Field> notSerField = this.findNotSerField(amisBeanDto);
        Optional<DataProxy> dataProxyService = this.findDataProxyService(amisBeanDto.getMainClass());
        dataProxyService.ifPresent(dataProxy -> {
            // todo 如何map中定义的字段类型跟原类不一致,则会强制转,例如: 规则引擎配置的会变成String
            // 找出 不需要转的字段，并重新塞回去
            Object bean = BeanUtil.toBean(newBean, amisBeanDto.getMainClass());
            Object o1 = dataProxy.beforeAdd(bean);
            Map<String, Object> beanMap = Maps.newHashMap(BeanUtil.toBeanMap(o1));
            // 不需要序列化的字段，重新塞回去，例如： 组合条件
            for (Field field : notSerField) {
                beanMap.put(field.getName(),newBean.get(field.getName()));
            }
            newBean.putAll(beanMap); // todo 不行,规则引擎要想个办法,尝试是否可以使用对象
//            newBean.putAll(BeanUtil.toBeanMap(o1)); // fix: 这里对象toBeanMap后无法使用putAll,table丢失
//            Object o1 = dataProxy.beforeAdd(newBean);
//            newBean.putAll(JSONObject.parseObject(JSONObject.toJSONString(o1)));
        });


        this.rewriteVal(amisBeanDto, newBean, ActionType.add); //重写字段值
        this.checkFieldRequired(ActionType.add, amisBeanDto, newBean);// 参数校验
        BaseRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        boolean save = service.save(newBean);
        dataProxyService.ifPresent(dataProxy -> {
            dataProxy.afterAdd(BeanUtil.toBean(newBean, amisBeanDto.getMainClass()));
        });
        return save;
    }

    @SneakyThrows
    public boolean update(AmisBeanDto amisBeanDto, Map<String, Object> map, boolean quickEdit) {
        JSONObject objMap = new JSONObject(map).getJSONObject(amisBeanDto.getMainClassSimpleName());
        Object o = BronyaAdminBaseAmisUtil.map2obj(objMap);
        Map<String, Object> newBean = amisFiledConvertSwitch.request(amisBeanDto.getMainClass(), o);
        Long userId = platformService.getLoginInfo().map(SysUserVo::getId).orElse(-1L);
        newBean.put("updateBy", userId);
        newBean.put("createTime", null);
        newBean.put("updateTime", DateUtil.formatDateTime(new Date()));
        List<Field> notSerField = this.findNotSerField(amisBeanDto);
        Optional<DataProxy> dataProxyService = this.findDataProxyService(amisBeanDto.getMainClass());
        DataProxy dataProxy = dataProxyService.orElse(null);
        if (dataProxy != null) {
            Object bean = BeanUtil.toBean(newBean, amisBeanDto.getMainClass());
            Object o1 = dataProxy.beforeUpdate(bean); // 跟create一样,不能这样转bean,字段类型不匹配的情况下有异常
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o1));
            for (Field field : notSerField) {
                jsonObject.put(field.getName(),newBean.get(field.getName()));
            }
            newBean.putAll(jsonObject);
        }
        this.rewriteVal(amisBeanDto, newBean, ActionType.edit); //重写字段值
        if (!quickEdit) {
            // 快速编辑不需要验证字段,因为不是提供全部的字段
            this.checkFieldRequired(ActionType.edit, amisBeanDto, newBean); // 校验参数
        }
        BaseRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        // 先从数据库取出数据,再更新,为了做审计
        Object primaryVal = map.get(AmisFieldUtil.findPrimaryKey(amisBeanDto.getMainClass()));
        Assert.notNull(primaryVal, () -> new SysException("更新数据未传入主键"));
        Amis amis = amisBeanDto.getMainClass().getAnnotation(Amis.class);
        Object objDataByDb = null;
        if (dataProxy != null || (amis != null && amis.audit())) {
            // 有开启审计/或者有代理才需要查询数据库数据
            objDataByDb = service.getById(primaryVal.toString());
        }
        boolean b = service.updateById(newBean);
        if (amis != null && amis.audit()) {
            auditBizService.addAuditRecord(userId, amisBeanDto, primaryVal, objDataByDb, newBean);
        }
        if (dataProxy != null) {
            Object after = BeanUtil.toBean(newBean, amisBeanDto.getMainClass());
            dataProxy.afterUpdate(after);
            // 做环绕处理
            dataProxy.aroundUpdate(objDataByDb, after);
        }
        return b;
    }

    public Map<String, Object> view(AmisBeanDto amisBeanDto, AmisViewReq req) {
        BaseRepository service = BaseRepositoryUtil.getService(amisBeanDto.getMainClass());
        Object record = service.getById(req.getId());
        return this.tableRecord(amisBeanDto, record);
    }

    public Map<String, Object> tableRecord(AmisBeanDto amisBeanDto, Object record) {
        Map<String, Object> mapTarget = BronyaAdminBaseAmisUtil.obj2map(amisBeanDto.getMainClass(), record);
        Optional<DataProxy> dataProxyService = this.findDataProxyService(amisBeanDto.getMainClass());
        dataProxyService.ifPresent(dataProxy -> {
            dataProxy.table(mapTarget);
        });
        this.rewriteVal(amisBeanDto, mapTarget, ActionType.table); //重写字段值

        Map<String, Object> res = Maps.newHashMap();
        mapTarget.forEach((k, v) -> {
            res.put(StrUtil.replace(k, StrPool.DOT, JsFieldUtil.JS_FIELD_NAME_JOIN), v);
        });
        return res;
    }

    public List<AmisSelectAutoComplete> dynamicTableAutoComplete(String bean, String term) {
        AmisBeanDto amisBeanDto = AmisTableUtil.find(bean);
        AmisPage amisPage = amisBeanDto.getMainClass().getAnnotation(AmisPage.class);
        Assert.notNull(amisPage, "分表未配置页面信息:{}", bean);
        Assert.notEmpty(amisPage.dynamicTable().cols(), "未配置分表字段:{}", bean);

        DSWrapper wrap = datasourceService.getDSWrapper(amisBeanDto.getMainClass());
        QueryWrapper queryWrapper = QueryWrapper.create();
        String tablePrefix = STR."\{StrUtil.toUnderlineCase(bean)}_";
        queryWrapper.likeLeft("TABLE_NAME", STR."\{tablePrefix}%\{term}");
        queryWrapper.select("TABLE_NAME")
                .from("INFORMATION_SCHEMA.TABLES");
//        SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'bronya-demo-one' AND TABLE_NAME LIKE 'symbol_%';
        String sql = queryWrapper.toSQL();
        log.info("查询分表sql:{}", sql);
        List<String> tableNames = Db.of(wrap).query(sql, String.class).stream().map(tableName -> StrUtil.removeAll(tableName, tablePrefix)).toList();
        return tableNames.stream().map(tableName -> new AmisSelectAutoComplete(tableName, tableName, tableName)).toList();
    }

    public AmisPageResp page(AmisBeanDto amisBeanDto, Map<String, Object> map) {
        Class<?> mainClass = amisBeanDto.getMainClass();
        Integer currentPage = MapUtil.getInt(map, "page", 1);
        Integer perPage = MapUtil.getInt(map, "perPage", 10);
        if (perPage > 100) {
            perPage = 100;
        }

        Optional<ICurd> curdImplOpt = this.getCurdImpl(amisBeanDto);
        if (curdImplOpt.isPresent()) {
            ICurd iCurd = curdImplOpt.get();
            PageResult page = iCurd.page(amisBeanDto, currentPage, perPage, map);
            return this.processPageData(amisBeanDto, page);
        }

        Map<String, Object> queryObjectMap = this.newQueryByPage(amisBeanDto, map);
        QueryWrapper queryWrapper = QueryWrapper.create();
        {
            // 系统配置的orderBy
            AmisPage amisPage = AnnotationUtil.getAnnotation(mainClass, AmisPage.class);
            AmisPage.OrderBy[] orderBIES = amisPage.orderBys();
            for (AmisPage.OrderBy orderBY : orderBIES) {
                for (String col : orderBY.cols()) {
                    queryWrapper.orderBy(StrUtil.toUnderlineCase(col), orderBY.type() == OrderByType.ASC);
                }
            }
            String orderBy = JsFieldUtil.toObjField(MapUtil.getStr(map, "orderBy")); //orderBy = "Hobby.name"
            if (StrUtil.isNotBlank(orderBy)) {
                List<String> split = SplitUtil.split(orderBy, StrPool.DOT);
                String orderClassName = split.getFirst();
                if (orderClassName.equals(amisBeanDto.getMainClassSimpleName())) {
                    // 属于当前对象的排序
                    String orderDir = MapUtil.getStr(map, "orderDir");
                    boolean asc = "asc".equalsIgnoreCase(orderDir);
                    String orderByCol = split.getLast();
                    queryWrapper.orderBy(StrUtil.toUnderlineCase(orderByCol), asc);
                }
            }
        }

        String tableName = StrUtil.toUnderlineCase(mainClass.getSimpleName());
        queryWrapper.select(STR."distinct \{tableName}.*");
        boolean isDynamicTable = DynamicTableUtil.isDynamicTable(amisBeanDto.getMainClass());
        if (isDynamicTable) {
            String dynamicTable = MapUtil.getStr(map, "dynamicTable");
            Assert.notBlank(dynamicTable, () -> new BizException("请先选择分表"));
            queryWrapper.from(STR."\{tableName}_\{dynamicTable}").as(tableName);
        } else {
            queryWrapper.from(amisBeanDto.getMainClass()).as(tableName);
        }

        // 这里还需要兼容多对多的查询
        TableClassInfo tableInfoAndFields = TableExtract.getTableInfoAndFields(mainClass);
        BindMiddleChildReq bindMiddleChild = MapUtil.get(map, "BindMiddleChild", BindMiddleChildReq.class);
        this.many2many(bindMiddleChild, amisBeanDto, map, tableInfoAndFields, queryWrapper);
        this.one2many(amisBeanDto, map, tableInfoAndFields, queryWrapper);

        // 关联值备注查询出来
        this.many2One(amisBeanDto, map, tableInfoAndFields, queryWrapper);

        DSWrapper wrap = datasourceService.getDSWrapper(mainClass);

        queryWrapper.where(queryObjectMap);
        String sql = queryWrapper.toSQL();
        if (bindMiddleChild != null
                && bindMiddleChild.getRelationshipType() == RelationshipManagementReq.RelationshipType.disconnect) {
            // not in 需要修复下sql
            sql = StrUtil.replace(sql, "NOT IN ('", "NOT IN (");
            sql = StrUtil.replaceLast(sql, "'')", "')", false);
        }
        log.debug("查询sql:{}", sql);
        PageResult<Entity> page = Db.of(wrap).page(sql, new Page(currentPage - 1, perPage));
        return this.processPageData(amisBeanDto, page);
    }

    private AmisPageResp processPageData(AmisBeanDto amisBeanDto, PageResult page) {
        List<Object> recordsMap = Lists.newArrayList(); // 存放 Map<String, Object> mapTarget
        List<Object> list = page.stream().toList();
        // 对每条数据渲染有徽章数据
        String primaryKeyName = this.getPrimaryKeyName(amisBeanDto);
        List<Long> primaryKeyByRecordBadge = this.findPrimaryKeyByRecordBadge(amisBeanDto, list); // 需要渲染的ids数据
        for (Object record : list) {
            Map<String, Object> stringObjectMap = this.tableRecord(amisBeanDto, record);
            Object id = stringObjectMap.get(primaryKeyName);
            if (primaryKeyByRecordBadge.contains(ConvertUtil.toLong(id))) {
                // 未读取的徽章有该条记录,需要渲染徽章
                stringObjectMap.put("badgeText","未读");
                stringObjectMap.put("badgeLevel","info");
            }
            recordsMap.add(stringObjectMap);
        }
        badgeService.toRead(amisBeanDto.getMainClass(),primaryKeyByRecordBadge); // 清理未读状态,更新为已读
        return new AmisPageResp(ConvertUtil.toLong(page.getTotal()), recordsMap);
    }

    private String getPrimaryKeyName(AmisBeanDto amisBeanDto){
        List<Field> primaryKeyFields = Arrays.stream(FieldUtil.getFields(amisBeanDto.getMainClass())).filter(temp -> temp.getAnnotation(ColumnId.class) != null).toList();
        Assert.isTrue(primaryKeyFields.size() == 1, () -> new BizException("主键字段只能有一个"));
        return primaryKeyFields.getFirst().getName();
    }

    /**
     * 找出带有徽章的数据
     * <p>
     * "badgeText": "默认",
     * "badgeLevel": "info"
     *
     * @return 徽章表未读的id
     */
    private List<Long> findPrimaryKeyByRecordBadge(AmisBeanDto amisBeanDto, List<Object> list){
        // 1. 找出主键名称
        String primaryKeyName = this.getPrimaryKeyName(amisBeanDto);
        // 2. 找出对应主键名称的id值
        List<Object> primaryKeys = list.stream().map(record -> {
            Map<String, Object> recordVals = BronyaAdminBaseAmisUtil.obj2map(amisBeanDto.getMainClass(), record);
            return recordVals.get(primaryKeyName);
        }).toList();
        // 3. 从徽章表确认是否有该未读的记录
        // 4. 对徽章表这些记录修改为已读取
        return badgeService.findUnReadPrimaryKey(amisBeanDto.getMainClass(), primaryKeys);
    }

    /**
     * 关联键的备注值查询
     * 例如: SELECT distinct hobby.*,(select name from student where id = hobby.student_id) as 'student_Id_Desc' FROM `hobby` AS `hobby` ORDER BY name DESC, remark DESC, id asc
     */
    private void many2One(AmisBeanDto selfAmisBeanDto, Map<String, Object> map, TableClassInfo tableInfoAndFields,
                          QueryWrapper queryWrapper) {

        List<BindMany2OneField> bindMany2OneFields = tableInfoAndFields.getBindMany2OneFields();
//        (select name from student where id = hobby.student_id) as 'student_Id_Desc'
        for (BindMany2OneField bindMany2OneField : bindMany2OneFields) {
            BindMany2One bindMany2One = bindMany2OneField.annotation();
            QueryWrapper many2OneSql = QueryWrapper.create();
            many2OneSql.select(StrUtil.toUnderlineCase(bindMany2One.labelField()));
            many2OneSql.from(bindMany2One.entity());
            String underlineCase = StrUtil.toUnderlineCase(bindMany2OneField.field().getName());
            String where = STR."\{StrUtil.toUnderlineCase(bindMany2One.valueField())} = \{StrUtil.toUnderlineCase(selfAmisBeanDto.getMainClassSimpleName())}.\{underlineCase}";
            many2OneSql.where(where);
            String sql = many2OneSql.toSQL();
            String selectCol = STR."(\{sql}) as \{underlineCase}_desc";
            queryWrapper.select(selectCol);
        }
    }

    private void one2many(AmisBeanDto selfAmisBeanDto, Map<String, Object> map, TableClassInfo tableInfoAndFields,
                          QueryWrapper queryWrapper) {
        One2ManyReq one2ManyReq = MapUtil.get(map, "One2ManyReq", One2ManyReq.class);
        List<BindMany2OneField> bindMany2OneFields = tableInfoAndFields.getBindMany2OneFields();
        if (CollUtil.isEmpty(bindMany2OneFields) || one2ManyReq == null || StrUtil.isBlank(one2ManyReq.getEntity())) {
            return;
        }
        String where =
                STR.
                        "\{StrUtil.toUnderlineCase(selfAmisBeanDto.getMainClassSimpleName())}.\{StrUtil.toUnderlineCase(one2ManyReq.getEntityField())} = \{one2ManyReq.getEntityFieldVal()}";
        queryWrapper.where(where);
    }
    // 处理多对多查询

    private void many2many(BindMiddleChildReq bindMiddleChild, AmisBeanDto amisBeanDto, Map<String, Object> map,
                           TableClassInfo tableInfoAndFields, QueryWrapper queryWrapper) {
        Class<?> mainClass = amisBeanDto.getMainClass();
        List<BindBindMiddleChild> bindBindMiddleChildrenList = tableInfoAndFields.getBindBindMiddleChildren();

        if (CollUtil.isEmpty(bindBindMiddleChildrenList) || bindMiddleChild == null
                || StrUtil.isBlank(bindMiddleChild.getMiddleEntity())) {
            return;
        }
        AmisBeanDto amisBeanDtoMiddle = AmisTableUtil.find(bindMiddleChild.getMiddleEntity());
        Class<?> middleClass = amisBeanDtoMiddle.getMainClass();
        for (BindBindMiddleChild bindBindMiddleChild : bindBindMiddleChildrenList) {
            BindMiddleChild annotation = bindBindMiddleChild.annotation();
            Class<?> entity = annotation.entity();
            if (entity != middleClass) {
                continue;
            }
            String joinField = annotation.conditionRef().joinField();
            String underlineMainClass = StrUtil.toUnderlineCase(mainClass.getSimpleName());
            String underlineBindMiddleChildSelfField = StrUtil.toUnderlineCase(bindMiddleChild.getSelfField());
            String underlineMiddleClassSimpleName = StrUtil.toUnderlineCase(middleClass.getSimpleName());

            String a =
                    STR.
                            "\{underlineMainClass}.\{underlineBindMiddleChildSelfField} = \{underlineMiddleClassSimpleName}.\{StrUtil.toUnderlineCase(joinField)}";
            queryWrapper.leftJoin(middleClass).on(a);
            if (bindMiddleChild.getRelationshipType() == RelationshipManagementReq.RelationshipType.connect) {
                String whereKey = STR.
                        "\{StrUtil.toUnderlineCase(bindMiddleChild.getMiddleEntity())}.\{StrUtil.toUnderlineCase(bindMiddleChild.getJoinField())}";
                String whereVal = bindMiddleChild.getSelfFieldVal();
                queryWrapper.where(STR."\{whereKey} = \{whereVal}");
            } else {
                // 非关联的查询
                // SELECT s.*
                // FROM student s
                // LEFT JOIN ref_school_student rss ON s.id = rss.student_id
                // WHERE rss.student_id IS NULL
                // AND s.id NOT IN (
                // SELECT DISTINCT rss.student_id
                // FROM ref_school_student rss
                // WHERE rss.school_id = 1
                // );
                QueryWrapper subQuery = QueryWrapper.create();
                subQuery.select(STR."DISTINCT \{underlineMiddleClassSimpleName}.\{StrUtil.toUnderlineCase(joinField)}");
                subQuery.from(StrUtil.toUnderlineCase(bindMiddleChild.getMiddleEntity()));
                subQuery.eq(
                        STR.
                                "\{StrUtil.toUnderlineCase(bindMiddleChild.getMiddleEntity())}.\{StrUtil.toUnderlineCase(bindMiddleChild.getJoinField())}",
                        bindMiddleChild.getSelfFieldVal());
                String subSql = subQuery.toSQL();

                queryWrapper.notIn(STR."\{underlineMainClass}.\{underlineBindMiddleChildSelfField}", subSql);
                // SELECT student.* FROM `student` LEFT JOIN `ref_school_student` ON student.id =
                // ref_school_student.student_id WHERE student.id NOT IN ('SELECT DISTINCT ref_school_student.student_id
                // FROM `ref_school_student` WHERE ref_school_student.school_id = '1'')
            }
        }
    }
    // 找出数据库字段类型跟类型不一致的字段，例如组合条件是json，变成对象再序列化就非json了

    private List<Field> findNotSerField(AmisBeanDto amisBeanDto){
        List<Field> fields = Lists.newArrayList();
        for (Field field : FieldUtil.getFields(amisBeanDto.getMainClass())) {
            AmisField amisField = field.getAnnotation(AmisField.class);
            if(amisField == null){
                continue;
            }
            AmisFieldView add = amisField.add(); // 连新增都不需要序列化，那么更新也不需要，只要去add就可以
            if (add.type() != AmisFieldView.ViewType.组合条件) {
                continue;
            }
            fields.add(field);
        }
        return fields;
    }

    private Optional<DataProxy> findDataProxyService(Class<?> mainClass) {
        Amis amis = AnnotationUtil.getAnnotation(mainClass, Amis.class);
        if (amis == null) {
            return Optional.empty();
        }
        Class<? extends DataProxy> dataProxyClass = amis.dataProxy();
        if (dataProxyClass == DataProxy.class) {
            return Optional.empty();
        }
        DataProxy bean = SpringUtil.getBean(dataProxyClass);
        return Optional.of(bean);
    }

    public Map<String, Object> newQueryByPage(AmisBeanDto amisBeanDto, Map<String, Object> pageQueryMap) {
        JSONObject objectQuery =
                Optional.ofNullable(new JSONObject(pageQueryMap).getJSONObject(amisBeanDto.getMainClassSimpleName()))
                        .orElse(new JSONObject());
        Map<String, Object> queryObjectMap = Maps.newHashMap();
        for (String key : objectQuery.keySet()) {
            Object obj = objectQuery.get(key);
            if (obj instanceof CharSequence str) {
                // 字符串有可能是空
                if (StrUtil.isNotBlank(str)) {
                    queryObjectMap.put(StrUtil.toUnderlineCase(key), obj);
                }
                continue;
            }
            queryObjectMap.put(StrUtil.toUnderlineCase(key), obj);
        }
        return queryObjectMap;
    }

    // 重写值
    private void rewriteVal(AmisBeanDto amisBeanDto, Map<String, Object> newBean, ActionType actionType) {
        for (Field field : FieldUtil.getFields(amisBeanDto.getMainClass())) {
            AmisField amisField = field.getAnnotation(AmisField.class);
            if (amisField == null) {
                continue;
            }
            switch (actionType) {
                case add -> {
                    this.rewriteValCreateOrEdit(field, amisField.add(), newBean);
                }
                case edit -> {
                    this.rewriteValCreateOrEdit(field, amisField.edit(), newBean);
                }
                case detail, table -> {
                    this.rewriteValDetailOrTable(amisBeanDto, field, amisField.table(), newBean, amisField.sensitive());
                }
            }
        }
    }

    private final String uploadPath = "/admin/sys-file/image/";

    private void rewriteValCreateOrEdit(Field field, AmisFieldView amisFieldView, Map<String, Object> newBean) {
        if (amisFieldView.type() == AmisFieldView.ViewType.组合条件) {
            // {id=a6b5a28bc654, conjunction=and, children=[{id=915c98d5b5bc, left={type=field, field=availBal}, op=equal, right=1}]}
            Object value = newBean.get(field.getName());
            String jsonString = JSONObject.toJSONString(value);
            newBean.put(field.getName(), jsonString);
        } else if (amisFieldView.type() == AmisFieldView.ViewType.上传图片) {
            String value = StrUtil.toString(newBean.get(field.getName()));
            value = StrUtil.removeAll(value, uploadPath);
            newBean.put(field.getName(), value);
        }
    }

    private void rewriteValDetailOrTable(AmisBeanDto amisBeanDto, Field field, AmisFieldView amisFieldView, Map<String, Object> newBean, Sensitive sensitive) {
        AmisFieldView.ViewType type = amisFieldView.type();
        String key = STR."\{amisBeanDto.getMainClassSimpleName()}.\{field.getName()}";
        if (sensitive.type() != AmisField.SensitiveType.none) {
            // 该字段为敏感字段,不需要前端返回值,删除
            newBean.remove(key);
        }
        if (type == AmisFieldView.ViewType.组合条件) {
            Object value = newBean.get(key);
            String json = StrUtil.toString(value);
            if (!JSONUtil.isTypeJSON(json)) {
                return;
            }
            ConditionGroup conditionGroup = JSONObject.parseObject(json, ConditionGroup.class);
            newBean.put(key, conditionGroup);
        } else if (type == AmisFieldView.ViewType.上传图片) {
            AmisField amisField = field.getAnnotation(AmisField.class);
            String customUrl = amisField.staticImage().customUrl();
            String url = customUrl.equals(BulinkeModelConstant.UPLOAD_FILE_PATH)?uploadPath:customUrl;
            Object fileName = newBean.get(key);
            newBean.put(key, STR."\{url}\{fileName}");
        } else if(type == AmisFieldView.ViewType.视频){
            AmisField amisField = field.getAnnotation(AmisField.class);
            String customUrl = amisField.video().src();
            Object fileName = newBean.get(key);
            newBean.put(key, STR."\{customUrl}\{fileName}");
        }

    }


    /**
     * 校验参数
     */
    private void checkFieldRequired(ActionType actionType, AmisBeanDto amisBeanDto, Map<String, Object> newBean) {
        for (Field field : FieldUtil.getFields(amisBeanDto.getMainClass())) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            AmisField amisField = field.getAnnotation(AmisField.class);
            if (amisField != null) {
                AmisFieldView amisFieldView = null;
                if (actionType == ActionType.add) {
                    amisFieldView = amisField.add();
                } else if (actionType == ActionType.edit) {
                    amisFieldView = amisField.edit();
                }
                if (amisFieldView.type() == AmisFieldView.ViewType.隐藏) {
                    // 页面隐藏该字段,不需要进行校验
                    continue;
                }
            }
            if (column.notNull() && newBean.get(field.getName()) == null) { // 规则不为null,但是值为null
                throw new BizException(STR."[\{column.comment()}] 输入值为空!");
            }
        }
    }
}
