package com.hutcwp.mvp.compiler.velocity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.velocity.VelocityContext;

/**
 * 基于velocity模板创建java类 Builder
 *
 * @author g7734 on 2020/5/8
 */
public class TemplateSourceBuilder {

    /**
     * 生成源文件的包名
     */
    String packageName;
    /**
     * 生成源文件的类名
     */
    String className;
    /**
     * 基于哪个velocity模板创建
     */
    String templateName;
    /**
     * 模板所需替换参数上下文
     */
    VelocityContext velocityContext;

    public static TemplateSourceBuilder create() {
        return new TemplateSourceBuilder();
    }

    private TemplateSourceBuilder() {
        velocityContext = new VelocityContext();
        velocityContext.put("date", getDate());
    }

    /**
     * 包名
     */
    public TemplateSourceBuilder packageName(String packageName) {
        this.packageName = packageName;
        velocityContext.put("packageName", packageName);
        return this;
    }

    /**
     * 类名
     */
    public TemplateSourceBuilder className(String className) {
        this.className = className;
        velocityContext.put("className", className);
        return this;
    }

    /**
     * 基于哪个velocity模板创建
     */
    public TemplateSourceBuilder templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    /**
     * 设置模板所需参数
     */
    public TemplateSourceBuilder put(String key, Object value) {
        if (value instanceof IMapping) {
            velocityContext.put(key, ((IMapping) value).mapping());
        } else if (value == null) {
            velocityContext.put(key, "null");
        } else {
            velocityContext.put(key, value);
        }
        return this;
    }

    /**
     * 设置模板所需参数
     */
    public TemplateSourceBuilder putList(String key, List<? extends IMapping> list) {
        if (list != null && list.size() > 0) {
            velocityContext.put(key, MappingConverter.covert(list));
        }
        return this;
    }

    /**
     * 获取创建日期
     */
    private String getDate() {
        return String.format("%s/%s/%s",
                Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR),
                Calendar.getInstance(Locale.CHINA).get(Calendar.MONTH) + 1,
                Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_MONTH));
    }
}