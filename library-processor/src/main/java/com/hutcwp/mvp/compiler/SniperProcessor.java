package com.hutcwp.mvp.compiler;

import com.example.annotations.mvp.DelegateBind;
import com.google.auto.service.AutoService;
import com.hutcwp.mvp.compiler.process.IProcessor;
import com.hutcwp.mvp.compiler.process.MvpProcessor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 自动生成数据中心store的state类和action类
 */

@AutoService(Processor.class)//自动生成 javax.annotation.processing.IProcessor 文件
public class SniperProcessor extends AbstractProcessor {

    public Filer mFiler; //文件相关的辅助类
    public Elements mElements; //元素相关的辅助类
    public Messager mMessager; //日志相关的辅助类
    public Types mTypes;
    public Map<String, String> mOptions;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        System.out.println("SniperProcessor: init method invoked!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("process============SniperProcessor");
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mTypes = processingEnv.getTypeUtils();
        mOptions = processingEnv.getOptions();

        mMessager.printMessage(Diagnostic.Kind.NOTE, "SniperProcessor ");

        List<IProcessor> processorList = new ArrayList<>();
        processorList.add(new MvpProcessor());
        process(processorList, roundEnv);

        return true;
    }

    private void process(List<IProcessor> processors, RoundEnvironment roundEnv) {
        System.out.println("process============SniperProcessor2");
        for (IProcessor processor : processors) {
            processor.process(roundEnv, this);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(DelegateBind.class.getCanonicalName());

        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
