//package amis.属性表;
//
//import npc.bulinke.generate.amistype.BaseAmisTest;
//import org.junit.jupiter.api.Test;
//
//public class AmisComponentsGenJava extends BaseAmisTest {
//
//    @Test
//    public void sparkline() {
//        String str = """
//            width	number		宽度
//            height	number		高度
//            placeholder	string		数据为空时显示的内容
//            """;
//        this.parse4列("Sparkline", str);
//    }
//
//    @Test
//    public void video() {
//        String str = """
//            className	string		外层 Dom 的类名
//            src	string		视频地址
//            isLive	boolean	false	是否为直播，视频为直播时需要添加上，支持flv和hls格式
//            videoType	string		指定直播视频格式
//            poster	string		视频封面地址
//            muted	boolean		是否静音
//            loop	boolean		是否循环播放
//            autoPlay	boolean		是否自动播放
//            rates	array		倍数，格式为[1.0, 1.5, 2.0]
//            frames	object		key 是时刻信息，value 可以可以为空，可有设置为图片地址，请看上方示例
//            jumpBufferDuration	boolean		点击帧的时候默认是跳转到对应的时刻，如果想提前 3 秒钟，可以设置这个值为 3
//            stopOnNextFrame	boolean		到了下一帧默认是接着播放，配置这个会自动停止
//            """;
//        this.parse4列("Video", str);
//    }
//
//    @Test
//    public void card() {
//        String str = """
//            className	string		外层 Dom 的类名
//            href	模板		外部链接
//            header	Object		Card 头部内容设置
//            header.className	string		头部类名
//            header.title	模板		标题
//            header.titleClassName	string		标题类名
//            header.subTitle	模板		副标题
//            header.subTitleClassName	string		副标题类名
//            header.subTitlePlaceholder	string		副标题占位
//            header.description	模板		描述
//            header.descriptionClassName	string		描述类名
//            header.descriptionPlaceholder	string		描述占位
//            header.avatar	模板		图片
//            header.avatarClassName	string	"pull-left thumb avatar b-3x m-r"	图片包括层类名
//            header.imageClassName	string		图片类名
//            header.avatarText	模板		如果不配置图片，则会在图片处显示该文本
//            header.avatarTextBackground	Array		设置文本背景色，它会根据数据分配一个颜色
//            header.avatarTextClassName	string		图片文本类名
//            header.highlight	boolean	false	是否显示激活样式
//            header.highlightClassName	string		激活样式类名
//            header.href	模板		点击卡片跳转的链接地址
//            header.blank	boolean	true	是否新窗口打开
//            body	Array		内容容器，主要用来放置非表单项组件
//            bodyClassName	string		内容区域类名
//            actions	Array<Action>		配置按钮集合
//            actionsCount	number	4	按钮集合每行个数
//            itemAction	Action		点击卡片的行为
//            media	Object		Card 多媒体部内容设置
//            media.type	'image'|'video'		多媒体类型
//            media.url	string		图片/视频链接
//            media.position	'left'|'right'|'top'|'bottom'	'left'	多媒体位置
//            media.className	string	"w-44 h-28"	多媒体类名
//            media.isLive	boolean	false	视频是否为直播
//            media.autoPlay	boolean	false	视频是否自动播放
//            media.poster	string	false	视频封面
//            secondary	模板		次要说明
//            toolbar	Array<Action>		工具栏按钮
//            dragging	boolean	false	是否显示拖拽图标
//            selectable	boolean	false	卡片是否可选
//            checkable	boolean	true	卡片选择按钮是否禁用
//            selected	boolean	false	卡片选择按钮是否选中
//            hideCheckToggler	boolean	false	卡片选择按钮是否隐藏
//            multiple	boolean	false	卡片是否为多选
//            useCardLabel	boolean	true	卡片内容区的表单项 label 是否使用 Card 内部的样式
//            """;
//        this.parse4列("Card", str);
//    }
//
//    @Test
//    public void timelineItem() {
//        String str = """
//            time	string 		节点时间
//            title	string | SchemaNode		节点标题
//            detail	string		节点详细描述（折叠）
//            detailCollapsedText	string	展开	详细内容折叠时按钮文案
//            detailExpandedText	string	折叠	详细内容展开时按钮文案
//            color	string | level样式（info、success、warning、danger）	#DADBDD	时间轴节点颜色
//            icon	string		icon 名，支持 fontawesome v4 或使用 url（优先级高于 color）
//            iconClassName	string		节点图标的 CSS 类名（优先级高于统一配置的 iconClassName ，（3.4.0 版本支持））
//            timeClassName	string		节点时间的 CSS 类名（优先级高于统一配置的 timeClassName，（3.4.0 版本支持））
//            titleClassName	string		节点标题的 CSS 类名（优先级高于统一配置的 titleClassName，（3.4.0 版本支持））
//            detailClassName	string		节点详情的 CSS 类名（优先级高于统一配置的 detailClassName，（3.4.0 版本支持））
//            """;
//        this.parse4列("TimelineItem", str);
//    }
//
//    @Test
//    public void timeline() {
//        String str = """
//            type	string		"timeline" 指定为 时间轴 渲染器
//            items	Array<timelineItem>	[]	配置节点数据
//            source	API 或 数据映射		数据源，可通过数据映射获取当前数据域变量、或者配置 API 对象
//            mode	left | right | alternate	right	指定文字相对于时间轴的位置，仅 direction=vertical 时支持
//            direction	vertical | horizontal	vertical	时间轴方向
//            reverse	boolean	false	根据时间倒序显示
//            """;
//        this.parse4列("Timeline", str);
//    }
//
//    @Test
//    public void markdown() {
//        String str = """
//            name	string		名称
//            value	string		静态值
//            className	string		类名
//            src	Api		外部地址
//            """;
//        this.parse3列("Markdown", str);
//    }
//
//    @Test
//    public void conditionBuilder() {
//        String str = """
//            className	string		外层 dom 类名
//            fieldClassName	string		输入字段的类名
//            source	string		通过远程拉取配置项
//            embed	boolean	true	内嵌展示
//            title	string		弹窗配置的顶部标题
//            fields			字段配置
//            showANDOR	boolean		用于 simple 模式下显示切换按钮
//            showNot	boolean		是否显示「非」按钮
//            draggable	boolean	true	是否可拖拽
//            searchable	boolean		字段是否可搜索
//            selectMode	'list' | 'tree' | 'chained'	'list'	组合条件左侧选项类型。'chained'模式需要3.2.0及以上版本
//            addBtnVisibleOn	string		表达式：控制按钮“添加条件”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型	3.2.0
//            addGroupBtnVisibleOn	string		表达式：控制按钮“添加条件组”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型	3.2.0
//            inputSettings	InputSettings		开启公式编辑模式时的输入控件类型	3.2.0
//            formula	object		字段输入控件变成公式编辑器。	3.2.0
//            showIf	boolean		开启后条件中额外还能配置启动条件。	3.2.0
//            formulaForIf	object		给 showIF 表达式用的公式信息	3.4.0
//            """;
//        this.parse4列("ConditionBuilder", str);
//    }
//
//    @Test
//    public void verificationCode() {
//        String str = """
//            length	number	6	验证码的长度，根据长度渲染对应个数的输入框
//            masked	boolean	false	是否是密码模式
//            separator	string		分隔符，支持表达式, 表达式只可以访问 index、character 变量, 参考自定义分隔符
//            """;
//        this.parse4列("VerificationCode", str);
//    }
//
//    @Test
//    public void inputFile() {
//        String str = """
//            receiver	API		上传文件接口
//            accept	string	text/plain	默认只支持纯文本，要支持其他类型，请配置此属性为文件后缀.xxx
//            capture	string	undefined	用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源
//            asBase64	boolean	false	将文件以base64的形式，赋值给当前组件
//            asBlob	boolean	false	将文件以二进制的形式，赋值给当前组件
//            maxSize	number		默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B
//            maxLength	number		默认没有限制，当设置后，一次只允许上传指定数量文件。
//            multiple	boolean	false	是否多选。
//            drag	boolean	false	是否为拖拽上传
//            extractValue	boolean	false	提取值
//            autoUpload	boolean	true	否选择完就自动开始上传
//            hideUploadButton	boolean	false	隐藏上传按钮
//            fileField	string	file	如果你不想自己存储，则可以忽略此属性。
//            nameField	string	name	接口返回哪个字段用来标识文件名
//            valueField	string	value	文件的值用那个字段来标识。
//            urlField	string	url	文件下载地址的字段名。
//            btnLabel	string		上传按钮的文字
//            startChunkApi	API		startChunkApi
//            chunkApi	API		chunkApi
//            finishChunkApi	API		finishChunkApi
//            documentation	string		文档内容
//            documentLink	string		文档链接
//            """;
//        this.parse4列("InputFile", str);
//    }
//
//    @Test
//    public void editor() {
//        String str = """
//            language	string	javascript	编辑器高亮的语言
//            size	string	md	编辑器高度，取值可以是 md、lg、xl、xxl
//            allowFullscreen	boolean	false	是否显示全屏模式开关
//            options	object		monaco 编辑器的其它配置，比如是否显示行号等，请参考这里，不过无法设置 readOnly，只读模式需要使用 disabled: true
//            placeholder	string		占位描述，没有值的时候展示
//            """;
//        this.parse4列("Editor", str);
//    }
//
//    @Test
//    public void Html() {
//        String str = """
//            html	string		html代码
//            """;
//        this.parse4列("Html", str);
//    }
//
//    @Test
//    public void 富文本() {
//        String str = """
//            saveAsUbb	boolean		是否保存为 ubb 格式
//            receiver	API		默认的图片保存 API
//            videoReceiver	API		默认的视频保存 API 仅支持 froala 编辑器
//            fileField	string		上传文件时的字段名
//            size	string		框的大小，可设置为 md 或者 lg
//            options	object		需要参考 tinymce 或 froala 的文档
//            buttons	Array<string>		froala 专用，配置显示的按钮，tinymce 可以通过前面的 options 设置 toolbar 字符串
//            """;
//        this.parse4列("InputRichText", str);
//    }
//
//    @Test
//    public void Color() {
//        String str = """
//            type	string		如果在 Table、Card 和 List 中，为"color"；在 Form 中用作静态展示，为"static-color"
//            className	string		外层 CSS 类名
//            value	string		显示的颜色值
//            name	string		在其他组件中，时，用作变量映射
//            defaultColor	string	#ccc	默认颜色值
//            showValue	boolean	true	是否显示右边的颜色值
//            """;
//        this.parse4列("Color", str);
//    }
//
//    @Test
//    public void InputColor() {
//        String str = """
//            format	string	hex	请选择 hex、hls、rgb或者rgba。
//            presetColors	Array<string>	选择器预设颜色值	选择器底部的默认颜色，数组内为空则不显示默认颜色
//            allowCustomColor	boolean	true	为false时只能选择颜色，使用 presetColors 设定颜色选择范围
//            clearable	boolean	"label"	是否显示清除按钮
//            resetValue	string	""	清除后，表单项值调整成该值
//            """;
//        this.parse4列("InputColor", str);
//    }
//
//    @Test
//    public void divider() {
//        String str = """
//            className	string		外层 Dom 的类名
//            lineStyle	string	solid	分割线的样式，支持dashed和solid
//            direction	string	horizontal	分割线的方向，支持horizontal和vertical	3.5.0
//            color	string		分割线的颜色	3.5.0
//            rotate	number		分割线的旋转角度	3.5.0
//            title	SchemaNode		分割线的标题	3.5.0
//            titleClassName	string		分割线的标题类名	3.5.0
//            titlePosition	string	center	分割线的标题位置，支持left、center和right	3.5.0
//            """;
//        this.parse4列("Divider", str);
//    }
//
//    @Test
//    public void link() {
//        String str = """
//            type	string		如果在 Table、Card 和 List 中，为"link"；在 Form 中用作静态展示，为"static-link"
//            body	string		标签内文本
//            href	string		链接地址
//            blank	boolean		是否在新标签页打开
//            htmlTarget	string		a 标签的 target，优先于 blank 属性
//            title	string		a 标签的 title
//            disabled	boolean		禁用超链接
//            icon	string		超链接图标，以加强显示
//            rightIcon	string		右侧图标
//            """;
//        this.parse4列("Link", str);
//    }
//
//    @Test
//    public void log() {
//        String str = """
//            height	number	500	展示区域高度
//            className	string		外层 CSS 类名
//            autoScroll	boolean	true	是否自动滚动
//            disableColor	boolean	false	是否禁用 ansi 颜色支持
//            placeholder	string		加载中的文字
//            encoding	string	utf-8	返回内容的字符编码
//            source	string		接口
//            credentials	string	include	fetch 的 credentials 设置
//            rowHeight	number		设置每行高度，将会开启虚拟渲染
//            maxLength	number		最大显示行数
//            operation	Array		可选日志操作：['stop','restart',clear','showLineNumber','filter']
//            """;
//        this.parse4列("Log", str);
//    }
//
//    @Test
//    public void progress() {
//        String str = """
//            mode	string	line	进度「条」的类型,可选line,circle,dashboard
//            strokeWidth	number	line类型为10，circle、dashboard类型为6	进度条线宽度
//            placeholder	string  占位文本
//            showLabel	boolean	true	是否展示进度文本
//            gapDegree	number	75仪表盘缺角角度，可取值0~295
//            stripe	boolean	false	背景是否显示条纹
//            animate	boolean	false	type为line可支持动画
//            showThresholdText	boolean	false	是否显示阈值（刻度）数值
//            gapPosition	string	bottom	仪表盘进度条缺口位置，可选top bottom left right
//            """;
//        this.parse4列("Progress", str);
//    }
//
//    @Test
//    public void InputRange() {
//        String str =
//            """
//                className	string		css 类名
//                min	number | string	0	最小值，支持变量	3.3.0后支持变量
//                max	number | string	100	最大， 支持变量值	3.3.0后支持变量
//                disabled	boolean	false	是否禁用
//                step	number | string	1	步长，支持变量	3.3.0后支持变量
//                showSteps	boolean	false	是否显示步长
//                parts	number or number[]	1	分割的块数
//                tooltipVisible	boolean	false	是否显示滑块标签
//                tooltipPlacement	auto or bottom or left or right	top	滑块标签的位置，默认auto，方向自适应 前置条件：tooltipVisible 不为 false 时有效
//                multiple	boolean	false	支持选择范围
//                joinValues	boolean	true	默认为 true，选择的 value 会通过 delimiter 连接起来，否则直接将以{min: 1, max: 100}的形式提交 前置条件：开启multiple时有效
//                clearable	boolean	false	是否可清除 前置条件：开启showInput时有效
//                showInput	boolean	false	是否显示输入框
//                """;
//        this.parse4列("InputRange", str);
//    }
//
//    @Test
//    public void JsonSchema() {
//        String str = """
//            schema	object | string		指定 json-schema
//            """;
//        this.parse4列("JsonSchema", str);
//    }
//
//    @Test
//    public void TransferPicker() {
//        String str =
//            """
//                source	string或 API		动态选项组
//                searchApi	API		如果想通过接口检索，可以设置这个 api。
//                resultListModeFollowSelect	boolean	false	结果面板跟随模式，目前只支持list、table、tree（tree 目前只支持非延时加载的tree）
//                statistics	boolean	true	是否显示统计数据
//                selectTitle	string	"请选择"	左侧的标题文字
//                resultTitle	string	"当前选择"	右侧结果的标题文字
//                sortable	boolean	false	结果可以进行拖拽排序（结果列表为树时，不支持排序）
//                selectMode	string	list	可选：list、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个 tree）。
//                searchResultMode	string		如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。
//                searchable	boolean	false	左侧列表搜索功能，当设置为  true  时表示可以通过输入部分内容检索出选项项。
//                searchPlaceholder	string		左侧列表搜索框提示
//                columns	Array<Object>		当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。
//                leftOptions	Array<Object>		当展示形式为 associated 时用来配置左边的选项集。
//                leftMode	string		当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。
//                rightMode	string		当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。
//                resultSearchable	boolean	false	结果（右则）列表的检索功能，当设置为 true 时，可以通过输入检索模糊匹配检索内容（目前树的延时加载不支持结果搜索功能）
//                resultSearchPlaceholder	string		右侧列表搜索框提示
//                menuTpl	string | SchemaNode		用来自定义选项展示
//                valueTpl	string | SchemaNode		用来自定义值的展示
//                itemHeight	number	32	每个选项的高度，用于虚拟渲染
//                virtualThreshold	number	100	在选项数量超过多少时开启虚拟渲染
//                borderMode	'full' | 'half' | 'none'		边框模式，'full'为全边框，'half'为半边框，'none'为没边框
//                pickerSize	string		弹窗大小，支持: xs、sm、md、lg、xl、full
//                """;
//        this.parse4列("TransferPicker", str);
//    }
//
//    @Test
//    public void InputRating() {
//        String str =
//            """
//                half	boolean	false	是否使用半星选择
//                count	number	5	总星数
//                readOnly	boolean	false	只读
//                allowClear	boolean	true	是否允许再次点击后清除
//                colors	string / object	{'2': '#abadb1', '3': '#787b81', '5': '#ffa900' }	星星被选中的颜色。 若传入字符串，则只有一种颜色。若传入对象，可自定义分段，键名为分段的界限值，键值为对应的类名
//                inactiveColor	string	#e7e7e8	未被选中的星星的颜色
//                texts	object	-	星星被选中时的提示文字。可自定义分段，键名为分段的界限值，键值为对应的类名
//                textPosition	right / left	right	文字的位置
//                char	string	★	自定义字符
//                className	string	-	自定义样式类名
//                charClassName	string	-	自定义字符类名
//                textClassName	string	-	自定义文字类名
//                """;
//        this.parse4列("InputRating", str);
//    }
//
//    @Test
//    public void dialog() {
//        String str = """
//            type	string		"dialog" 指定为 Dialog 渲染器
//            title	SchemaNode		弹出层标题
//            body	SchemaNode		往 Dialog 内容区加内容
//            size	string		指定 dialog 大小，支持: xs、sm、md、lg、xl、full
//            bodyClassName	string	modal-body	Dialog body 区域的样式类名
//            closeOnEsc	boolean	false	是否支持按 Esc 关闭 Dialog
//            showCloseButton	boolean	true	是否显示右上角的关闭按钮
//            showErrorMsg	boolean	true	是否在弹框左下角显示报错信息
//            showLoading	boolean	true	是否在弹框左下角显示 loading 动画
//            disabled	boolean	false	如果设置此属性，则该 Dialog 只读没有提交操作。
//            actions	Array<Action>	【确认】和【取消】	如果想不显示底部按钮，可以配置：[]
//            data	object		支持数据映射，如果不设定将默认将触发按钮的上下文中继承数据。
//            """;
//        this.parse4列("Dialog", str);
//    }
//
//    @Test
//    public void button() {
//        String str =
//            """
//                className	string		指定添加 button 类名
//                url	string		点击跳转的地址，指定此属性 button 的行为和 a 链接一致
//                size	'xs' | 'sm' | 'md' | 'lg' 		设置按钮大小
//                actionType	'button' | 'reset' | 'submit'| 'clear'| 'url'	button	设置按钮类型
//                level	'link' | 'primary' | 'enhance' | 'secondary' | 'info'|'success' | 'warning' | 'danger' | 'light'| 'dark' | 'default'	default	设置按钮样式
//                tooltip	'string' | 'TooltipObject'		气泡提示内容
//                tooltipPlacement	'top' | 'right' | 'bottom' | 'left' 	top	气泡框位置器
//                tooltipTrigger	'hover' | 'focus'		触发 tootip
//                disabled	'boolean'	false	按钮失效状态
//                disabledTip	'string' | 'TooltipObject'		按钮失效状态下的提示
//                block	'boolean'	false	将按钮宽度调整为其父宽度的选项
//                loading	'boolean'	false	显示按钮 loading 效果
//                loadingOn	'string'		显示按钮 loading 表达式
//                """;
//        this.parse4列("Button", str);
//    }
//
//    @Test
//    public void buttonGroup() {
//        String str =
//            """
//                type	string	"button-group"	指定为 button-group 渲染器
//                vertical	boolean	false	是否使用垂直模式
//                tiled	boolean	false	是否使用平铺模式
//                btnLevel	'link' | 'primary' | 'secondary' | 'info'|'success' | 'warning' | 'danger' | 'light'| 'dark' | 'default'	"default"	按钮样式
//                btnActiveLevel	'link' | 'primary' | 'secondary' | 'info'|'success' | 'warning' | 'danger' | 'light'| 'dark' | 'default'	"default"	选中按钮样式
//                buttons	Array<Action>		按钮
//                className	string		外层 Dom 的类名
//                """;
//        this.parse4列("ButtonGroup", str);
//    }
//
//    @Test
//    public void Picker() {
//        String str = """
//            source	string或 API 或 数据映射		动态选项组
//            multiple	boolean		是否为多选。
//            delimiter	boolean	false	拼接符
//            labelField	boolean	label	选项标签字段
//            valueField	boolean	value	选项值字段
//            joinValues	boolean	true	拼接值
//            extractValue	boolean	false	提取值
//            autoFill	object		自动填充
//            modalTitle	string	请选择	设置模态框的标题
//            modalMode	string	dialog	设置 dialog 或者 drawer，用来配置弹出方式。
//            embed	boolean	false	是否使用内嵌模式
//            overflowConfig	OverflowConfig	参考OverflowConfig	开启最大标签展示数量的相关配置
//            """;
//        this.parse4列("Picker", str);
//    }
//
//    @Test
//    public void Textarea() {
//        String str = """
//            minRows	number	3	最小行数
//            maxRows	number	20	最大行数
//            trimContents	boolean	true	是否去除首尾空白文本
//            readOnly	boolean	false	是否只读
//            showCounter	boolean	false	是否显示计数器
//            maxLength	number	-	限制最大字数
//            clearable	boolean	false	是否可清除
//            resetValue	string	""	清除后设置此配置项给定的值。
//            """;
//        this.parse4列("Textarea", str);
//    }
//
//    @Test
//    public void Tag() {
//        String str =
//            """
//                displayMode	'normal' | 'rounded' | 'status'	normal	展现模式
//                color	'active' | 'inactive' | 'error' | 'success' | 'processing' | 'warning' | 具体色值		颜色主题，提供默认主题，并支持自定义颜色值
//                label	string	-	标签内容
//                icon	SchemaIcon	dot 图标	status 模式下的前置图标
//                className	string		自定义 CSS 样式类名
//                style	object	{}	自定义样式（行内样式），优先级最高
//                closable	boolean	false	是否展示关闭按钮
//                """;
//        this.parse4列("Tag", str);
//    }
//
//    @Test
//    public void InputImage() {
//        String str =
//            """
//                receiver	API		上传文件接口
//                accept	string	.jpeg,.jpg,.png,.gif	支持的图片类型格式，请配置此属性为图片后缀，例如.jpg,.png
//                capture	string	undefined	用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源
//                maxSize	number		默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B
//                maxLength	number		默认没有限制，当设置后，一次只允许上传指定数量文件。
//                multiple	boolean	false	是否多选。
//                joinValues	boolean	true	拼接值
//                extractValue	boolean	false	提取值
//                delimiter	string	,	拼接符
//                autoUpload	boolean	true	否选择完就自动开始上传
//                hideUploadButton	boolean	false	隐藏上传按钮
//                fileField	string	file	如果你不想自己存储，则可以忽略此属性。
//                crop	boolean或{"aspectRatio":""}		用来设置是否支持裁剪。
//                crop.aspectRatio	number		裁剪比例。浮点型，默认 1 即 1:1，如果要设置 16:9 请设置 1.7777777777777777 即 16 / 9。。
//                crop.rotatable	boolean	false	裁剪时是否可旋转
//                crop.scalable	boolean	false	裁剪时是否可缩放
//                crop.viewMode	number	1	裁剪时的查看模式，0 是无限制
//                cropFormat	string	image/png	裁剪文件格式
//                cropQuality	number	1	裁剪文件格式的质量，用于 jpeg/webp，取值在 0 和 1 之间
//                limit	Limit		限制图片大小，超出不让上传。
//                frameImage	string		默认占位图地址
//                fixedSize	boolean		是否开启固定尺寸,若开启，需同时设置 fixedSizeClassName
//                fixedSizeClassName	string		开启固定尺寸时，根据此值控制展示尺寸。例如h-30,即图片框高为 h-30,AMIS 将自动缩放比率设置默认图所占位置的宽度，最终上传图片根据此尺寸对应缩放。
//                initAutoFill	boolean	false	表单反显时是否执行 autoFill
//                uploadBtnText	string | SchemaNode		上传按钮文案。支持 tpl、schema 形式配置。
//                dropCrop	boolean	true	图片上传后是否进入裁剪模式
//                initCrop	boolean	false	图片选择器初始化后是否立即进入裁剪模式
//                draggable	boolean	false	开启后支持拖拽排序改变图片值顺序
//                draggableTip	string	'拖拽排序'	拖拽提示文案
//                """;
//        this.parse4列("InputImage", str);
//    }
//
//    @Test
//    public void inputMonthRange() {
//        String str = """
//            format	string	X	日期选择器值格式
//            inputFormat	string	YYYY-DD	日期选择器显示格式
//            placeholder	string	"请选择月份范围"	占位文本
//            minDate	string		限制最小日期，用法同 限制范围
//            maxDate	string		限制最大日期，用法同 限制范围
//            minDuration	string		限制最小跨度，如： 2days
//            maxDuration	string		限制最大跨度，如：1year
//            utc	boolean	false	保存 UTC 值
//            clearable	boolean	true	是否可清除
//            embed	boolean	false	是否内联模式
//            animation	boolean	true	是否启用游标动画	2.2.0
//            extraName	string		是否存成两个字段	3.3.0
//            """;
//        this.parse4列("InputMonthRange", str);
//    }
//
//    @Test
//    public void inputDateRange() {
//        String str =
//            """
//                valueFormat	string	X	日期选择器值格式	3.4.0 版本后支持
//                displayFormat	string	YYYY-MM-DD	日期选择器显示格式	3.4.0 版本后支持
//                placeholder	string	"请选择日期范围"	占位文本
//                shortcuts	string | string[] | Array<{label: string; startDate: string; endDate: string}>	"yesterday,7daysago,prevweek,thismonth,prevmonth,prevquarter"	日期范围快捷键	3.1.0版本后支持表达式
//                minDate	string		限制最小日期，用法同 限制范围
//                maxDate	string		限制最大日期，用法同 限制范围
//                minDuration	string		限制最小跨度，如： 2days
//                maxDuration	string		限制最大跨度，如：1year
//                utc	boolean	false	保存 UTC 值
//                clearable	boolean	true	是否可清除
//                embed	boolean	false	是否内联模式
//                animation	boolean	true	是否启用游标动画	2.2.0
//                extraName	string		是否存成两个字段	3.3.0
//                """;
//        this.parse3列("InputDateRange", str);
//    }
//
//    @Test
//    public void InputMonth() {
//        String str = """
//            value	string		默认值
//            valueFormat	string	X	月份选择器值格式，更多格式类型请参考 moment	3.4.0
//            displayFormat	string	YYYY-MM	月份选择器显示格式，即时间戳格式，更多格式类型请参考 moment	3.4.0
//            placeholder	string	"请选择月份"	占位文本
//            clearable	boolean	true	是否可清除
//            """;
//        this.parse5列反过来("InputMonth", str);
//    }
//
//    @Test
//    public void Checkboxes() {
//        String str = """
//            options	Array<object>或Array<string>		选项组
//            source	string或 API		动态选项组
//            delimiter	string	,	拼接符
//            labelField	string	"label"	选项标签字段
//            valueField	string	"value"	选项值字段
//            joinValues	boolean	true	拼接值
//            extractValue	boolean	false	提取值
//            columnsCount	number	1	选项按几列显示，默认为一列
//            menuTpl	string		支持自定义选项渲染
//            checkAll	boolean	false	是否支持全选
//            inline	boolean	true	是否显示为一行
//            defaultCheckAll	boolean	false	默认是否全选
//            creatable	boolean	false	新增选项
//            createBtnLabel	string	"新增选项"	新增选项
//            addControls	Array<表单项>		自定义新增表单项
//            addApi	API		配置新增选项接口
//            editable	boolean	false	编辑选项
//            editControls	Array<表单项>		自定义编辑表单项
//            editApi	API		配置编辑选项接口
//            removable	boolean	false	删除选项
//            deleteApi	API		配置删除选项接口
//            optionType	default | button	default	按钮模式
//            itemClassName	string		选项样式类名
//            labelClassName	string		选项标签样式类名
//            """;
//        this.parse4列("Checkboxes", str);
//    }
//
//    @Test
//    public void Date() {
//        String str =
//            """
//                type	string		如果在 Table、Card 和 List 中，为"date"；在 Form 中用作静态展示，为"static-date"
//                className	string		外层 CSS 类名
//                value	string		显示的日期数值
//                name	string		在其他组件中，时，用作变量映射
//                placeholder	string	-	占位内容
//                displayFormat	string	YYYY-MM-DD	展示格式, 更多格式类型请参考 文档	版本号 3.4.0 及以上支持
//                valueFormat	string	X	数据格式，默认为时间戳。更多格式类型请参考 文档
//                fromNow	boolean	false	是否显示相对当前的时间描述，比如: 11 小时前、3 天前、1 年前等，fromNow 为 true 时，format 不生效。
//                updateFrequency	number	60000	更新频率， 默认为 1 分钟
//                displayTimeZone	string		设置日期展示时区，可设置清单参考：https://gist.github.com/diogocapela/12c6617fc87607d11fd62d2a4f42b02a
//                """;
//        this.parse5列反过来("Date", str);
//    }
//
//    @Test
//    public void ButtonGroupSelect() {
//        String str =
//            """
//                type	string	"button-group-select"	指定为 button-group-select 渲染器
//                vertical	boolean	false	是否使用垂直模式
//                tiled	boolean	false	是否使用平铺模式
//                btnLevel	'link' | 'primary' | 'secondary' | 'info'|'success' | 'warning' | 'danger' | 'light'| 'dark' | 'default'	"default"	按钮样式
//                btnActiveLevel	'link' | 'primary' | 'secondary' | 'info'|'success' | 'warning' | 'danger' | 'light'| 'dark' | 'default'	"default"	选中按钮样式
//                options	Array<object>或Array<string>		选项组
//                option.badge	object		角标	2.8.1
//                source	string或 API		动态选项组
//                multiple	boolean	false	多选
//                labelField	boolean	"label"	选项标签字段
//                valueField	boolean	"value"	选项值字段
//                joinValues	boolean	true	拼接值
//                extractValue	boolean	false	提取值
//                autoFill	object		自动填充
//                """;
//        this.parse5列反过来("ButtonGroupSelect", str);
//    }
//
//    @Test
//    public void Switch() {
//        String str = """
//            option	string		选项说明
//            onText	string / IconSchema		开启时开关显示的内容
//            offText	string / IconSchema		关闭时开关显示的内容
//            trueValue	boolean / string / number	true	标识真值
//            falseValue	boolean / string / number	false	标识假值
//            size	"sm" | "md"	"md"	开关大小
//            """;
//        this.parse4列("Switch", str);
//    }
//
//    @Test
//    public void Select() {
//        String str =
//            """
//                options	Array<object>或Array<string>		选项组
//                source	API 或 数据映射		动态选项组
//                autoComplete	API		自动提示补全
//                delimiter	string	false	拼接符
//                labelField	string	"label"	选项标签字段
//                valueField	string	"value"	选项值字段
//                joinValues	boolean	true	拼接值
//                extractValue	boolean	false	提取值
//                checkAll	boolean	false	是否支持全选
//                checkAllLabel	string	全选	全选的文字
//                checkAllBySearch	boolean	true	有检索时只全选检索命中的项
//                defaultCheckAll	boolean	false	默认是否全选
//                creatable	boolean	false	新增选项
//                multiple	boolean	false	多选
//                searchable	boolean	false	检索
//                createBtnLabel	string	"新增选项"	新增选项
//                addControls	Array<表单项>		自定义新增表单项
//                addApi	API		配置新增选项接口
//                editable	boolean	false	编辑选项
//                editControls	Array<表单项>		自定义编辑表单项
//                editApi	API		配置编辑选项接口
//                removable	boolean	false	删除选项
//                deleteApi	API		配置删除选项接口
//                autoFill	object		自动填充
//                menuTpl	string		支持配置自定义菜单
//                clearable	boolean		单选模式下是否支持清空
//                hideSelected	boolean	false	隐藏已选选项
//                mobileClassName	string		移动端浮层类名
//                selectMode	string	``	可选：group、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个 tree）。
//                searchResultMode	string		如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。
//                columns	Array<Object>		当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。
//                leftOptions	Array<Object>		当展示形式为 associated 时用来配置左边的选项集。
//                leftMode	string		当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。
//                rightMode	string		当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。
//                maxTagCount	number		标签的最大展示数量，超出数量后以收纳浮层的方式展示，仅在多选模式开启后生效
//                overflowTagPopover	TooltipObject	{"placement": "top", "trigger": "hover", "showArrow": false, "offset": [0, -10]}	收纳浮层的配置属性，详细配置参考Tooltip
//                optionClassName	string		选项 CSS 类名
//                popOverContainerSelector	string		弹层挂载位置选择器，会通过querySelector获取
//                clearable	boolean		是否展示清空图标
//                overlay	{ width: string | number, align: "left" | "center" | "right" }		弹层宽度与对齐方式 2.8.0 以上版本
//                showInvalidMatch	boolean	false	选项值与选项组不匹配时选项值是否飘红
//                """;
//        this.parse4列("Select", str);
//    }
//
//    @Test
//    public void Radios() {
//        String str = """
//            options	Array<object>或Array<string>		选项组
//            source	string或 API		动态选项组
//            labelField	string	"label"	选项标签字段
//            valueField	string	"value"	选项值字段
//            columnsCount	number	1	选项按几列显示，默认为一列
//            inline	boolean	true	是否显示为一行
//            selectFirst	boolean	false	是否默认选中第一个
//            autoFill	object		自动填充
//            """;
//        this.parse4列("Radios", str);
//    }
//
//    @Test
//    public void InputNumber() {
//        String str = """
//            min	模板		最小值
//            max	模板		最大值
//            step	number		步长
//            precision	number		精度，即小数点后几位，支持 0 和正整数
//            showSteps	boolean	true	是否显示上下点击按钮
//            readOnly	boolean	false	只读
//            prefix	string		前缀
//            suffix	string		后缀
//            unitOptions	string[]		单位选项	1.4.0
//            kilobitSeparator	boolean	false	千分分隔
//            keyboard	boolean	true	键盘事件（方向上下）
//            big	boolean	false	是否使用大数	2.3.0
//            displayMode	"base" | "enhance"	"base"	样式类型
//            borderMode	"full" | "half" | "none"	"full"	边框模式，全边框，还是半边框，或者没边框
//            resetValue	number | string	""	清空输入内容时，组件值将设置为 resetValue
//            clearValueOnEmpty	boolean	false	内容为空时从数据域中删除该表单项对应的值	2.8.0
//            """;
//        this.parse4列("InputNumber", str);
//    }
//
//    @Test
//    public void InputDateTime() {
//        String str = """
//            value	string		默认值
//            valueFormat	string	X	日期时间选择器值格式，更多格式类型请参考 文档	3.4.0 版本后支持
//            displayFormat	string	YYYY-MM-DD HH:mm:ss	日期时间选择器显示格式，即时间戳格式，更多格式类型请参考 文档	3.4.0 版本后支持
//            placeholder	string	"请选择日期以及时间"	占位文本
//            shortcuts	string | string[] | Array<{"label": string; date: string}>		日期时间快捷键	3.1.0版本后支持表达式
//            minDate	string		限制最小日期时间
//            maxDate	string		限制最大日期时间
//            utc	boolean	false	保存 utc 值
//            clearable	boolean	true	是否可清除
//            embed	boolean	false	是否内联
//            timeConstraints	object	true	请参考 input-time 里的说明
//            isEndDate	boolean	false	如果配置为 true，会自动默认为 23:59:59 秒
//            disabledDate	string		用字符函数来控制哪些天不可以被点选
//            """;
//        this.parse4列("InputDateTime", str);
//    }
//
//    @Test
//    public void InputDate() {
//        String str =
//            """
//                value	string		默认值
//                valueFormat	string	X	日期选择器值格式，更多格式类型请参考 文档	3.4.0 版本后支持
//                displayFormat	string	YYYY-MM-DD	日期选择器显示格式，即时间戳格式，更多格式类型请参考 文档	3.4.0 版本后支持
//                closeOnSelect	boolean	false	点选日期后，是否马上关闭选择框
//                placeholder	string	"请选择日期"	占位文本
//                shortcuts	string | string[] | Array<{"label": string; date: string}>		日期快捷键，字符串格式为预设值，对象格式支持写表达式	3.1.0版本后支持表达式
//                minDate	string		限制最小日期
//                maxDate	string		限制最大日期
//                utc	boolean	false	保存 utc 值
//                clearable	boolean	true	是否可清除
//                embed	boolean	false	是否内联模式
//                disabledDate	string		用字符函数来控制哪些天不可以被点选
//                """;
//        this.parse4列("InputDate", str);
//    }
//
//    @Test
//    public void inputText() {
//        String str = """
//                options	Array<object>或Array<string>		选项组
//                source	string或 API		动态选项组
//                autoComplete	string或 API		自动补全
//                multiple	boolean		是否多选
//                delimiter	string	,	拼接符
//                labelField	string	"label"	选项标签字段
//                valueField	string	"value"	选项值字段
//                joinValues	boolean	true	拼接值
//                extractValue	boolean	false	提取值
//                trimContents	boolean		是否去除首尾空白文本。
//                clearValueOnEmpty	boolean		文本内容为空时去掉这个值
//                creatable	boolean		是否可以创建，默认为可以，除非设置为 false 即只能选择选项中的值
//                clearable	boolean		是否可清除
//                resetValue	string	""	清除后设置此配置项给定的值。
//                prefix	string	""	前缀
//                suffix	string	""	后缀
//                showCounter	boolean		是否显示计数器
//                minLength	number		限制最小字数
//                maxLength	number		限制最大字数
//                transform	object		自动转换值，可选 transform: { lowerCase: true, upperCase: true }
//                borderMode	"full"| "half" | "none"	"full"	输入框边框模式，全边框，还是半边框，或者没边框。
//                inputControlClassName	string		control 节点的 CSS 类名
//                nativeInputClassName	string		原生 input 标签的 CSS 类名
//                nativeAutoComplete	string	off	原生 input 标签的 autoComplete 属性，比如配置集成 new-password
//            """;
//        this.parse4列("InputText", str);
//    }
//}