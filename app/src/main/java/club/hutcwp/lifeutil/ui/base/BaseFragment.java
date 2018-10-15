package club.hutcwp.lifeutil.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hut.cwp.mvp.MvpFragment;
import hut.cwp.mvp.MvpPresenter;
import hut.cwp.mvp.MvpView;

/**
 * Created by hutcwp on 2017/4/15.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public abstract class BaseFragment<P extends MvpPresenter<V>, V extends MvpView> extends MvpFragment<P,V> {

    private static final String TAG = "BaseFragment";
    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    protected ViewDataBinding binding;

    @LayoutRes
    protected abstract int getLayoutId();

    public ViewDataBinding getBinding() {
        return binding;
    }

    //初始化View
    protected abstract void initViews();

    //懒加载
    protected abstract void lazyFetchData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initViews();
        return binding.getRoot();

    }

    /**
     * 判断Fragment懒加载是否准备完成
     */
    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }

    /**
     * 用户可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.d(TAG, "setUserVisibleHint: ");
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    /**
     * View创建完成
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasFetchData = false;
        isViewPrepared = false;
    }

    public void showSnack(String msg) {
        if(binding.getRoot()!=null) {
            Snackbar snackbar = Snackbar.make(binding.getRoot(), msg, Toast.LENGTH_LONG);
            snackbar.show();
        }
    }
}
