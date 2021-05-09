package hut.cwp.compiler;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import hut.cwp.compiler.process.IProcessor;
import hut.cwp.compiler.process.MvpProcessor;

/**
 * 自动生成数据中心store的state类和action类
 */

@AutoService(Processor.class)//自动生成 javax.annotation.processing.IProcessor 文件
@SupportedSourceVersion(SourceVersion.RELEASE_7)//java版本支持
@SupportedAnnotationTypes({//标注注解处理器支持的注解类型
        "hut.cwp.annotations.mvp.DelegateBind",
        "hut.cwp.annotations.mvp.DelegateData"
})
public class SniperProcessor extends AbstractProcessor {

    public Filer mFiler; //文件相关的辅助类
    public Elements mElements; //元素相关的辅助类
    public Messager mMessager; //日志相关的辅助类
    public Types mTypes;
    public Map<String, String> mOptions;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("process============");
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mTypes = processingEnv.getTypeUtils();
        mOptions = processingEnv.getOptions();

        List<IProcessor> processorList = new ArrayList<>();
        processorList.add(new MvpProcessor());
        process(processorList, roundEnv);

        return true;
    }

    private void process(List<IProcessor> processors, RoundEnvironment roundEnv) {
        for (IProcessor processor : processors) {
            processor.process(roundEnv, this);
        }
    }
}
