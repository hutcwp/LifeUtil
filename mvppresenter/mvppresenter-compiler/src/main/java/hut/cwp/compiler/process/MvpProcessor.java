package hut.cwp.compiler.process;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import hut.cwp.annotations.mvp.DelegateBind;
import hut.cwp.annotations.mvp.DelegateData;
import hut.cwp.compiler.SniperProcessor;
import hut.cwp.compiler.util.Utils;

/**
 * MvpProcessor
 */

public class MvpProcessor implements IProcessor {

    private final ClassName
            PRESENTER_BINDER = ClassName.get("hut.cwp.api.mvp", "PresenterBinder");
    private final ClassName REFLEXDATA_INNER = ClassName
            .get("hut.cwp.annotations.mvp.LifeData", "InnerClass");

    private final String FIELD_PRESENTER = "presenter";
    private final String FIELD_VIEW = "view";
    private final String LIFEDATA_NAME = "hut.cwp.annotations.mvp.LifeData";

    private SniperProcessor mProcessor;

    @Override
    public void process(RoundEnvironment roundEnv, SniperProcessor mAbstractProcessor) {
        mProcessor = mAbstractProcessor;
        Set<TypeElement> set =
                ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(DelegateBind.class));
        for (TypeElement element : set) {
            String packageName =
                    mProcessor.mElements.getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName() + "$$PresenterBinder";
            if (getAnnotationClassValue(element.getAnnotation(DelegateBind.class)) != null) {
                ClassName presenterClass = ClassName.bestGuess(
                        getAnnotationClassValue(element.getAnnotation(DelegateBind.class))
                                .toString());

                TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className)
                        .addModifiers(Modifier.PUBLIC)
                        .addTypeVariable(TypeVariableName.get("P", presenterClass))
                        .addTypeVariable(TypeVariableName.get("V", ClassName.get(element)))
                        .addSuperinterface(ParameterizedTypeName
                                .get(PRESENTER_BINDER, TypeVariableName.get("P"),
                                        TypeVariableName.get("V")));

                typeBuilder.addField(
                        FieldSpec.builder(presenterClass, FIELD_PRESENTER, Modifier.PRIVATE)
                                .build());
                typeBuilder.addField(
                        FieldSpec.builder(ClassName.get(element), FIELD_VIEW, Modifier.PRIVATE)
                                .build());

                Set<ExecutableElement> bindDataElement = getBindDataElement(element);

                MethodSpec bindDataMethod = generateBindDataMethod(presenterClass, bindDataElement);
                typeBuilder.addMethod(bindDataMethod);

                MethodSpec
                        unBindDataMethod =
                        generateUnBindDataMethod(presenterClass, bindDataElement);
                typeBuilder.addMethod(unBindDataMethod);

                MethodSpec.Builder bindPresenterMethodBuilder =
                        MethodSpec.methodBuilder("bindPresenter")
                                .addAnnotation(Override.class)
                                .addModifiers(Modifier.PUBLIC)
                                .returns(presenterClass)
                                .addParameter(ClassName.get(element), "t")
                                .addStatement("$N = t", FIELD_VIEW)
                                .addStatement("$L = new $T()", FIELD_PRESENTER, presenterClass)
                                .addStatement("$N()", bindDataMethod)
                                .addStatement("return $L", FIELD_PRESENTER);

                typeBuilder.addMethod(bindPresenterMethodBuilder.build());

                MethodSpec.Builder unbindPresenterMethodBuilder =
                        MethodSpec.methodBuilder("unbindPresenter")
                                .addAnnotation(Override.class)
                                .addModifiers(Modifier.PUBLIC)
                                .returns(void.class)
                                .addStatement("$N()", unBindDataMethod)
                                .addStatement("$L = null", FIELD_VIEW)
                                .addStatement("$L = null", FIELD_PRESENTER);

                typeBuilder.addMethod(unbindPresenterMethodBuilder.build());

                try {
                    JavaFile javaFile = JavaFile.builder(packageName, typeBuilder.build()).build();
                    javaFile.writeTo(mProcessor.mFiler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成unBindData方法，根据生命周期对lifeData进行取消绑定，避免内存泄漏
     *
     * @param presenterClass
     * @param bindData
     * @return
     */
    private MethodSpec generateUnBindDataMethod(ClassName presenterClass,
                                                Set<ExecutableElement> bindData) {
        TypeElement presenterElement =
                mProcessor.mElements.getTypeElement(presenterClass.reflectionName());
        MethodSpec.Builder dataBindBuilder = MethodSpec.methodBuilder("unBindData")
                .addModifiers(Modifier.PRIVATE)
                .returns(void.class);
        for (Element innerElement : presenterElement.getEnclosedElements()) {
            if (innerElement instanceof VariableElement) {

                String[] parameterized =
                        Utils.getParameterizedTypeByTypeMirror(innerElement.asType());
                TypeName reflexData = Utils.getClassNameByTypeMirror(innerElement.asType());

                if (parameterized.length == 1 && reflexData.toString().contains(LIFEDATA_NAME)) {
                    String var = innerElement.getSimpleName().toString();
                    dataBindBuilder.addStatement("$L.$L.setInnerClass(null)", FIELD_PRESENTER, var);
                }
            }
        }
        return dataBindBuilder.build();
    }

    /**
     * 生成bindData方法，主要对lifeData进行方法反射调用绑定
     *
     * @param presenterClass
     * @param bindData
     * @return
     */
    private MethodSpec generateBindDataMethod(ClassName presenterClass,
                                              Set<ExecutableElement> bindData) {
        mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE,
                "lifeData's presenter-------------->" + presenterClass.reflectionName());
        TypeElement presenterElement =
                mProcessor.mElements.getTypeElement(presenterClass.reflectionName());
        MethodSpec.Builder dataBindBuilder = MethodSpec.methodBuilder("bindData")
                .addModifiers(Modifier.PRIVATE)
                .returns(void.class);
        for (Element innerElement : presenterElement.getEnclosedElements()) {
            if (innerElement instanceof VariableElement) {
                String[] parameterized =
                        Utils.getParameterizedTypeByTypeMirror(innerElement.asType());
                TypeName lifeData = Utils.getClassNameByTypeMirror(innerElement.asType());

                mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE,
                        "variable's name------------------->" + innerElement.getSimpleName());
                mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE,
                        "variable's parameterized size----->" + parameterized.length);
                mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE,
                        "variable's type------------------->" + lifeData.toString());

                if (parameterized.length == 1 && lifeData.toString().contains(LIFEDATA_NAME)) {
                    String var = innerElement.getSimpleName().toString();
                    dataBindBuilder
                            .addStatement("$L.$L = new $T()", FIELD_PRESENTER, var, lifeData);

//                    mProcessor.mMessager.printMessage(Diagnostic.Kind.NOTE,
//                            "ClassName.bestGuess(parameterized[0])----->"+ ClassName.bestGuess(parameterized[0]).reflectionName());

                    Set<ExecutableElement> lifeDataMethods =
                            getBindDatElementByLiftData((VariableElement) innerElement, bindData);
                    if (lifeDataMethods != null && lifeDataMethods.size() > 0) {
                        dataBindBuilder
                                .addStatement("$L.$L.setInnerClass($L)", FIELD_PRESENTER, var,
                                        generateLifeDataAnonymousClass(
                                                innerElement.getSimpleName().toString(), ClassName
                                                        .bestGuess(parameterized[0]),
                                                lifeDataMethods));
                    } else {
                        dataBindBuilder
                                .addStatement("$L.$L.setInnerClass(null)", FIELD_PRESENTER, var);
                    }
                }
            }
        }
        return dataBindBuilder.build();
    }

    /**
     * 获取activity或者fragment中使用了@DelegateData的所有元素
     *
     * @param typeElement
     * @return
     */
    private Set<ExecutableElement> getBindDataElement(TypeElement typeElement) {
        Set<ExecutableElement> set = new HashSet<>();
        for (Element e : typeElement.getEnclosedElements()) {
            if (e instanceof ExecutableElement && e.getAnnotation(DelegateData.class) != null) {
                set.add((ExecutableElement) e);
            }
        }
        return set;
    }

    /**
     * 获取presenter中lifeData需要进行数据绑定的activity/fragment中的方法(@DelegateData)元素集合
     *
     * @param lifeData
     * @param elementSet
     * @return
     */
    private Set<ExecutableElement> getBindDatElementByLiftData(VariableElement lifeData,
                                                               Set<ExecutableElement> elementSet) {
        Set<ExecutableElement> set = new HashSet<>();
        for (ExecutableElement executableElement : elementSet) {
            List<? extends VariableElement> paraList = executableElement.getParameters();
            if (paraList != null && paraList.size() == 1 &&
                    lifeData.getSimpleName().toString()
                            .equalsIgnoreCase(paraList.get(0).getSimpleName().toString())) {
                set.add(executableElement);
            }
        }
        return set;
    }

    /**
     * 生成ReflexData的匿名类InnerClass
     *
     * @param variableName 内部类泛型的className
     * @return
     */
    private TypeSpec generateLifeDataAnonymousClass(String variableName, ClassName parameter,
                                                    Set<ExecutableElement> lifeDataMethods) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onDataChange")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(parameter, "t");
        for (ExecutableElement executableElement : lifeDataMethods) {
            methodBuilder.addStatement("$N.$N(t)", FIELD_VIEW, executableElement.getSimpleName());
        }

        return TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(REFLEXDATA_INNER, parameter))
                .addMethod(methodBuilder.build())
                .build();

    }

    private TypeMirror getAnnotationClassValue(DelegateBind annotation) {
        try {
            annotation.presenter();
        } catch (MirroredTypeException mte) {
            if (mte.getTypeMirror() != null) {
                return mte.getTypeMirror();
            }
        }
        return null;
    }
}
