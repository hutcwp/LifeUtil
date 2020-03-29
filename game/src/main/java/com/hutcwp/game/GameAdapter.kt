package com.hutcwp.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter

/**
 *
 * Created by hutcwp on 2020-03-29 14:44
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class GameAdapter(private var mGameList: List<GameBean>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_view_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGameList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mGameList[position]
        holder.tvName?.text = data.name
        val random = java.util.Random()
        val ranColor = -0x1000000 or random.nextInt(0x00ffffff)
        holder.itemView.setBackgroundColor(ranColor)
        holder.itemView.setOnClickListener {
            ARouter.getInstance().build(data.path).navigation()
        }
    }

    /**
     * ViewHolder
     */
    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val tvName: TextView? = rootView.findViewById(R.id.tvName)
    }

}