package com.hutcwp.common.mvp;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.hutcwp.util.BarUtils;


/**
 * desc   :基类Activity
 * base class Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 是否拦截模板定义的onCreate
     * Whether to intercept the template definition
     */
    protected boolean interceptOnCreate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 设置布局
         * setLayout
         * */
        setContentView(bindLayout());
        if (isTransparentStatusBar()) {
            BarUtils.transparentStatusBar(this);
        }


        if (interceptOnCreate) {
            return;
        }
        /*
         * 初始化数据
         * initialization data
         * */
        initData(savedInstanceState);
        /*
         * 初始化布局
         * Initialize layout
         * */
        initView();
        /*
         * 请求类数据加载
         * Request class data loading
         * */
        requestData();
    }

    /**
     * 是否拦截模板定义的onCreate
     * Whether to intercept the template definition  onCreate
     */
    public void setInterceptOnCreate(boolean interceptOnCreate) {
        this.interceptOnCreate = interceptOnCreate;
    }

    protected abstract int bindLayout();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView();

    protected void requestData() {

    }

    protected boolean isTransparentStatusBar() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public Resources getResources() {
        /*
         * 字体大小不随设置变动而改变。
         * The font size does not change with the Settings.
         * */
        Resources resources = super.getResources();
        Configuration newConfig = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();

        if (newConfig.fontScale != 1) {
            newConfig.fontScale = 1;
           /* if (Build.VERSION.SDK_INT >= 17) {
                Context configurationContext = createConfigurationContext(newConfig);
                resources = configurationContext.getResources();
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
            } else {*/
            resources.updateConfiguration(newConfig, displayMetrics);
            // }
        }
        return resources;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 使用IdleHandler延迟执行部分任务
     * Use IdleHandler to delay the execution of some tasks
     */
    protected final void delayToDealOnUiThread(final Runnable runnable) {
        Looper.myQueue().addIdleHandler(() -> {
            if (runnable != null) {
                runnable.run();
            }
            return false;
        });
    }

}
