package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.layout_battle_detail.view.*

/**
 *  author : kevin
 *  date : 2022/3/13 11:24 AM
 *  description :
 */
class BattleDetailLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_battle_detail, this)
    }

    fun setChatMsg(msg: String) {
        view_chat_msg?.setChatMsg(msg)
    }

    fun updateRobots(leftRobot: Robot, rightRobot: Robot) {
        battle_simple_detail_left?.updateData(leftRobot)
        battle_simple_detail_right?.updateData(rightRobot)
    }

}