package com.hutcwp.common.web

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hutcwp.common.R
import kotlinx.android.synthetic.main.activity_easy_web.*
import me.hutcwp.constants.RoutePath

@Route(path = RoutePath.SIMPLE_WEB, name = "web页面")
class EasyWebActivity : BaseAgentWebActivity() {

    @JvmField
    @Autowired(name = "url")
    var url: String = ""

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_easy_web)
        initView()
    }

    private fun initView() {
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.title = ""

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    @NonNull
    override fun getAgentWebParent(): ViewGroup {
        return findViewById<View>(R.id.container) as ViewGroup
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun getIndicatorColor(): Int {
        return Color.parseColor("#ff0000")
    }

    override fun setTitle(view: WebView?, title: String) {
        super.setTitle(view, title)
        var titleStr = title
        if (!TextUtils.isEmpty(titleStr)) {
            if (titleStr.length > 10) {
                titleStr = title.substring(0, 10) + "..."
            }
        }
        toolbar_title.text = titleStr
    }

    override fun getIndicatorHeight(): Int {
        return 3
    }

    @Nullable
    override fun getUrl(): String {
        return url
    }
}