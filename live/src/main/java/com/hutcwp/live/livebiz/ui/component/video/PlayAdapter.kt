package com.hutcwp.live.livebiz.ui.component.video

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.live.livebiz.ui.component.bean.Playable
import com.hutcwp.livebiz.R


/**
 *
 * Created by hutcwp on 2020-03-08 00:56
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PlayAdapter(private val context: Context, var playableList: MutableList<Playable>) : RecyclerView.Adapter<PlayAdapter.PlayHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(l: OnItemClickListener) {
        this.listener = l
    }

    //取得data数据
    val data: List<Playable>
        get() = playableList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayHolder {
        val view = inflater.inflate(R.layout.item_music, parent, false)
        return PlayHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PlayHolder, position: Int) {
        val item = playableList[position]
        holder.playName?.text = item.name
        holder.rootView.setOnClickListener {
            listener?.onClick(item)
        }
    }

    /**
     * 设置新内容
     * @param data 新内容
     */
    fun setNewData(data: MutableList<Playable>) {
        playableList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (playableList == null) 0 else playableList!!.size
    }

    //添加data数据
    fun addData(position: Int, data: List<Playable>) {
        playableList?.addAll(position, data)
        notifyItemRangeInserted(position, data.size)
    }

    interface OnItemClickListener {
        fun onClick(playable: Playable)
    }

    inner class PlayHolder(var rootView: View) : RecyclerView.ViewHolder(rootView) {
        var playName: TextView? = null

        init {
            playName = rootView.findViewById<View>(R.id.name) as TextView
        }


    }
}
