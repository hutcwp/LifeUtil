package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.lib

/**
 *
 * Created by hutcwp on 2020-03-13 18:08
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PublicChatAdapter : BaseChatAdapter<BaseChatMsg>() {

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

}