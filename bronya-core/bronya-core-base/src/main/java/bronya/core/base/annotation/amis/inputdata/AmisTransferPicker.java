package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.List;

import org.dromara.hutool.core.reflect.ConstructorUtil;
import org.dromara.hutool.http.meta.Method;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.handler.IAmisTransferPickerHandler;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.TransferPickerOptionsVo;
import bronya.core.base.annotation.amis.type.TransferPicker.TransferPickerBorderMode;
import bronya.core.base.annotation.amis.type.TransferPicker.TransferPickerLeftMode;
import bronya.core.base.annotation.amis.type.TransferPicker.TransferPickerSize;
import bronya.core.base.annotation.amis.type.TransferPicker.TransferPickerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisTransferPicker extends AmisComponents {

    public AmisTransferPicker(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private List<TransferPickerOptionsVo> options;
    /** 动态选项组 */
    private AmisApi source;
    /** 如果想通过接口检索，可以设置这个 api。 */
    private AmisApi searchApi;
    /** 结果面板跟随模式，目前只支持list、table、tree（tree 目前只支持非延时加载的tree） */
    private Boolean resultListModeFollowSelect;
    /** 是否显示统计数据 */
    private Boolean statistics;
    /** 左侧的标题文字 */
    private String selectTitle;
    /** 右侧结果的标题文字 */
    private String resultTitle;
    /** 结果可以进行拖拽排序（结果列表为树时，不支持排序） */
    private Boolean sortable;
    /**
     * 可选：list、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个
     * tree）。
     */
    private TransferPickerType selectMode;
    /** 如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。 */
    private TransferPickerType searchResultMode;
    /** 左侧列表搜索功能，当设置为 true 时表示可以通过输入部分内容检索出选项项。 */
    private Boolean searchable;
    /** 左侧列表搜索框提示 */
    private String searchPlaceholder;
    /** 当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。 */
    private Object columns;
    /** 当展示形式为 associated 时用来配置左边的选项集。 */
    private Object leftOptions;
    /** 当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。 */
    private TransferPickerLeftMode leftMode;
    /** 当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。 */
    private TransferPickerType rightMode;
    /** 结果（右则）列表的检索功能，当设置为 true 时，可以通过输入检索模糊匹配检索内容（目前树的延时加载不支持结果搜索功能） */
    private Boolean resultSearchable;
    /** 右侧列表搜索框提示 */
    private String resultSearchPlaceholder;
    /** 用来自定义选项展示 */
    private Object menuTpl;
    /** 用来自定义值的展示 */
    private Object valueTpl;
    /** 每个选项的高度，用于虚拟渲染 */
    private Integer itemHeight;
    /** 在选项数量超过多少时开启虚拟渲染 */
    private Integer virtualThreshold;
    /** 边框模式，'full'为全边框，'half'为半边框，'none'为没边框 */
    private TransferPickerBorderMode borderMode;
    /** 弹窗大小，支持: xs、sm、md、lg、xl、full */
    private TransferPickerSize pickerSize;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface TransferPicker {
        /** 数据驱动 */
        Class<? extends IAmisTransferPickerHandler> handler() default IAmisTransferPickerHandler.class;

        String url() default "";

        /** 结果面板跟随模式，目前只支持list、table、tree（tree 目前只支持非延时加载的tree） */
        boolean resultListModeFollowSelect() default true;

        /** 是否显示统计数据 */
        boolean statistics() default true;

        /** 左侧的标题文字 */
        String selectTitle() default "请选择";

        /** 右侧结果的标题文字 */
        String resultTitle() default "当前选择";

        /** 结果可以进行拖拽排序（结果列表为树时，不支持排序） */
        boolean sortable() default false;

        /**
         * 可选：list、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个
         * tree）。
         */
        TransferPickerType selectMode() default TransferPickerType.tree;

        /** 如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。 */
        TransferPickerType searchResultMode() default TransferPickerType.tree;

        /** 左侧列表搜索功能，当设置为 true 时表示可以通过输入部分内容检索出选项项。 */
        boolean searchable() default false;

        /** 左侧列表搜索框提示 */
        String searchPlaceholder() default "";

        // /** 当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。 */
        // Object columns() default "";
        // /** 当展示形式为 associated 时用来配置左边的选项集。 */
        // Object leftOptions() default "";
        /** 当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。 */
        TransferPickerLeftMode leftMode() default TransferPickerLeftMode.tree;

        /** 当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。 */
        TransferPickerType rightMode() default TransferPickerType.tree;

        /** 结果（右则）列表的检索功能，当设置为 true 时，可以通过输入检索模糊匹配检索内容（目前树的延时加载不支持结果搜索功能） */
        boolean resultSearchable() default false;

        /** 右侧列表搜索框提示 */
        String resultSearchPlaceholder() default "";

        // /** 用来自定义选项展示 */
        // Object menuTpl() default "";
        // /** 用来自定义值的展示 */
        // Object valueTpl() default "";
        /** 每个选项的高度，用于虚拟渲染 */
        int itemHeight() default 31;

        /** 在选项数量超过多少时开启虚拟渲染 */
        int virtualThreshold() default 100;

        /** 边框模式，'full'为全边框，'half'为半边框，'none'为没边框 */
        TransferPickerBorderMode borderMode() default TransferPickerBorderMode.half;

        /** 弹窗大小，支持: xs、sm、md、lg、xl、full */
        TransferPickerSize pickerSize() default TransferPickerSize.lg;
    }

    public static AmisTransferPicker change(Boolean required,TransferPicker annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisTransferPicker temp = new AmisTransferPicker(required,type, name, label,disabled,width,remark);
        if (annotation.handler() != IAmisTransferPickerHandler.class) {
            IAmisTransferPickerHandler iAmisTransferPickerHandler = ConstructorUtil.newInstance(annotation.handler());
            temp.setOptions(iAmisTransferPickerHandler.transferPickerOptions());
        }
        temp.setSource(new AmisApi(Method.GET, annotation.url()));
        // temp.setSource(annotation.source());
        // temp.setSearchApi(annotation.searchApi());
        temp.setResultListModeFollowSelect(annotation.resultListModeFollowSelect());
        temp.setStatistics(annotation.statistics());
        temp.setSelectTitle(annotation.selectTitle());
        temp.setResultTitle(annotation.resultTitle());
        temp.setSortable(annotation.sortable());
        temp.setSelectMode(annotation.selectMode());
        temp.setSearchResultMode(annotation.searchResultMode());
        temp.setSearchable(annotation.searchable());
        temp.setSearchPlaceholder(annotation.searchPlaceholder());
        // temp.setColumns(annotation.columns());
        // temp.setLeftOptions(annotation.leftOptions());
        temp.setLeftMode(annotation.leftMode());
        temp.setRightMode(annotation.rightMode());
        temp.setResultSearchable(annotation.resultSearchable());
        temp.setResultSearchPlaceholder(annotation.resultSearchPlaceholder());
        // temp.setMenuTpl(annotation.menuTpl());
        // temp.setValueTpl(annotation.valueTpl());
        temp.setItemHeight(annotation.itemHeight());
        temp.setVirtualThreshold(annotation.virtualThreshold());
        temp.setBorderMode(annotation.borderMode());
        temp.setPickerSize(annotation.pickerSize());
        return temp;
    }
}