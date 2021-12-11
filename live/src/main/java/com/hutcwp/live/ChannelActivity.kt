package com.hutcwp.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.DividerItemDecoration
import com.alibaba.android.arouter.facade.annotation.Route
import com.drakeet.multitype.MultiTypeAdapter
import com.hutcwp.live.chat.*
import com.hutcwp.live.chat.bean.Data
import com.hutcwp.live.chat.bean.Foo
import com.hutcwp.live.chat.bean.MyChatMsg
import com.hutcwp.live.chat.binder.msg.*
import com.hutcwp.live.chat.binder.other.DataType1ViewBinder
import com.hutcwp.live.chat.binder.other.DataType2ViewBinder
import com.hutcwp.live.chat.binder.other.FooViewDelegate
import com.hutcwp.live.chat.data.*
import com.hutcwp.live.chat.intepreter.ActivityNewsInterpreter
import com.hutcwp.live.chat.intepreter.GiftInterpreter
import com.hutcwp.live.chat.intepreter.NormalChatInterpreter
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

        adapter.register(GiftNewsHolder())
        adapter.register(SystemNewsHolder())
        adapter.register(NormalMsgHolder())
//        adapter.register(NormalChatHolder())
        adapter.register(ActivityNewsHolder())


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
        rv_public_screen?.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))

        initData()

    }

    private fun initData() {
        val textItem = Foo("world")

        for (i in 0..19) {
            items.add(textItem)
        }

        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy2", Data.TYPE_2))
        items.add(Data("typy2", Data.TYPE_2))

        //        items.addAll(TestUtils.getRandomMsgList(10))

        adapter.items = items

        adapter.notifyDataSetChanged()

        //        testSendMsgDelay()

        testInsertGiftData()
        testInsertSystemData()
        testInsertChatData()
        testInsertChatData()
        testInsertChatData()
        testInsertChatData()
    }

    private fun testInsertGiftData() {
        val chatMsg = TestUtils.getRandomMsg()
        chatMsg.type = MyChatMsg.TYPE_GIFT_MSG

        val span = GiftInterpreter().parse(chatMsg)
        val giftData = GiftMsgData(span)

        items.add(giftData)
        adapter.notifyItemInserted(items.size)
    }

    private fun testInsertSystemData() {
        val chatMsg = TestUtils.getRandomMsg()
        chatMsg.type = MyChatMsg.TYPE_SYSTEM_NEWS

        val systemMsgData = SystemMsgData(chatMsg.content)

        items.add(systemMsgData)
        adapter.notifyItemInserted(items.size)
    }

    private fun testInsertChatData() {
        val chatMsg = TestUtils.getRandomMsg()
        val msgData: BaseMsgData
        when (chatMsg.type) {
            MyChatMsg.TYPE_SYSTEM_NEWS -> {
                msgData = SystemMsgData(chatMsg.content)

            }
            MyChatMsg.TYPE_GIFT_MSG -> {
                val span = GiftInterpreter().parse(chatMsg)
                msgData = GiftMsgData(span)
            }
            MyChatMsg.TYPE_ACTIVITY_NEWS -> {
                val span = ActivityNewsInterpreter().parse(chatMsg)
                msgData = ActivityNewsData(span)
            }

            else -> {
                msgData = NormalMsgData(NormalChatInterpreter().parse(chatMsg))
            }
        }

        items.add(msgData)
        adapter.notifyItemInserted(items.size)
    }


    private fun testSendMsgDelay() {
        handle.postDelayed({
            items.add(TestUtils.getRandomMsg())
            adapter.notifyItemInserted(items.size)
            rv_public_screen.smoothScrollToPosition(items.size)

            testSendMsgDelay()
        }, 1500)
    }
}