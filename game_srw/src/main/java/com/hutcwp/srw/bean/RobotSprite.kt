package com.hutcwp.srw.bean

import android.content.Context
import com.hutcwp.srw.BaseUI
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/6 6:14 PM
 *  description :
 */
class RobotSprite(val context: Context, val robot: Robot, val params: RobotParams) : BaseSprite(BaseUI(context), params.pos, params.resId) {

    fun beAttack(robot: Robot) {
        val attack = robot.attribute.attack
        this.robot.attribute.hp = this.robot.attribute.hp - attack
        if (this.robot.attribute.hp <= 0) {
            showDestroyAnim()
        }
    }

    private fun showDestroyAnim() {

    }

    fun updateMoveAvailable(canMove: Boolean) {
        if (canMove) {
            view.alpha = 1f
        } else {
            view.alpha = 0.5f
        }
    }

}