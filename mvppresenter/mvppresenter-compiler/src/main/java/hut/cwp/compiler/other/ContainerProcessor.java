// package hut.cwp.compiler;
//
// import hut.cwp.annotations.InitAttrConfig;
// import hut.cwp.annotations.InitAttrConfigs;
//
// import com.google.auto.service.AutoService;
// import com.squareup.javapoet.ClassName;
// import com.squareup.javapoet.CodeBlock;
// import com.sun.source.tree.ClassTree;
// import com.sun.source.util.Trees;
// import com.sun.tools.javac.code.Symbol;
// import com.sun.tools.javac.tree.JCTree;
// import com.sun.tools.javac.tree.TreeScanner;
//
// import java.io.IOException;
// import java.io.Writer;
// import java.lang.annotation.Annotation;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.LinkedHashMap;
// import java.util.LinkedHashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
//
// import javax.annotation.processing.AbstractProcessor;
// import javax.annotation.processing.Messager;
// import javax.annotation.processing.ProcessingEnvironment;
// import javax.annotation.processing.Processor;
// import javax.annotation.processing.RoundEnvironment;
// import javax.lang.model.SourceVersion;
// import javax.lang.model.element.AnnotationMirror;
// import javax.lang.model.element.Element;
// import javax.lang.model.element.PackageElement;
// import javax.lang.model.element.TypeElement;
// import javax.lang.model.element.VariableElement;
// import javax.lang.model.type.MirroredTypeException;
// import javax.lang.model.type.TypeMirror;
// import javax.lang.model.util.Elements;
// import javax.lang.model.util.Types;
// import javax.tools.JavaFileObject;
//
// // @AutoService(Processor.class)
// public class ContainerProcessor extends AbstractProcessor {
//
//     private Logger logger;
//     private Messager messager;
//     private Elements mElementUtils;
//     private Map<String, ContainerProxyInfo> mProxyMap = new HashMap<>();
//     private final Map<QualifiedId, Id> symbols = new LinkedHashMap<>();
//
//     private Trees trees;
//     private static final List<String> SUPPORTED_TYPES = Arrays.asList(
//             "array", "attr", "bool", "color", "dimen", "drawable", "id", "integer", "string"
//     );
//     private Types typeUtils;
//
//     @Override
//     public synchronized void init(ProcessingEnvironment processingEnv) {
//         super.init(processingEnv);
//         messager = processingEnv.getMessager();
//         logger = new Logger(messager);
//         mElementUtils = processingEnv.getElementUtils();
//         trees = Trees.instance(processingEnv);
//         logger.info("tree is " + trees);
//         typeUtils = processingEnv.getTypeUtils();
//     }
//
//     @Override
//     public Set<String> getSupportedAnnotationTypes() {
//         HashSet<String> supportTypes = new LinkedHashSet<>();
//         supportTypes.add(InitAttrConfigs.class.getCanonicalName());
//         return supportTypes;
//     }
//
//     private Set<Class<? extends Annotation>> getSupportedAnnotations() {
//         Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
//         //添加注解
//         annotations.add(InitAttrConfigs.class);
//         return annotations;
//     }
//
//     @Override
//     public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//         logger.info("Container process...");
//         mProxyMap.clear();
//         scanForRClasses(roundEnvironment);
//         parseAnnotation(roundEnvironment);
//         generateCode();
//         return true;
//     }
//
//     private void parseAnnotation(RoundEnvironment roundEnv) {
//         Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(InitAttrConfigs.class);
//         for (Element element : elements) {
//             TypeElement typeElement = (TypeElement) element;
//             String className = typeElement.getQualifiedName().toString(); //完全限定名称
//             ContainerProxyInfo proxyInfo = new ContainerProxyInfo(mElementUtils, typeElement, processingEnv);
//             mProxyMap.put(className, proxyInfo);
//
//             InitAttrConfigs configs = typeElement.getAnnotation(InitAttrConfigs.class);
//             Map<String, Id> components = proxyInfo.getComponts();
//             components.clear();
//             for (InitAttrConfig config : configs.value()) {
//
//
//                 try {
//                     config.component();
//                 } catch (MirroredTypeException e) {
//                     TypeMirror typeMirror = e.getTypeMirror();
//                     /**
//                      * 通过这个方法来获取具体实现类型！！！
//                      */
//                     Types typeUtils = processingEnv.getTypeUtils();
//                     TypeElement classTypeElement = (TypeElement) typeUtils.asElement(typeMirror);
//
//                     int resId = config.resourceId();
//                     String name = classTypeElement.getQualifiedName().toString();
//                     QualifiedId qualifiedId = elementToQualifiedId(element, resId);
//                     logger.info("qualifiedId=" + qualifiedId);
//                     Id id = getId(qualifiedId);
//                     CodeBlock codeBlock = id.code;
//                     logger.info("[parseAnnotation]:" + "resId:" + resId + "codeBlock=" + codeBlock);
//                     components.put(name, id);
//                 }
//
//
//             }
//
//             proxyInfo.setComponts(components);
//
//
//         }
//     }
//
//     private void generateCode() {
//         //统一进行文件生成
//         for (ContainerProxyInfo proxyInfo : mProxyMap.values()) {
//             try {
//                 JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
//                         proxyInfo.getProxyClassFullName(),
//                         proxyInfo.getTypeElement());
//                 Writer writer = jfo.openWriter();
//                 writer.write(proxyInfo.generateJavaCode());
//                 writer.flush();
//                 writer.close();
//             } catch (IOException e) {
//                 logger.error("presenter generateCode fail!");
//                 logger.error(e);
//             }
//         }
//     }
//
//     @Override
//     public SourceVersion getSupportedSourceVersion() {
//         return SourceVersion.latestSupported();
//     }
//
//     private QualifiedId elementToQualifiedId(Element element, int id) {
//         return new QualifiedId(mElementUtils.getPackageOf(element), id);
//     }
//
//     private Id getId(QualifiedId qualifiedId) {
//         if (symbols.get(qualifiedId) == null) {
//             symbols.put(qualifiedId, new Id(qualifiedId.id));
//         }
//         return symbols.get(qualifiedId);
//     }
//
//     private void scanForRClasses(RoundEnvironment env) {
//         if (trees == null) {
//             return;
//         }
//
//         RClassScanner scanner = new RClassScanner();
//
//         for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
//             for (Element element : env.getElementsAnnotatedWith(annotation)) {
//                 JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
//                 if (tree != null) { // tree can be null if the references are compiled types and not source
//                     scanner.setCurrentPackage(mElementUtils.getPackageOf(element));
//                     tree.accept(scanner);
//                 }
//             }
//         }
//
//         for (Map.Entry<PackageElement, Set<Symbol.ClassSymbol>> packageNameToRClassSet
//                 : scanner.getRClasses().entrySet()) {
//             PackageElement respectivePackageName = packageNameToRClassSet.getKey();
//             for (Symbol.ClassSymbol rClass : packageNameToRClassSet.getValue()) {
//                 parseRClass(respectivePackageName, rClass, scanner.getReferenced());
//             }
//         }
//     }
//
//     private void parseRClass(PackageElement respectivePackageName, Symbol.ClassSymbol rClass,
//                              Set<String> referenced) {
//         TypeElement element;
//
//         try {
//             element = rClass;
//         } catch (MirroredTypeException mte) {
//             element = (TypeElement) typeUtils.asElement(mte.getTypeMirror());
//         }
//
//         JCTree tree = (JCTree) trees.getTree(element);
//         if (tree != null) { // tree can be null if the references are compiled types and not source
//             IdScanner idScanner =
//                     new IdScanner(symbols, mElementUtils.getPackageOf(element), respectivePackageName,
//                             referenced);
//             tree.accept(idScanner);
//         } else {
//             parseCompiledR(respectivePackageName, element, referenced);
//         }
//     }
//
//     private void parseCompiledR(PackageElement respectivePackageName, TypeElement rClass,
//                                 Set<String> referenced) {
//         for (Element element : rClass.getEnclosedElements()) {
//             String innerClassName = element.getSimpleName().toString();
//             if (SUPPORTED_TYPES.contains(innerClassName)) {
//                 for (Element enclosedElement : element.getEnclosedElements()) {
//                     if (enclosedElement instanceof VariableElement) {
//                         String fqName = mElementUtils.getPackageOf(enclosedElement).getQualifiedName().toString()
//                                 + ".R."
//                                 + innerClassName
//                                 + "."
//                                 + enclosedElement.toString();
//                         if (referenced.contains(fqName)) {
//                             VariableElement variableElement = (VariableElement) enclosedElement;
//                             Object value = variableElement.getConstantValue();
//
//                             if (value instanceof Integer) {
//                                 int id = (Integer) value;
//                                 ClassName rClassName =
//                                         ClassName.get(mElementUtils.getPackageOf(variableElement).toString(), "R",
//                                                 innerClassName);
//                                 String resourceName = variableElement.getSimpleName().toString();
//                                 QualifiedId qualifiedId = new QualifiedId(respectivePackageName, id);
//                                 symbols.put(qualifiedId, new Id(id, rClassName, resourceName));
//                             }
//                         }
//                     }
//                 }
//             }
//         }
//     }
//
//     private static class RClassScanner extends TreeScanner {
//         // Maps the currently evaluated rPackageName to R Classes
//         private final Map<PackageElement, Set<Symbol.ClassSymbol>> rClasses = new LinkedHashMap<>();
//         private PackageElement currentPackage;
//         private Set<String> referenced = new HashSet<>();
//
//         @Override
//         public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
//             Symbol symbol = jcFieldAccess.sym;
//             if (symbol != null
//                     && symbol.getEnclosingElement() != null
//                     && symbol.getEnclosingElement().getEnclosingElement() != null
//                     && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
//                 Set<Symbol.ClassSymbol> rClassSet = rClasses.get(currentPackage);
//                 if (rClassSet == null) {
//                     rClassSet = new HashSet<>();
//                     rClasses.put(currentPackage, rClassSet);
//                 }
//                 referenced.add(getFqName(symbol));
//                 rClassSet.add(symbol.getEnclosingElement().getEnclosingElement().enclClass());
//             }
//         }
//
//         Map<PackageElement, Set<Symbol.ClassSymbol>> getRClasses() {
//             return rClasses;
//         }
//
//         Set<String> getReferenced() {
//             return referenced;
//         }
//
//         void setCurrentPackage(PackageElement packageElement) {
//             this.currentPackage = packageElement;
//         }
//     }
//
//     private static String getFqName(Symbol rSymbol) {
//         return rSymbol.packge().getQualifiedName().toString()
//                 + ".R."
//                 + rSymbol.enclClass().name.toString()
//                 + "."
//                 + rSymbol.name.toString();
//     }
//
//     private static class IdScanner extends TreeScanner {
//         private final Map<QualifiedId, Id> ids;
//         private final PackageElement rPackageName;
//         private final PackageElement respectivePackageName;
//         private final Set<String> referenced;
//
//         IdScanner(Map<QualifiedId, Id> ids, PackageElement rPackageName,
//                   PackageElement respectivePackageName, Set<String> referenced) {
//             this.ids = ids;
//             this.rPackageName = rPackageName;
//             this.respectivePackageName = respectivePackageName;
//             this.referenced = referenced;
//         }
//
//         @Override
//         public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
//             for (JCTree tree : jcClassDecl.defs) {
//                 if (tree instanceof ClassTree) {
//                     ClassTree classTree = (ClassTree) tree;
//                     String className = classTree.getSimpleName().toString();
//                     if (SUPPORTED_TYPES.contains(className)) {
//                         ClassName rClassName = ClassName.get(rPackageName.getQualifiedName().toString(), "R",
//                                 className);
//                         VarScanner scanner = new VarScanner(ids, rClassName, respectivePackageName, referenced);
//                         ((JCTree) classTree).accept(scanner);
//                     }
//                 }
//             }
//         }
//     }
//
//     private static class VarScanner extends TreeScanner {
//         private final Map<QualifiedId, Id> ids;
//         private final ClassName className;
//         private final PackageElement respectivePackageName;
//         private final Set<String> referenced;
//
//         private VarScanner(Map<QualifiedId, Id> ids, ClassName className,
//                            PackageElement respectivePackageName, Set<String> referenced) {
//             this.ids = ids;
//             this.className = className;
//             this.respectivePackageName = respectivePackageName;
//             this.referenced = referenced;
//         }
//
//         @Override
//         public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
//             if ("int".equals(jcVariableDecl.getType().toString())) {
//                 String resourceName = jcVariableDecl.getName().toString();
//                 if (referenced.contains(getFqName(jcVariableDecl.sym))) {
//                     int id = Integer.valueOf(jcVariableDecl.getInitializer().toString());
//                     QualifiedId qualifiedId = new QualifiedId(respectivePackageName, id);
//                     ids.put(qualifiedId, new Id(id, className, resourceName));
//                 }
//             }
//         }
//     }
//
//     private static AnnotationMirror getMirror(Element element,
//                                               Class<? extends Annotation> annotation) {
//         for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
//             if (annotationMirror.getAnnotationType().toString().equals(annotation.getCanonicalName())) {
//                 return annotationMirror;
//             }
//         }
//         return null;
//     }
//
//
// }
