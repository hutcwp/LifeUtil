package com.example.annotations.shceme;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * <p> 生成协议
 * Created by n24314 on 2022/10/21. E-mail: caiwenpeng@corp.netease.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Shceme {

    public String name() default "";

}
