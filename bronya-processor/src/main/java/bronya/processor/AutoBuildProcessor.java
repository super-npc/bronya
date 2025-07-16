package bronya.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 调试:
 * 1. 使用idea 建立远程调试 监听端口8000, 并打断点
 * 2. 在命令行运行 mvnDebug clean package
 *
 * 代码不更新: mvn idea:module
 */
@AutoService(Processor.class)
//@SupportedAnnotationTypes("bronya.processor.MyAnnotation")
//@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class AutoBuildProcessor extends AbstractProcessor {
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.out.println("aaaa哈哈哈111 = " + processingEnv);
        super.init(processingEnv); // 确保调用父类的 init 方法
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("写入完成.开始444 = " + annotations);
//        try {
//            // 检查生成的文件是否已经存在
//            String packageName = "com.hahha";
//            String className = "GeneratedExample";
//            String filePath = packageName.replace('.', '/') + "/" + className + ".java";
//            System.out.println("文件路径 = " + filePath);
//            if (processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, packageName, className + ".class") != null) {
//                System.out.println("文件已存在，跳过生成: " + filePath);
//                return false; // 如果文件已存在，跳过生成
//            }
//
//            // 定义字段
//            FieldSpec nameField = FieldSpec.builder(String.class, "name", Modifier.PRIVATE).build();
//
//            FieldSpec ageField = FieldSpec.builder(TypeName.INT, "age", Modifier.PRIVATE).build();
//
//            FieldSpec versionField = FieldSpec.builder(String.class, "VERSION", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$S", "1.0.0").build();
//
//            ParameterizedTypeName listOfStrings = ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(String.class));
//
//            FieldSpec itemsField = FieldSpec.builder(listOfStrings, "items", Modifier.PRIVATE).initializer("new $T<>()", ArrayList.class).build();
//
//            // 构造方法
//            MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addStatement("this.name = $S", "默认名称").addStatement("this.age = 0").build();
//
//            // Getter和Setter
//            MethodSpec getName = MethodSpec.methodBuilder("getName").addModifiers(Modifier.PUBLIC).returns(String.class).addStatement("return name").build();
//
//            MethodSpec setName = MethodSpec.methodBuilder("setName").addModifiers(Modifier.PUBLIC).addParameter(String.class, "name").addStatement("this.name = name").build();
//
//            // 业务方法
//            MethodSpec addItem = MethodSpec.methodBuilder("addItem").addModifiers(Modifier.PUBLIC).addParameter(String.class, "item").addStatement("items.add(item)").build();
//            MethodSpec printInfo = MethodSpec.methodBuilder("printInfo").addModifiers(Modifier.PUBLIC).addStatement("$T.out.println($S + name)", System.class, "名称: ").addStatement("$T.out.println($S + age)", System.class, "年龄: ").addStatement("$T.out.println($S + items)", System.class, "项目列表: ").build();
//            // 静态方法
//            MethodSpec getVersion = MethodSpec.methodBuilder("getVersion").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(String.class).addStatement("return VERSION").build();
//            // 生成类
//            TypeSpec generatedClass = TypeSpec.classBuilder(className)
//                .addModifiers(Modifier.PUBLIC)
//                .addField(nameField)
//                .addField(ageField)
//                .addField(versionField)
//                .addField(itemsField)
//                .addMethod(constructor)
//                .addMethod(getName)
//                .addMethod(setName)
//                .addMethod(addItem)
//                .addMethod(printInfo)
//                .addMethod(getVersion)
//                .build();
//            // 生成Java文件
//            JavaFile javaFile = JavaFile.builder(packageName, generatedClass)
//                .addFileComment("这是自动生成的代码")
//                .build();
//
//            // 写入文件
//            javaFile.writeTo(processingEnv.getFiler());
//            System.out.println("写入完成1 = " + javaFile);
//        } catch (IOException e) {
//            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "生成错误: " + e);
//        }
        System.out.println("写入完成2 = ");
        return true; // 返回 true 表示已经处理了所有注解
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}