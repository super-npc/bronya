package bronya.admin.module.db.badge.domain;

import java.util.Date;

import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.autofill.annotation.InsertFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.db.badge.proxy.BadgeProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.OrderByType;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.ReadStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "徽章事件")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@Amis(extBean = Badge.BadgeExt.class, dataProxy = BadgeProxy.class)
@AmisPage(menu = @Menu(show = false, module = "系统", group = "数据管理", menu = "菜单"),
    orderBys = {@AmisPage.OrderBy(cols = {Badge.Fields.readStatus}, type = OrderByType.ASC),
        @AmisPage.OrderBy(cols = {Badge.Fields.id}, type = OrderByType.ASC)},
    btns = @Btns(add = false, edit = false, delete = false),
    headerToolbar = @Operation(optBtns = {@Operation.OptBtn(name = "随机扔徽章", level = Operation.BtnLevelType.info,
        method = Method.GET, batch = true, url = "/admin/badge/random-add-badge")}))
@TableIndexes({@TableIndex(fields = {Badge.Fields.readStatus}, type = IndexTypeEnum.NORMAL),
    @TableIndex(fields = {Badge.Fields.bean}, type = IndexTypeEnum.NORMAL)})
public class Badge {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "状态", defaultValue = "UNREAD")
    @AmisField(sortable = true, search = true)
    private ReadStatus readStatus;

    @Column(notNull = true, comment = "bean")
    @AmisField
    private String bean;

    @Column(notNull = true, comment = "主键")
    @AmisField
    private Long primaryKey;

    @Column(comment = "原因", type = MysqlTypeConstant.TEXT)
    @AmisField
    private String reason;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @Data
    public static class BadgeExt {

    }
}
