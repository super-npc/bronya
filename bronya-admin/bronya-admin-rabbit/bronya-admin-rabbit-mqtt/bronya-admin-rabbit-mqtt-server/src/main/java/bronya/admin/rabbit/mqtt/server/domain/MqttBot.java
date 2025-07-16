package bronya.admin.rabbit.mqtt.server.domain;

import java.util.Date;

import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import org.dromara.autotable.annotation.IndexField;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexSortTypeEnum;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "mqttBot")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis(audit = true, extBean = MqttBot.MqttBotExt.class)
@AmisPage(autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
    menu = @Menu(module = "例子", group = "队列", menu = "mqtt机器人"),
    operation = @Operation(optBtns = {
        @Operation.OptBtn(name = "上线", level = Operation.BtnLevelType.info, method = Method.GET, batch = true,
            url = "/demo/rabbit/mqtt/up"),
        @Operation.OptBtn(name = "下线", level = Operation.BtnLevelType.info, method = Method.GET, batch = true,
            url = "/demo/rabbit/mqtt/down")}))
@TableIndexes({@TableIndex(indexFields = {@IndexField(field = MqttBot.Fields.deviceId, sort = IndexSortTypeEnum.DESC)},
    type = IndexTypeEnum.UNIQUE)})
public class MqttBot extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(search = true)
    private Long id;

    @Column(notNull = true, comment = "设备id")
    @AmisField(quickEdit = true, search = true, sortable = true, copyable = true)
    private String deviceId;

    @Column(notNull = true, comment = "状态", defaultValue = "offline")
    @AmisField
    private MqttLiveStatus status;

    @Column(notNull = true, comment = "私有订阅")
    @AmisField(quickEdit = true, copyable = true, remark = "多个以逗号分隔")
    private String topic;

    @Data
    public static class MqttBotExt {
        @AmisField(comment = "运行信息",
            editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true),
            table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
            detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String running;
    }
}
