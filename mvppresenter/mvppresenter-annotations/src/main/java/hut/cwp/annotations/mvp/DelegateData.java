package hut.cwp.annotations.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于MvpActivity,MvpFragment,MvpDialogFragment子类方法订阅presenter中的数据
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface DelegateData {
}
