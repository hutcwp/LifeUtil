package com.hutcwp.live.livebiz.ui.component.danmu

import android.graphics.Color
import android.os.Bundle
import android.text.SpannedString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.annotations.mvp.DelegateBind
import com.example.presenter.core.MvpFragment
import com.hutcwp.live.livebiz.base.util.BasicConfig
import com.hutcwp.live.livebiz.ui.component.danmu.kinds.ColorDanmaku
import com.hutcwp.live.livebiz.ui.component.danmu.kinds.ColorTextCacheStuffer
import com.hutcwp.live.livebiz.ui.component.danmu.kinds.DanmakuStufferProxy
import com.hutcwp.live.livebiz.ui.component.emoji.RichTextManager
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import com.hutcwp.livebiz.R

import master.flame.danmaku.controller.DrawHandler
import master.flame.danmaku.danmaku.model.*
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser
import master.flame.danmaku.ui.widget.DanmakuView
import me.hutcwp.util.ResolutionUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@DelegateBind(presenter = DanmuPresenter::class)
class DanmuComponent : MvpFragment<DanmuPresenter?, IDanmuComponent?>(), IDanmuComponent {
    private var showDanmaku = false
    private var danmakuView: DanmakuView? = null
    private var danmakuContext: DanmakuContext? = null
    private val parser: BaseDanmakuParser = object : BaseDanmakuParser() {
        override fun parse(): IDanmakus {
            return Danmakus()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.component_danmu, container, false)
        initView(view)
        return view
    }

    private fun initView(rootView: View) {
        danmakuView = rootView.findViewById(R.id.danmaku_view)
        danmakuView?.enableDanmakuDrawingCache(true)
        danmakuView?.setCallback(object : DrawHandler.Callback {
            override fun prepared() {
                showDanmaku = true
                danmakuView?.start()
            }

            override fun updateTimer(timer: DanmakuTimer) {}
            override fun danmakuShown(danmaku: BaseDanmaku) {}
            override fun drawingFinished() {}
        })
        danmakuContext = DanmakuContext.create()
        danmakuContext?.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_NONE)
                ?.setDuplicateMergingEnabled(false)
                ?.setScaleTextSize(1f) //弹幕缩放倍数l
                ?.setScrollSpeedFactor(1.8f) //弹幕速度
                ?.setDanmakuMargin(30)
                ?.setCacheStuffer(
                        ColorTextCacheStuffer(),
                        DanmakuStufferProxy(context!!, danmakuView!!)
                )
        danmakuView?.prepare(parser, danmakuContext)
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private fun addColorDanmaku(content: String, withBorder: Boolean) {
        val danmaku = danmakuContext!!.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL)
        danmaku.text = content
        danmaku.padding = 5
        danmaku.textSize = ResolutionUtils.convertDpToPixel(16f, context)
        danmaku.textColor = Color.WHITE
        danmaku.time = danmakuView!!.currentTime
        if (withBorder) {
            danmaku.borderColor = Color.GREEN
        }
        danmakuView!!.addDanmaku(danmaku)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveMsg(msg: MyChatMsg) {
        addColorDanmaku(msg)
    }

    private fun addColorDanmaku(msg: MyChatMsg) {
        val danmaku = ColorDanmaku()
        danmaku.msg = msg
        val content = SpannedString(msg.content)
        val features: MutableList<RichTextManager.Feature> = ArrayList()
        features.add(RichTextManager.Feature.EMOTICON)
        RichTextManager.getInstance().getSpannableString(BasicConfig.getInstance().appContext, content, features)

        danmaku.text = content
        danmaku.name = msg.sendUserName
        danmaku.level = 1
        danmaku.nameColor = Color.RED
        danmaku.messageColor = Color.GREEN
        danmaku.background = Color.TRANSPARENT
        danmaku.headerUrl = "https://img.mttmp.com/images/2018/02/14/01/1741_3mjWSt_h3vwnu5.jpg"

        danmaku.padding = 5
        danmaku.textSize = ResolutionUtils.convertDpToPixel(16f, context)
        danmaku.textColor = Color.WHITE
        danmaku.time = danmakuView?.currentTime ?: 0
        danmaku.duration = Duration(7200)
        danmakuView?.addDanmaku(danmaku)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        if (danmakuView != null && danmakuView!!.isPrepared) {
            danmakuView!!.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (danmakuView != null && danmakuView!!.isPrepared && danmakuView!!.isPaused) {
            danmakuView!!.resume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        showDanmaku = false
        if (danmakuView != null) {
            danmakuView!!.release()
            danmakuView = null
        }
    }
}