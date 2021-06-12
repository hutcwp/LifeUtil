package com.hutcwp.common.mvp;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.hutcwp.util.ThreadUtils;


/**
 * fragment基类
 * fragment The base class
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 是否进行了初始化
     * Whether it was initialized or not
     */
    protected boolean isInitView = false;
    /**
     * 是否对用户可见
     * Is it visible to the user
     */
    private boolean isVisible = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(bindLayout(), container, false);
        isInitView = true;
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*
         * isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
         * The Boolean value isVisibleToUser indicates whether the UI user of the Fragment is visible or not. Get the token and record it
         * */
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    /**
     * 判断是否要加载数据
     * Determine if you want to load the data
     */
    private void isCanLoadData() {
        /*
         * 所以条件是view初始化完成并且对用户可见
         * So the condition is that the view is initialized and visible to the user
         * */
        if (isInitView && isVisible) {
            onLazyLoad();
            /*
             * 防止重复加载数据
             * Prevent reloading of data
             * */
            // isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 绑定布局
     * Binding layout
     *
     * @return the int
     */
    protected abstract int bindLayout();


    /**
     * 加载要显示的数据
     * Loads the data to display
     */
    protected abstract void onLazyLoad();

    /**
     * 初始化布局
     * Initialize layout
     *
     * @param rootView the root view 根视图
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化数据
     * initialization data
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        if ((getView() != null) && (getView().getParent() != null)) {
            ((ViewGroup) getView().getParent()).removeView(getView());
        }
        super.onDestroyView();
    }

    /**
     * 将任务切换到Ui线程执行
     * Switch the task to Ui thread execution
     *
     * @param runnable the runnable 可运行的
     */
    protected final void runOnUiThread(Runnable runnable) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(runnable);
        } else if (getView() != null) {
            getView().post(runnable);
        } else {
            ThreadUtils.runOnUiThread(runnable);
        }
    }

    /**
     * 使用IdleHandler延迟执行部分任务
     * Use IdleHandler Delay the execution of part of the task
     *
     * @param runnable the runnable
     */
    protected final void delayToDealOnUiThread(final Runnable runnable) {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (runnable != null) {
                    runnable.run();
                }
                return false;
            }
        });
    }
}
