package com.hutcwp.mvp.compiler.schem;// package hut.cwp.compiler;


import com.example.annotations.shceme.Shceme;
import com.google.auto.service.AutoService;
import com.hutcwp.mvp.compiler.other.ContainerProxyInfo;
import com.hutcwp.mvp.compiler.other.Id;
import com.hutcwp.mvp.compiler.other.Logger;
import com.hutcwp.mvp.compiler.other.QualifiedId;
import com.hutcwp.mvp.compiler.velocity.IMapping;
import com.hutcwp.mvp.compiler.velocity.TemplateSourceBuilder;
import com.hutcwp.mvp.compiler.velocity.TemplateSourceCreator;
import com.squareup.javapoet.ClassName;
import com.sun.source.tree.ClassTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class SchemProcessor extends AbstractProcessor {

    private Logger logger;
    private Messager messager;
    private Elements mElementUtils;
    private Map<String, ContainerProxyInfo> mProxyMap = new HashMap<>();
    private final Map<QualifiedId, Id> symbols = new LinkedHashMap<>();

    private Trees trees;

    private static String TEMPLATE_NAME = "template/CcJsMethodCollection.vm";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
        logger = new Logger(messager);
        mElementUtils = processingEnv.getElementUtils();
        trees = Trees.instance(processingEnv);
        logger.info("tree is " + trees);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(Shceme.class.getCanonicalName());
        return supportTypes;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        logger.info("SchemProcessor process...");
        mProxyMap.clear();
        parseAnnotation(roundEnvironment);
        generateCode();
        return true;
    }

    private void parseAnnotation(RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Shceme.class);
        List<JsMethodData> jsMethodList = new ArrayList<>();
        JsMethodData jsMethodData;

        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getQualifiedName().toString(); //完全限定名称

            System.out.println("打印注解：" + className);
            if (element.getKind() == ElementKind.CLASS) {
                jsMethodData = new JsMethodData(element.getSimpleName().toString(), "test");
                jsMethodList.add(jsMethodData);
            }
        }

        String className = String.format("Shecme%s", hashCode());
        TemplateSourceCreator.getInstance().create(processingEnv, TemplateSourceBuilder.create()
            // 源文件包名
            .packageName("com.netease.cc.js")
            // 源文件类名
            .className(className)
            // 基于哪个velocity模板创建源文件
            .templateName(TEMPLATE_NAME)
            // Js方法列表
            .putList("jsMethods", jsMethodList));
    }

    private void generateCode() {
        //统一进行文件生成

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    /**
     * Js方法描述数据
     */
    private static class JsMethodData implements IMapping {

        /**
         * Js方法名称
         */
        private final String name;
        /**
         * Js方法描述
         */
        private final String comment;

        public JsMethodData(String name, String comment) {
            this.name = name;
            this.comment = comment;
        }

        @Override
        public Map<String, Object> mapping() {
            Map<String, Object> map = new HashMap<>(2);
            map.put("name", name);
            map.put("comment", comment);
            return map;
        }
    }


}
