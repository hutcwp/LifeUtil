package com.hutcwp.common.mvp;

/**
 *
 * date   : 2019/5/11
 * desc   :基类Mvp的Presenter,包含明确的Model
 * The base class Mvp The Presenter Include STH Model
 */
public abstract class BasePresenter<V extends IBaseView, M extends IModel> extends Presenter<V> {
    /**
     * 对应的model
     * The corresponding model
     */
    protected M mModel;

    public BasePresenter(M model) {
        this.mModel = model;
    }

}
