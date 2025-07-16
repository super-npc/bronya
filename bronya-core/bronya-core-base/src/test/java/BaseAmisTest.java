//
//
//import java.util.List;
//import java.util.Optional;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.dromara.hutool.core.text.StrUtil;
//import org.dromara.hutool.core.text.split.SplitUtil;
//
//public class BaseAmisTest {
//
//    public void parse5列反过来(String components, String 属性表) {
//        List<AmisFile.AmisField> list = 属性表.lines().map(line -> {
//            List<String> split = SplitUtil.split(line, "	");
//            String name = split.get(0).trim();
//            String type = this.parseType(split.get(1)).trim();
//            String _default = split.get(2).trim();
//            String desc = split.get(3).trim();// 5列的时候有一个版本
//            // String desc = split.get(4).trim();
//            AmisFile.AmisField javaField = new AmisFile.AmisField();
//            javaField.setName(name);
//            javaField.setType(type);
//            javaField.set_default(_default);
//            javaField.setDesc(desc);
//            return javaField;
//        }).toList();
//        AmisFile javaFile = new AmisFile(components, list);
//        String render = AmisComponents.render(javaFile);
//        System.out.println(render);
//    }
//
//    public void parse5列(String components, String 属性表) {
//        List<AmisFile.AmisField> list = 属性表.lines().map(line -> {
//            List<String> split = SplitUtil.split(line, "	");
//            String name = split.get(0).trim();
//            String type = this.parseType(split.get(1)).trim();
//            String _default = split.get(2).trim();
//            String version = split.get(3).trim();// 5列的时候有一个版本
//            String desc = split.get(4).trim();
//            AmisFile.AmisField javaField = new AmisFile.AmisField();
//            javaField.setName(name);
//            javaField.setType(type);
//            javaField.set_default(_default);
//            javaField.setDesc(desc);
//            return javaField;
//        }).toList();
//        AmisFile javaFile = new AmisFile(components, list);
//        String render = AmisComponents.render(javaFile);
//        System.out.println(render);
//    }
//
//    public void parse4列(String components, String 属性表) {
//        List<AmisFile.AmisField> list = 属性表.lines().map(line -> {
//            List<String> split = SplitUtil.split(line, "	");
//            String name = split.get(0).trim();
//            String type = this.parseType(split.get(1)).trim();
//            String _default = Optional.ofNullable(CollUtil.get(split, 2)).orElse("").trim();
//            String desc = Optional.ofNullable(CollUtil.get(split, 3)).orElse("").trim();
//            AmisFile.AmisField javaField = new AmisFile.AmisField();
//            javaField.setName(name);
//            javaField.setType(type);
//            javaField.set_default(_default);
//            javaField.setDesc(desc);
//            return javaField;
//        }).toList();
//        AmisFile javaFile = new AmisFile(components, list);
//        String render = AmisComponents.render(javaFile);
//        System.out.println(render);
//    }
//
//    public void parse3列(String components, String 属性表) {
//        List<AmisFile.AmisField> list = 属性表.lines().map(line -> {
//            List<String> split = SplitUtil.split(line, "	");
//            String name = split.get(0).trim();
//            String type = this.parseType(split.get(1)).trim();
//            String _default = Optional.ofNullable(split.get(2)).orElse("").trim();
//            AmisFile.AmisField javaField = new AmisFile.AmisField();
//            javaField.setName(name);
//            javaField.setType(type);
//            javaField.set_default(_default);
//            return javaField;
//        }).toList();
//        AmisFile javaFile = new AmisFile(components, list);
//        String render = AmisComponents.render(javaFile);
//        System.out.println(render);
//    }
//
//    private String parseType(String type) {
//        return switch (type) {
//            case "string" -> "String";
//            case "boolean" -> "Boolean";
//            case "number" -> "Integer";
//            case "array" -> "List";
//            default -> "Object";
//        };
//    }
//
//    @Data
//    @AllArgsConstructor
//    public static class AmisFile {
//        private String javaFileName;
//        private List<AmisField> fields;
//
//        @Data
//        public static class AmisField {
//            private String name;
//            private String type;
//            private String _default;
//            private String desc;
//
//            public String getTypeAnnotation() {
//                if (type.equals("Boolean")) {
//                    return "boolean";
//                } else if (type.equals("Integer")) {
//                    return "int";
//                }
//                return type;
//            }
//
//            public String getDefaultAnnotation() {
//                if (StrUtil.isBlank(_default)) {
//                    if (type.equals("Boolean")) {
//                        return "false";
//                    } else if (type.equals("Integer")) {
//                        return "0";
//                    } else if (type.equals("String")) {
//                        return "\"\"";
//                    } else if (type.equals("Object")) {
//                        return "\"\"";
//                    }
//                }
//                return "";
//            }
//
//            public String getNameLowerFirst() {
//                return StrUtil.lowerFirst(this.name);
//            }
//
//            public String getNameUpperFirst() {
//                return StrUtil.upperFirst(this.name);
//            }
//        }
//    }
//}