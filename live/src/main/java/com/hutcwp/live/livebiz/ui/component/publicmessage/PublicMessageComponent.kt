package com.hutcwp.live.livebiz.ui.component.publicmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hutcwp.live.livebiz.base.util.MLog
import com.hutcwp.live.livebiz.ui.component.Component
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.DefaultChatDecoration
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.PublicChatView
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype.PublicChatAdapter2
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype.PublicChatView2
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
import java.util.concurrent.TimeUnit

@BindPresenter(presenter = PublicMessagePresenter::class)
class PublicMessageComponent : Component<PublicMessagePresenter?, IPublicMessageComponent?>(), IPublicMessageComponent {
    private var btnLog: Button? = null
    private var btnTest: Button? = null
    private var rvPublicMessage: PublicChatView? = null
    private var rvPublicMessage2: PublicChatView2? = null

    private var sendMsgDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.component_public_message, container, false)
        initView(view)
        initListener()
        initData()
        return view
    }

    private fun initData() {
//        val adapter = PublicChatAdapter()
//        rvPublicMessage?.let {
//            it.addItemDecoration(DefaultChatDecoration(ResolutionUtils.convertDpToPixel(3f, context).toInt()))
//            it.setChatAdapter(adapter)
//                    .addMsgs(TestUtils.getRandomMsgList(10))
//        }

        val adapter2 = PublicChatAdapter2()
        adapter2.register(ActivityNewsViewBinder())
        adapter2.register(HeaderChatViewBinder())
        adapter2.register(NormalViewBinder())
        adapter2.register(SystemNewViewBinder())
        adapter2.register(GiftViewBinder())
        rvPublicMessage2?.let {
            it.addItemDecoration(DefaultChatDecoration(ResolutionUtils.convertDpToPixel(3f, context).toInt()))
            it.setChatAdapter(adapter2)
//                    .addMsgs(TestUtils.getRandomMsgList(10))
        }
    }

    private fun initView(root: View) {
//        rvPublicMessage = root.findViewById(R.id.rvPublicMessage)
        rvPublicMessage2 = root.findViewById(R.id.rvPublicMessage2)
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
        if (sendMsgDisposable == null || sendMsgDisposable!!.isDisposed) {
            sendMsgDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {

                        val rand = (1..5).shuffled().last()
                        when (rand) {
                            1 -> rvPublicMessage2?.addMsg(TestUtils.getActivityMsg())
                            2 -> rvPublicMessage2?.addMsg(TestUtils.getGiftMsg())
                            3 -> rvPublicMessage2?.addMsg(TestUtils.getHeaderChatMsg())
                            4 -> rvPublicMessage2?.addMsg(TestUtils.getNormalMsg())
                            5 -> rvPublicMessage2?.addMsg(TestUtils.getSystemNewMsg())
                            else -> rvPublicMessage2?.addMsg(TestUtils.getNormalMsg())
                        }

                        MLog.info(TAG, "rand is $rand")
//                        EventBus.getDefault().post(msg)
                    }
        }
    }


    companion object {
        const val TAG = "PublicMessageComponent"
    }
}