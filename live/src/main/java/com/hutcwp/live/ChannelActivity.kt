package com.hutcwp.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.alibaba.android.arouter.facade.annotation.Route
import com.drakeet.multitype.MultiTypeAdapter
import com.hutcwp.live.chat.*
import com.hutcwp.live.chat.bean.Data
import com.hutcwp.live.chat.bean.Foo
import com.hutcwp.live.chat.bean.MyChatMsg
import com.hutcwp.live.chat.binder.chat.*
import com.hutcwp.live.chat.binder.other.DataType1ViewBinder
import com.hutcwp.live.chat.binder.other.DataType2ViewBinder
import com.hutcwp.live.chat.binder.other.FooViewDelegate
import kotlinx.android.synthetic.main.live_activity_channel.*
import me.hutcwp.constants.RoutePath


@Route(path = RoutePath.LIVE_CHANNEL, name = "直播间")
class ChannelActivity : AppCompatActivity() {

    private val handle = Handler(Looper.myLooper()!!)

    private val adapter = MultiTypeAdapter()
    private val items = ArrayList<Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_activity_channel)

        initRV()
    }

    private fun initRV() {
        adapter.register(FooViewDelegate())

        adapter.register(MyChatMsg::class).to(
                ActivityNewsHolder(),
                GiftNewsHolder(),
                HeaderChatHolder(),
                NormalChatHolder(),
                SystemNewsHolder()
        ).withKotlinClassLinker { _, data ->
            when (data.type) {
                MyChatMsg.TYPE_ACTIVITY_NEWS -> ActivityNewsHolder::class
                MyChatMsg.TYPE_GIFT_MSG -> GiftNewsHolder::class
                MyChatMsg.TYPE_SYSTEM_NEWS -> SystemNewsHolder::class
                MyChatMsg.TYPE_NORMAL_TEXT -> NormalChatHolder::class
                else -> SystemNewsHolder::class
            }
        }

        adapter.register(Data::class).to(
                DataType1ViewBinder(),
                DataType2ViewBinder()
        ).withKotlinClassLinker { _, data ->
            when (data.type) {
                Data.TYPE_2 -> DataType2ViewBinder::class
                else -> DataType1ViewBinder::class
            }
        }

        rv_public_screen?.adapter = adapter

        val textItem = Foo("world")

        for (i in 0..19) {
            items.add(textItem)
        }

        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy2", Data.TYPE_2))
        items.add(Data("typy2", Data.TYPE_2))

        items.addAll(TestUtils.getRandomMsgList(10))

        adapter.items = items

        adapter.notifyDataSetChanged()

        testSendMsgDelay()
    }

    private fun testSendMsgDelay() {
        handle.postDelayed({
            items.add(TestUtils.getRandomMsg())
            adapter.notifyItemInserted(items.size)
            rv_public_screen.smoothScrollToPosition(items.size)

            testSendMsgDelay()
        }, 500)
    }
}