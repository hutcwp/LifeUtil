package com.hutcwp.srw.controller

import com.hutcwp.srw.BattleScene
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.music.BackgroundMusic
import me.hutcwp.BaseConfig
import java.util.*

/**
 *  author : kevin
 *  date : 2022/3/20 12:12 AM
 *  description :
 */
class BattleController(private val battleScene: BattleScene,
                       private val leftRobotSprite: RobotSprite,
                       private val rightRobotSprite: RobotSprite) {


    private var isAuto = true //是否先手

    private var battleStepQueue: Queue<Runnable> = LinkedList<Runnable>()

    fun initBattle(isAuto: Boolean) {
        this.isAuto = isAuto

        battleScene.updateBattleInfo(leftRobotSprite, rightRobotSprite)
        playBGM(rightRobotSprite.robot.operator.bgmPath)
        initBattleData()
    }

    /**
     * 计算战斗数据
     */
    private fun initBattleData() {
        battleScene.showChatMsg("开始战斗...")

        if (isAuto) {
            createBattleStep(rightRobotSprite, leftRobotSprite)
        } else {
            createBattleStep(leftRobotSprite, rightRobotSprite)
        }
    }

    /**
     * 播放战斗步骤
     */
    fun playBattle() {
        if (battleStepQueue.isNotEmpty()) {
            battleStepQueue.poll()?.run()
        } else {
            finish()
        }
    }

    /**
     * 生成战斗步骤
     */
    private fun createBattleStep(attacker: RobotSprite, defender: RobotSprite) {
        battleStepQueue.clear()

        battleStepQueue.add(Runnable {
            battleScene.showChatMsg("来啊，看我的！", attacker.robot.operator)
            battleScene.showAttackAnim(attacker, attacker.useWeapon()!!)


            battleStepQueue.add(Runnable {
                defender.beAttackByWeapon(attacker, attacker.useWeapon()!!)
                battleScene.updateRobotInfo(leftRobotSprite, rightRobotSprite)
                battleScene.showChatMsg("${defender.robot.attribute.name} 受到 " +
                        "${attacker.useWeapon()!!.attackValue} 伤害", defender.robot.operator)


                if (!defender.isAlive()) {

                    battleScene.showChatMsg("${defender.robot.attribute.name} 倒下了～")
                } else {

                    battleStepQueue.add(Runnable {
                        battleScene.showChatMsg("接招！", defender.robot.operator)
                        battleScene.showAttackAnim(defender, defender.useWeapon()!!)

                        attacker.beAttackByWeapon(defender, defender.useWeapon()!!)

                        battleStepQueue.offer(Runnable {
                            battleScene.updateRobotInfo(leftRobotSprite, rightRobotSprite)
                            battleScene.showChatMsg("${attacker.robot.attribute.name} 受到 " +
                                    "${defender.useWeapon()!!.attackValue} 伤害", attacker.robot.operator)

                            if(!attacker.isAlive()){
                                battleScene.showChatMsg("${defender.robot.attribute.name} 倒下了～")
                            }

                        })
                    })
                }

            })


        })
    }

    private fun playBGM(path: String?) {
        path ?: return
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).playBackgroundMusic(path, true)
    }

    private fun stopBGM() {
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).stopBackgroundMusic()
    }

    private fun finish() {
        stopBGM()
        battleScene.onFinish()
    }

}