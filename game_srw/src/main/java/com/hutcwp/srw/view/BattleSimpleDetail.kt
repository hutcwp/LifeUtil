package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.view_battle_deatil_simple.view.*

/**
 *  author : kevin
 *  date : 2022/3/13 11:26 AM
 *  description : 战斗介绍视图
 */
class BattleSimpleDetail @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    init {
        View.inflate(context, R.layout.view_battle_deatil_simple, this)
    }


    fun updateData(robot: Robot) {
        tv_hp?.text = "Hp: ${robot.attribute.hp} / ${robot.attribute.maxHp}"
    }

}