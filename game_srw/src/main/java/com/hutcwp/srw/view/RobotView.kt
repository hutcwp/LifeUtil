package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.layout_robot_info.view.*

/**
 *  author : kevin
 *  date : 2022/3/11 12:07 AM
 *  description :
 */
class RobotView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_robot_info, this)
    }

    fun updateData(robot: Robot) {
        tv_name?.text = robot.name
        tv_level?.text = "LV" + robot.level
        tv_type?.text = "" + robot.type
        tv_hp?.text = "HP: 380/380"
    }

}