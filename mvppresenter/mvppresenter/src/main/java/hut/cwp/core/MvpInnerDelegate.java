package hut.cwp.core;

import android.os.Bundle;

import hut.cwp.api.mvp.MvpApi;
import hut.cwp.api.mvp.PresenterBinder;
import hut.cwp.util.MLog;

/**
 * a delegate for {@link MvpFragment} or {@link MvpActivity} handle some logic
 *
 */

public class MvpInnerDelegate<P extends MvpPresenter<V>, V extends MvpView> {

    private MvpInnerDelegateCallback<P, V> mMvpInnerDelegateCallback;
    private PresenterBinder<P, V> mPresenterBinder;

    public MvpInnerDelegate(MvpInnerDelegateCallback<P, V> mvpInnerDelegateCallback) {
        mMvpInnerDelegateCallback = mvpInnerDelegateCallback;
    }

    /**
     * createPresenter封装在delegate中，避免在activity/fragment中多次出现
     *
     * @return
     */
    public P createPresenter() {
        P presenter = null;
        if(mPresenterBinder == null){
            mPresenterBinder = MvpApi.getPresenterBinder(mMvpInnerDelegateCallback.getMvpView());
            presenter = mPresenterBinder.bindPresenter(mMvpInnerDelegateCallback.getMvpView());
        }
        return presenter != null ? presenter : (P) new MvpPresenter<>();
    }

    /**
     * attach MvpView{@link MvpActivity}/{@link MvpFragment} to {@link MvpPresenter}
     *
     * @param savedInstanceState
     */
    public void attach(Bundle savedInstanceState) {
//        MLog.info("hutcwp","z->"+mMvpInnerDelegateCallback.getClass().getInterfaces().length);
//        for (Class ii : mMvpInnerDelegateCallback.getClass().getInterfaces()) {
//            //if (ii.isAssignableFrom(MvpView.class)) {
//            if(MvpView.class.isAssignableFrom(ii)){
                mMvpInnerDelegateCallback.getPresenter().attachView(mMvpInnerDelegateCallback.getMvpView());
                mMvpInnerDelegateCallback.getPresenter().onCreate(savedInstanceState);
//                break;
//            }
//        }
    }

    /**
     * detach PresenterBinder<P, V>,
     * 解除MvpView{@link MvpActivity}/{@link MvpFragment} 对 {@link MvpPresenter}中的数据绑定
     */
    public void detach(){
        if(mPresenterBinder != null){
            mPresenterBinder.unbindPresenter();
        }
        mPresenterBinder = null;
    }
}
