package com.hutcwp.srw.bean

import android.content.Context
import com.hutcwp.srw.BaseUI
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.RobotUI
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.info.battle.Weapon

/**
 *  author : kevin
 *  date : 2022/3/6 6:14 PM
 *  description :
 */
class RobotSprite(val context: Context, val robot: Robot, val params: RobotParams) : BaseSprite(RobotUI(context), params.pos, params.resId) {


    var canAction: Boolean = true //是否还可以行动

    fun beAttack(robot: Robot) {
        val attack = robot.attribute.attack
        this.robot.attribute.hp = this.robot.attribute.hp - attack
        if (this.robot.attribute.hp <= 0) {
            showDestroyAnim()
        }
    }

    private fun showDestroyAnim() {

    }

    fun useWeapon(): Weapon? {
        return robot.weapons[0]
    }

    /**
     * 被什么武器攻击了
     */
    fun beAttackByWeapon(robot: Robot, weapon: Weapon) {
        val attack = weapon.attackValue
        this.robot.attribute.hp = this.robot.attribute.hp - attack
        if (this.robot.attribute.hp <= 0) {
            this.robot.attribute.hp = 0
            showDestroyAnim()
        }
    }

    fun isAlive(): Boolean {
        return this.robot.attribute.hp > 0
    }

    fun beAttacked(hitValue: Int) {
        robot.attribute.hp -= hitValue
        if (robot.attribute.hp <= 0) {
            GameMain.destroyRobot(this)
        }
    }

    fun updateMoveAvailable(canMove: Boolean) {
        if (canMove) {
            view.alpha = 1f
        } else {
            view.alpha = 0.3f
        }
    }

    fun destroy() {
        (view as RobotUI).showDestroyAnim()
    }

    fun updateAction(canAction: Boolean) {
        this.canAction = canAction

        updateMoveAvailable(canAction)
    }

    fun showAttackAnim() {

    }


}