package com.hutcwp.common.mvp;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;

/**
 * date   : 2019/5/11
 * desc   :基类Mvp的Activity
 * The base class Mvp The Activity
 *
 * @param <P> the type parameter
 */
@SuppressWarnings("unchecked")
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity
        implements IBaseView {

    /**
     * 逻辑处理类
     * Logical processing class
     */
    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*
         * 初始化桥梁类
         * Initializes the bridge class
         * */
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化Presenter
     * 注意1：这里会优先使用presenter的一参构造(model)，如果没有则使用无参构造。
     * 注意2：若有参数model，则默认使用model的空参构造，如果要用有参构造请重写createPresenter()
     * Initialize the Presenter
     * Note 1: The one - parameter presenter construct (Model) is used first, and no - parameter construct is used if not.
     * Note 2: The null parameter construct of model is used by default if there is a parameter model. Override createPresenter() if there is a parameter model
     */
    private void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter == null) {
            Class presenter;
            Class model = null;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                /*
                 * 第一个泛型为presenter
                 * The first generic is presenter
                 * */
                presenter = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
                Type modelType = presenter.getGenericSuperclass();
                if (modelType instanceof ParameterizedType) {
                    /*
                     * presenter的第二个泛型参数是model
                     * presenter The second generic parameter is Model
                     * */
                    Type[] arguments = ((ParameterizedType) modelType).getActualTypeArguments();
                    if (arguments.length >= 2) {
                        model = (Class) arguments[1];
                    }
                }
            } else {
                /*
                 * 如果没有指定泛型参数，则默认使用Presenter
                 * If no generic parameter is specified, Presenter is used by default
                 * */
                presenter = Presenter.class;
            }
            try {
                if (model == null) {
                    mPresenter = (P) presenter.newInstance();
                } else {
                    Constructor pConstructor = presenter.getDeclaredConstructor(model);
                    mPresenter = (P) pConstructor.newInstance(model.newInstance());
                }
            } catch (Exception e) {
                /*
                 * 如果这里发生异常了，下边肯定会发生空指针异常，不处理的原因是要告诉使用者注意使用规则。
                 * If an exception occurs here, a null pointer exception must occur below, and the reason for not handling is to tell the user to pay attention to the rules
                 * */
                Log.e("Exception is ", e.toString());
            }
        }
        mPresenter.attachView(this);
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(mPresenter);
        mPresenter.detachView();
    }

    /**
     * 初始化逻辑处理类
     * Initializes the logical processing class
     *
     * @return the p
     */
    protected P createPresenter() {
        return null;
    }


//    @Override
//    public void onError(NvsError error) {
//        //  showToast(error.getErrorMessage(), SHORT);
//    }

    @Override
    public void onShowDialog() {
        /*
         * 网络请求的弹窗
         * Popovers for network requests
         * */
    }

    @Override
    public void onDismissDialog() {
        /*
         * 网络请求的弹窗
         * Popovers for network requests
         * */
    }
}
