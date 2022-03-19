package com.hutcwp.srw.controller

import com.hutcwp.srw.BattleScene
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.battle.BattleStep
import java.util.*

/**
 *  author : kevin
 *  date : 2022/3/20 12:12 AM
 *  description :
 */
class BattleController(val battleScene: BattleScene, val leftRobotSprite: RobotSprite, val rightRobotSprite: RobotSprite) {


    private var firstAction = true //是否先手

    private var battleStepQueue: Queue<Runnable> = LinkedList<Runnable>()

    fun initBattle() {
        this.firstAction = true

        battleScene.updateBattleInfo(leftRobotSprite, rightRobotSprite)

    }


    fun startBattle() {
        var attacker: RobotSprite? = null
        var defender: RobotSprite? = null
        if (firstAction) {
            attacker = rightRobotSprite
            defender = leftRobotSprite
        } else {
            attacker = leftRobotSprite
            defender = rightRobotSprite
        }

        realBattleStep(attacker, defender)
    }

    private fun realBattleStep(attacker: RobotSprite, defender: RobotSprite) {
        battleScene.showAttackAnim(attacker,attacker.useWeapon()!!)
        Thread.sleep(2000)
        defender.beAttackByWeapon(attacker.robot, attacker.useWeapon()!!)

        if (!defender.isAlive()) {
            this.finish()
        } else {

            battleScene.showAttackAnim(defender,defender.useWeapon()!!)
            Thread.sleep(2000)

            attacker.beAttackByWeapon(defender.robot,defender.useWeapon()!!)
        }

    }

//    private fun runAllTask() {
//        while (battleStepQueue.isNotEmpty()) {
//            battleStepQueue.poll()?.run()
//        }
//    }

    /**
     * 生成战斗步骤
     */
    private fun createBattleStep() {
        var attacker: RobotSprite? = null
        var defender: RobotSprite? = null
        if (firstAction) {
            attacker = rightRobotSprite
            defender = leftRobotSprite
        } else {
            attacker = leftRobotSprite
            defender = rightRobotSprite
        }

//        battleStepQueue.add(attack(attacker, defender))
//        if (GameMain.isAlive(defender.robot)) {
//            battleStepQueue.add(attack(defender, attacker))
//        }
    }


    fun finish() {

    }

}