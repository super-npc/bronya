package bronya.core.base.annotation.amis.type.TransferPicker;

public enum TransferPickerType {
    // 分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个 tree)
    list, table, tree, chained, associated
}
