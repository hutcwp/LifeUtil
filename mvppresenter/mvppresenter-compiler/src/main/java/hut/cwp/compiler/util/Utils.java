package hut.cwp.compiler.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import hut.cwp.compiler.SniperProcessor;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;


public class Utils {

    public static final String ANNOTATION = "@";

    public static boolean isPublic(TypeElement element) {
        return element.getModifiers().contains(PUBLIC);
    }

    public static boolean isAbstract(TypeElement element) {
        return element.getModifiers().contains(ABSTRACT);
    }

    public static boolean isValidClass(Messager messager, TypeElement element) {
        if (element.getKind() != ElementKind.CLASS) {
            return false;
        }

        if (!isPublic(element)) {
            String message = String.format("Classes annotated with %s must be public.", ANNOTATION);
            Utils.error(message, messager);
            return false;
        }

        if (isAbstract(element)) {
            String message =
                    String.format("Classes annotated with %s must not be abstract.", ANNOTATION);
            Utils.error(message, messager);
            return false;
        }

        return true;
    }

    /**
     * 根据class的全限定类名字符串或者simpleName
     *
     * @param className
     * @return
     */
    public static String getSimpleClassName(String className) {
        int index = className.lastIndexOf(".");
        return className.substring(index + 1);
    }

    /**
     * 根据TypeMirror生成泛型类限定名数组
     *
     * @param typeMirror
     * @return
     */
    public static String[] getParameterizedTypeByTypeMirror(TypeMirror typeMirror) {
        String[] parameterized = new String[]{};
        if (!typeMirror.getKind().isPrimitive() &&
                typeMirror.toString().contains("<") && typeMirror.toString().contains(">")) {
            int start = typeMirror.toString().indexOf("<");
            int end = typeMirror.toString().indexOf(">");
            parameterized = typeMirror.toString().substring(start + 1, end).split(",");
        }
        return parameterized;
    }

    /**
     * 根据TypeMirror生成TypeName（支持泛型）
     *
     * @param typeMirror
     * @return
     */
    public static TypeName getClassNameByTypeMirror(TypeMirror typeMirror) {
        if (typeMirror.getKind().isPrimitive()) {
            switch (typeMirror.getKind()) {
                case BOOLEAN:
                    return TypeName.get(boolean.class);
                case BYTE:
                    return TypeName.get(byte.class);
                case SHORT:
                    return TypeName.get(short.class);
                case INT:
                    return TypeName.get(int.class);
                case LONG:
                    return TypeName.get(long.class);
                case CHAR:
                    return TypeName.get(char.class);
                case FLOAT:
                    return TypeName.get(float.class);
                case DOUBLE:
                    return TypeName.get(double.class);
            }
        } else if (typeMirror.toString().contains("<") && typeMirror.toString().contains(">")) {
            int start = typeMirror.toString().indexOf("<");
            int end = typeMirror.toString().indexOf(">");
            String className = typeMirror.toString().substring(0, start);
            String[] parameterized = typeMirror.toString().substring(start + 1, end).split(",");
            if (parameterized.length == 1) {
                return ParameterizedTypeName
                        .get(ClassName.bestGuess(className), ClassName.bestGuess(parameterized[0]));
            } else if (parameterized.length == 2) {
                return ParameterizedTypeName
                        .get(ClassName.bestGuess(className), ClassName.bestGuess(parameterized[0]),
                                ClassName
                                        .bestGuess(parameterized[1]));
            } else {
                return ClassName.bestGuess(className);
            }
        }
        return ClassName.bestGuess(typeMirror.toString());
    }

    /**
     * 判断2个element是否为继承关系
     *
     * @param element1
     * @param element2
     * @return
     */
    public static boolean isInherit(SniperProcessor processor, Element element1, Element element2) {
        return processor.mTypes.isSubtype(element1.asType(), element2.asType())
                || processor.mTypes.isSubtype(element2.asType(), element1.asType());
    }

    /**
     * 判断两个方法是否为重载关系
     *
     * @param element1
     * @param element2
     * @return
     */
    public static boolean isOverrideMethod(SniperProcessor processor, ExecutableElement element1,
                                           ExecutableElement element2) {
        boolean isSubsignature = processor.mTypes.isSubsignature((ExecutableType) element1.asType(),
                (ExecutableType) element2.asType());
        return element1.getSimpleName().equals(element2.getSimpleName()) && isSubsignature;
    }

    /**
     * 生成格式醒目的编译错误输出
     *
     * @param error
     * @return
     */
    public static void error(String error, Messager messager) {
        StringBuffer dump = new StringBuffer();
        dump.append(
                "---------------------------------sniper apt compile fail------------------------------------\n");
        dump.append(error);
        dump.append("\n");
        dump.append(
                "------------------------------------ reason dump end----------------------------------------\n");
        System.out.print(dump.toString());
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }
}
