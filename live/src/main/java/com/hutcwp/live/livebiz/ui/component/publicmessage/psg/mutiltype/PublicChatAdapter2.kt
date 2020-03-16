package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.mutiltype

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.publicmessage.lib.BaseChatMsg
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.MyChatMsg
import me.drakeet.multitype.ItemViewBinder

/**
 *
 * Created by hutcwp on 2020-03-13 18:08
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicChatAdapter2 : BaseChatAdapter2<BaseChatMsg>() {

    private val mDatas: MutableList<BaseChatMsg> = mutableListOf()

    override fun addItem(chatMsg: BaseChatMsg) {
        mDatas.add(chatMsg)
        items = mDatas.toList()
        notifyItemChanged(itemCount)
    }

    override fun addItemList(dataList: List<BaseChatMsg>) {
        mDatas.addAll(dataList.toList())
        items = mDatas.toList()
        notifyDataSetChanged()
    }

    override fun removeItems(startPos: Int, endPos: Int) {
    }

//    override fun getItemViewType(position: Int): Int {
//        val msg = mDatas[position]
//        if (msg is MyChatMsg) {
//            return msg.type
//        }
//
//        return MyChatMsg.TYPE_NORMAL_TEXT
//    }

}