package bronya.shared.module.common.type;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.dromara.hutool.core.util.RandomUtil;

@Getter
@AllArgsConstructor
public enum Color {
    // 浅粉色("lightPink", "#FFB6C1", "255,182,193"),
    // 粉红("pink", "#FFC0CB", "255,192,203"),
    猩红("crimson", "#DC143C", "220,20,60"),
    // 脸红的淡紫色("lavenderBlush", "#FFF0F5", "255,240,245"),
    // 苍白的紫罗兰红色("paleVioletRed", "#DB7093", "219,112,147"),
    热情的粉红("hotPink", "#FF69B4", "255,105,180"), 深粉色("deepPink", "#FF1493", "255,20,147"),
    适中的紫罗兰红色("mediumVioletRed", "#C71585", "199,21,133"), 兰花的紫色("orchid", "#DA70D6", "218,112,214"),
    // 蓟("thistle", "#D8BFD8", "216,191,216"),
    // 李子("plum", "#DDA0DD", "221,160,221"),
    紫罗兰("violet", "#EE82EE", "238,130,238"), 洋红("magenta", "#FF00FF", "255,0,255"),
    灯笼海棠("fuchsia", "#FF00FF", "255,0,255"), 深洋红色("darkMagenta", "#8B008B", "139,0,139"),
    紫色("purple", "#800080", "128,0,128"), 适中的兰花紫("mediumOrchid", "#BA55D3", "186,85,211"),
    深紫罗兰色("darkVoilet", "#9400D3", "148,0,211"), 深兰花紫("darkOrchid", "#9932CC", "153,50,204"),
    靛青("indigo", "#4B0082", "75,0,130"), 深紫罗兰的蓝色("blueViolet", "#8A2BE2", "138,43,226"),
    适中的紫色("mediumPurple", "#9370DB", "147,112,219"), 适中的板岩暗蓝灰色("mediumSlateBlue", "#7B68EE", "123,104,238"),
    板岩暗蓝灰色("slateBlue", "#6A5ACD", "106,90,205"), 深岩暗蓝灰色("darkSlateBlue", "#483D8B", "72,61,139"),
    // 薰衣草花的淡紫色("lavender", "#E6E6FA", "230,230,250"),
    // 幽灵的白色("ghostWhite", "#F8F8FF", "248,248,255"),
    纯蓝("blue", "#0000FF", "0,0,255"), 适中的蓝色("mediumBlue", "#0000CD", "0,0,205"),
    午夜的蓝色("midnightBlue", "#191970", "25,25,112"), 深蓝色("darkBlue", "#00008B", "0,0,139"),
    海军蓝("navy", "#000080", "0,0,128"), 皇军蓝("royalBlue", "#4169E1", "65,105,225"),
    矢车菊的蓝色("cornflowerBlue", "#6495ED", "100,149,237"),
    // 淡钢蓝("lightSteelBlue", "#B0C4DE", "176,196,222"),
    // 浅石板灰("lightSlateGray", "#778899", "119,136,153"),
    石板灰("slateGray", "#708090", "112,128,144"), 道奇蓝("doderBlue", "#1E90FF", "30,144,255"),
    // 爱丽丝蓝("aliceBlue", "#F0F8FF", "240,248,255"),
    钢蓝("steelBlue", "#4682B4", "70,130,180"), 淡蓝色("lightSkyBlue", "#87CEFA", "135,206,250"),
    天蓝色("skyBlue", "#87CEEB", "135,206,235"), 深天蓝("deepSkyBlue", "#00BFFF", "0,191,255"),
    // 淡蓝("lightBLue", "#ADD8E6", "173,216,230"),
    火药蓝("powDerBlue", "#B0E0E6", "176,224,230"), 军校蓝("cadetBlue", "#5F9EA0", "95,158,160"),
    // 蔚蓝色("azure", "#F0FFFF", "240,255,255"),
    // 淡青色("lightCyan", "#E1FFFF", "225,255,255"),
    // 苍白的绿宝石("paleTurquoise", "#AFEEEE", "175,238,238"),
    青色("cyan", "#00FFFF", "0,255,255"), 水绿色("aqua", "#00FFFF", "0,255,255"),
    深绿宝石("darkTurquoise", "#00CED1", "0,206,209"), 深石板灰("darkSlateGray", "#2F4F4F", "47,79,79"),
    深青色("darkCyan", "#008B8B", "0,139,139"), 水鸭色("teal", "#008080", "0,128,128"),
    适中的绿宝石("mediumTurquoise", "#48D1CC", "72,209,204"),
    // 浅海洋绿("lightSeaGreen", "#20B2AA", "32,178,170"),
    绿宝石("turquoise", "#40E0D0", "64,224,208"), 绿玉碧绿色("auqamarin", "#7FFFAA", "127,255,170"),
    适中的碧绿色("mediumAquamarine", "#00FA9A", "0,250,154"),
    // 适中的春天的绿色("mediumSpringGreen", "#F5FFFA", "245,255,250"),
    // 薄荷奶油("mintCream", "#00FF7F", "0,255,127"),
    春天的绿色("springGreen", "#3CB371", "60,179,113"), 海洋绿("seaGreen", "#2E8B57", "46,139,87"),
    // 蜂蜜("honeydew", "#F0FFF0", "240,255,240"),
    淡绿色("lightGreen", "#90EE90", "144,238,144"),
    // 苍白的绿色("paleGreen", "#98FB98", "152,251,152"),
    深海洋绿("darkSeaGreen", "#8FBC8F", "143,188,143"), 酸橙绿("limeGreen", "#32CD32", "50,205,50"),
    酸橙色("lime", "#00FF00", "0,255,0"), 森林绿("forestGreen", "#228B22", "34,139,34"), 纯绿("green", "#008000", "0,128,0"),
    深绿色("darkGreen", "#006400", "0,100,0"), 查特酒绿("chartreuse", "#7FFF00", "127,255,0"),
    草坪绿("lawnGreen", "#7CFC00", "124,252,0"), 绿黄色("greenYellow", "#ADFF2F", "173,255,47"),
    橄榄土褐色("oliveDrab", "#556B2F", "85,107,47"),
    // 米色("beige", "#6B8E23", "107,142,35"),
    // 浅秋麒麟黄("lightGoldenrodYellow", "#FAFAD2", "250,250,210"),
    // 象牙("ivory", "#FFFFF0", "255,255,240"),
    // 浅黄色("lightYellow", "#FFFFE0", "255,255,224"),
    纯黄("yellow", "#FFFF00", "255,255,0"), 橄榄("olive", "#808000", "128,128,0"),
    深卡其布("darkKhaki", "#BDB76B", "189,183,107"),
    // 柠檬薄纱("lemonChiffon", "#FFFACD", "255,250,205"),
    灰秋麒麟("paleGodenrod", "#EEE8AA", "238,232,170"), 卡其布("khaki", "#F0E68C", "240,230,140"),
    金("gold", "#FFD700", "255,215,0"),
    // 玉米色("cornislk", "#FFF8DC", "255,248,220"),
    秋麒麟("goldEnrod", "#DAA520", "218,165,32"),
    // 花的白色("floralWhite", "#FFFAF0", "255,250,240"),
    // 老饰带("oldLace", "#FDF5E6", "253,245,230"),
    // 小麦色("wheat", "#F5DEB3", "245,222,179"),
    // 鹿皮鞋("moccasin", "#FFE4B5", "255,228,181"),
    橙色("orange", "#FFA500", "255,165,0"),
    // 番木瓜("papayaWhip", "#FFEFD5", "255,239,213"),
    // 漂白的杏仁("blanchedAlmond", "#FFEBCD", "255,235,205"),
    // Navajo白("navajoWhite", "#FFDEAD", "255,222,173"),
    // 古代的白色("antiqueWhite", "#FAEBD7", "250,235,215"),
    晒黑("tan", "#D2B48C", "210,180,140"), 结实的树("brulyWood", "#DEB887", "222,184,135"),
    浓汤("bisque", "#FFE4C4", "255,228,196"), 深橙色("darkOrange", "#FF8C00", "255,140,0"),
    // 亚麻布("linen", "#FAF0E6", "250,240,230"),
    秘鲁("peru", "#CD853F", "205,133,63"), 桃色("peachPuff", "#FFDAB9", "255,218,185"),
    沙棕色("sandyBrown", "#F4A460", "244,164,96"), 巧克力("chocolate", "#D2691E", "210,105,30"),
    马鞍棕色("saddleBrown", "#8B4513", "139,69,19"),
    // 海贝壳("seaShell", "#FFF5EE", "255,245,238"),
    黄土赭色("sienna", "#A0522D", "160,82,45"),
    // 浅鲜肉("lightSalmon", "#FFA07A", "255,160,122"),
    珊瑚("coral", "#FF7F50", "255,127,80"), 橙红色("orangeRed", "#FF4500", "255,69,0"),
    深鲜肉("darkSalmon", "#E9967A", "233,150,122"), 番茄("tomato", "#FF6347", "255,99,71"),
    薄雾玫瑰("mistyRose", "#FFE4E1", "255,228,225"), 鲜肉("salmon", "#FA8072", "250,128,114"),
    // 雪("snow", "#FFFAFA", "255,250,250"),
    淡珊瑚色("lightCoral", "#F08080", "240,128,128"), 玫瑰棕色("rosyBrown", "#BC8F8F", "188,143,143"),
    印度红("indianRed", "#CD5C5C", "205,92,92"), 纯红("red", "#FF0000", "255,0,0"), 棕色("brown", "#A52A2A", "165,42,42"),
    耐火砖("fireBrick", "#B22222", "178,34,34"), 深红色("darkRed", "#8B0000", "139,0,0"), 栗色("maroon", "#800000", "128,0,0"),
    // 纯白("white", "#FFFFFF", "255,255,255"),
    // 白烟("whiteSmoke", "#F5F5F5", "245,245,245"),
    // Gainsboro("gainsboro", "#DCDCDC", "220,220,220"),
    浅灰色("lightGrey", "#D3D3D3", "211,211,211"),
    // 银白色("silver", "#C0C0C0", "192,192,192"),
    深灰色("darkGray", "#A9A9A9", "169,169,169"), 灰色("gray", "#808080", "128,128,128"),
    // 暗淡的灰色("dimGray", "#696969", "105,105,105"),
    纯黑("black", "#000000", "0,0,0");

    private final String cn;
    private final String hex;
    private final String rgb;

    /**
     * 随机拿一个
     */
    public static Color getRandom() {
        return RandomUtil.randomEle(Color.values());
    }

    public String getAmisFormatRgb(){
        return STR."rgb(\{this.getRgb()})";
    }

    public static Color convert(String cn) {
        return Arrays.stream(Color.values()).filter(temp -> temp.name().equals(cn)).findFirst().orElse(null);
    }
}