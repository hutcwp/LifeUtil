package com.hutcwp.srw.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.srw.R
import com.hutcwp.srw.info.Robot
import kotlinx.android.synthetic.main.view_battle_scene.view.*

/**
 *  author : kevin
 *  date : 2022/3/13 11:16 AM
 *  description :
 */
class BattleSceneLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_battle_scene, this)
    }


    fun updateRobots(left: Robot, right: Robot) {
        iv_left?.setImageResource(left.attribute.imgId)
        iv_right?.setImageResource(right.attribute.imgId)
    }

}