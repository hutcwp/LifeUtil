package com.hutcwp.live.livebiz.ui.component.publicmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hutcwp.live.livebiz.base.util.MLog
import com.hutcwp.live.livebiz.ui.component.Component
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib.DefaultChatDecoration
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib.PublicChatAdapter
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib.PublicChatView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util.TestUtils
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.*
import com.hutcwp.livebiz.R
import hut.cwp.mvp.BindPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import me.hutcwp.util.ResolutionUtils
import me.hutcwp.util.RxUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.TimeUnit

@BindPresenter(presenter = PublicMessagePresenter::class)
class PublicMessageComponent : Component<PublicMessagePresenter?, IPublicMessageComponent?>(), IPublicMessageComponent {
    private var btnLog: Button? = null
    private var btnTest: Button? = null
    private var rvPublicMessage: PublicChatView? = null
    private var sendMsgDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.component_public_message, container, false)
        initView(view)
        initListener()
        initData()
        return view
    }

    private fun initData() {
        val adapter = PublicChatAdapter()
        adapter.register(ActivityNewsViewBinder())
        adapter.register(HeaderChatViewBinder())
        adapter.register(NormalViewBinder())
        adapter.register(SystemNewViewBinder())
        adapter.register(GiftViewBinder())
        rvPublicMessage?.let {
            it.addItemDecoration(DefaultChatDecoration(ResolutionUtils.convertDpToPixel(3f, context).toInt()))
            it.setChatAdapter(adapter)
        }
    }

    private fun initView(root: View) {
        rvPublicMessage = root.findViewById(R.id.rvPublicMessage2)
        btnTest = root.findViewById(R.id.btnTest)
        btnLog = root.findViewById(R.id.btnLog)
    }

    private fun initListener() {
        btnLog?.setOnClickListener { presenter.performLog() }
        btnTest?.setOnClickListener { presenter.performTest() }
    }

    override fun showTest() {
        startSendMsg()
    }

    override fun showLog() {
        stopSendMsg()
    }

    private fun stopSendMsg() {
        RxUtils.dispose(sendMsgDisposable)
    }

    private fun startSendMsg() {
        RxUtils.dispose(sendMsgDisposable)
        sendMsgDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val rand = (1..5).shuffled().last()
//                    val msg = when (rand) {
//                        1 -> TestUtils.getActivityMsg()
//                        2 -> TestUtils.getGiftMsg()
//                        3 -> TestUtils.getHeaderChatMsg()
//                        4 -> TestUtils.getNormalMsg()
//                        5 -> TestUtils.getSystemNewMsg()
//                        else -> TestUtils.getNormalMsg()
//                    }
                    val msg = TestUtils.getNormalMsg()
                    MLog.info(TAG, "rand is $rand")
                    EventBus.getDefault().post(msg)
                }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveMsg(msg: MyChatMsg) {
        rvPublicMessage?.addMessage(msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        RxUtils.dispose(sendMsgDisposable)
    }


    companion object {
        const val TAG = "PublicMessageComponent"
    }
}