package com.hutcwp.mvp.compiler.velocity;

import androidx.annotation.NonNull;
import java.io.Writer;
import java.util.Locale;
import java.util.Properties;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 基于velocity模板创建java类
 *
 * @author g7734 on 2020/5/8
 */
public class TemplateSourceCreator {

    private VelocityEngine velocityEngine;
    private static TemplateSourceCreator instance;

    private TemplateSourceCreator() {
        init();
    }

    public static TemplateSourceCreator getInstance() {
        if (instance == null) {
            instance = new TemplateSourceCreator();
        }
        return instance;
    }

    /**
     * 初始化
     */
    private void init() {
        Properties props = getDefaultProp();
        velocityEngine = new VelocityEngine(props);
        velocityEngine.init();
    }

    /**
     * 基于velocity模板创建java类
     */
    public void create(@NonNull ProcessingEnvironment processingEnv,
                       @NonNull TemplateSourceBuilder builder) {
        String path = String.format(Locale.getDefault(), "%s.%s", builder.packageName, builder.className);
        Writer writer = null;

        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(path);
            Template template = velocityEngine.getTemplate(builder.templateName, "UTF-8");
            writer = jfo.openWriter();
            template.merge(builder.velocityContext, writer);
        } catch (Exception e) {
            System.out.println("TemplateSourceCreator creating %s base on template:%s exception!\\n\"");
//            messager.printError("TemplateSourceCreator creating %s base on template:%s exception!\n", builder.className, builder.templateName);
//            messager.printError(""+ e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

    /**
     * 获取默认的velocity配置
     */
    private Properties getDefaultProp() {
        Properties prop = new Properties();
        prop.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        prop.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        return prop;
    }
}
