package com.hutcwp.live.livebiz.ui.component;


import androidx.fragment.app.FragmentManager;
import com.example.presenter.core.MvpFragment;
import com.example.presenter.core.MvpPresenter;
import com.example.presenter.core.MvpView;
import com.hutcwp.live.livebiz.base.util.MLog;

/**
 * Created by hutcwp on 2018/8/20 00:50
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public abstract class Component<P extends MvpPresenter<V>, V extends MvpView> extends MvpFragment<P, V> {

    private static final String TAG = "Component";

//    abstract int getResId();

    public Component() {
    }


    final public FragmentManager getChildManager() {
        return getChildManager();
    }

    public void showSelf() {
        if (!getFragmentManager().isDestroyed()) {
            getFragmentManager().beginTransaction().show(this).commitAllowingStateLoss();
        }
    }

    public void hideSelf() {
        if (!getFragmentManager().isDestroyed()) {
            getFragmentManager().beginTransaction().hide(this).commitAllowingStateLoss();
        }
    }

    /*
     * 获取当前组件容器activity的上下文，此上下文为activity的一个hashcode，用于区分不同的页面用
     * @return
     */
    public int getActivityContext() {
        int context = 0;
        if (getContext() != null) {
            context = getContext().hashCode();
            MLog.info(TAG, "[getActivityContext] context== " + context);
        }
        return context;
    }

}
