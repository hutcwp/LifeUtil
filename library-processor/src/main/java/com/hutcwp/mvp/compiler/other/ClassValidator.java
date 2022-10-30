package com.hutcwp.mvp.compiler.other;

import static javax.lang.model.element.Modifier.PRIVATE;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by hutcwp on 2018/4/19.
 */

final class ClassValidator
{
    static boolean isPrivate(Element annotatedClass)
    {
        return annotatedClass.getModifiers().contains(PRIVATE);
    }

    static String getClassName(TypeElement type, String packageName)
    {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
