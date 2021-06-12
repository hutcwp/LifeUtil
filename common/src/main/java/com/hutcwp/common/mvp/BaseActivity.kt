package com.hutcwp.common.mvp

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.hutcwp.common.R
import com.hutcwp.common.event.BaseActivityEvent
import me.hutcwp.constants.AppGlobal
import me.hutcwp.util.BarUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * desc   :基类Activity
 * base class Activity
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 是否拦截模板定义的onCreate
     * Whether to intercept the template definition
     */
    protected fun isInterceptOnCreate() = false

    protected fun needActionBar() = false

    protected val isTransparentStatusBar = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        initTheme()
        if (!needActionBar()) {
            if (supportActionBar != null) {
                supportActionBar!!.hide()
            }
        }

        /*
         * 设置布局
         * setLayout
         * */setContentView(bindLayout())
        if (isTransparentStatusBar) {
            BarUtils.transparentStatusBar(this)
        }
        if (isInterceptOnCreate()) {
            return
        }
        /*
         * 初始化数据
         * initialization data
         * */initData(savedInstanceState)
        /*
         * 初始化布局
         * Initialize layout
         * */initView()
        /*
         * 请求类数据加载
         * Request class data loading
         * */requestData()
    }

    protected abstract fun bindLayout(): Int
    protected abstract fun initData(savedInstanceState: Bundle?)
    protected abstract fun initView()
    protected open fun requestData() {}


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun getResources(): Resources {
        /*
         * 字体大小不随设置变动而改变。
         * The font size does not change with the Settings.
         * */
        val resources = super.getResources()
        val newConfig = resources.configuration
        val displayMetrics = resources.displayMetrics
        if (newConfig.fontScale != 1f) {
            newConfig.fontScale = 1f
            /* if (Build.VERSION.SDK_INT >= 17) {
                Context configurationContext = createConfigurationContext(newConfig);
                resources = configurationContext.getResources();
                displayMetrics.scaledDensity = displayMetrics.density * newConfig.fontScale;
            } else {*/resources.updateConfiguration(newConfig, displayMetrics)
            // }
        }
        return resources
    }

    private fun initTheme() {
        val pf = getSharedPreferences(AppGlobal.SHARE_FILE_NAME, MODE_PRIVATE)
        val theme = pf.getInt(AppGlobal.FL_CUR_THEME_SP, R.style.GreeneryTheme)

        when (theme) {
            0 -> {
                setTheme(R.style.LapisBlueTheme)
            }
            1 -> {
                setTheme(R.style.PaleDogwoodTheme)
            }
            2 -> {
                setTheme(R.style.GreeneryTheme)
            }
            3 -> {
                setTheme(R.style.PrimroseYellowTheme)
            }
            4 -> {
                setTheme(R.style.FlameTheme)
            }
            5 -> {
                setTheme(R.style.IslandParadiseTheme)
            }
            6 -> {
                setTheme(R.style.KaleTheme)
            }
            7 -> {
                setTheme(R.style.PinkYarrowTheme)
            }
            8 -> {
                setTheme(R.style.NiagaraTheme)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                window.statusBarColor = getColorAccent()
            }
        }
    }

    private fun getColorAccent(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.data
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseActivityEvent<T>?) {
    }

    /**
     * 使用IdleHandler延迟执行部分任务
     * Use IdleHandler to delay the execution of some tasks
     */
    protected fun delayToDealOnUiThread(runnable: Runnable?) {
        Looper.myQueue().addIdleHandler {
            runnable?.run()
            false
        }
    }
}