package com.hutcwp.srw.ui.view.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/4/10 6:15 PM
 *  description :
 */
class RobotInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var robot: Robot? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)

        return if (viewType == TYPE_ATTR) {
            RobotAttrHolder(itemView)
        } else {
            RobotWeaponHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        robot ?: return
        when (holder) {
            is RobotAttrHolder -> {
                holder.updateInfo(robot!!)
            }
            is RobotWeaponHolder -> {
                holder.updateInfo(robot!!)
            }
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            return TYPE_ATTR
        } else {
            TYPE_WEAPON
        }
    }

    override fun getItemCount(): Int {
        return 2
    }


    fun setData(robot: Robot) {
        this.robot = robot
        notifyDataSetChanged()
    }


    private class RobotAttrHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container = itemView.findViewById<ViewGroup>(R.id.view_container)

        fun updateInfo(robot: Robot) {
            val view = RobotAttrLayout(itemView.context).apply {
                this.updateRobotInfo(robot)
            }
            container?.removeAllViews()
            container.addView(view)
        }
    }

    private class RobotWeaponHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container = itemView.findViewById<ViewGroup>(R.id.view_container)

        fun updateInfo(robot: Robot) {
            val view = WeaponLayout(itemView.context).apply {
                this.updateRobotInfo(robot)
            }
            container?.removeAllViews()
            container.addView(view)
        }
    }


    companion object {
        const val TYPE_ATTR = 0
        const val TYPE_WEAPON = 1
    }

}
