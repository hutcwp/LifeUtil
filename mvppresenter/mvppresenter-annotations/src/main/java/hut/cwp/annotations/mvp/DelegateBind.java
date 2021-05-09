package hut.cwp.annotations.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于MvpActivity,MvpFragment,MvpDialogFragment子类绑定presenter
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DelegateBind {
    Class<?> presenter();
}
