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
class BattleController(val battleScene: BattleScene, val leftRobotSprite: RobotSprite, val rightRobotSprite: RobotSprite) {


    private var firstAction = true //是否先手

    private var battleStepQueue: Queue<Runnable> = LinkedList<Runnable>()

    fun initBattle() {
        this.firstAction = true

        battleScene.updateBattleInfo(leftRobotSprite, rightRobotSprite)
        playBGM()
        startBattle()
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

        battleScene.showChatMsg("开始战斗...")

        createBattleStep(attacker, defender)
    }

    fun startPlayBattle() {
        if (battleStepQueue.isNotEmpty()) {
            battleStepQueue.poll().run()
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
            battleScene.showChatMsg("来啊，看我的！")
            battleScene.showAttackAnim(attacker, attacker.useWeapon()!!)
            defender.beAttackByWeapon(attacker.robot, attacker.useWeapon()!!)

            if (!defender.isAlive()) {
                this.finish()
            } else {

                battleStepQueue.add(Runnable {
                    battleScene.showChatMsg("接招！")
                    battleScene.showAttackAnim(defender, defender.useWeapon()!!)

                    attacker.beAttackByWeapon(defender.robot, defender.useWeapon()!!)
                })
            }
        })
    }

    private fun playBGM() {
        val path = "audio/music2/82.mp3"
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).playBackgroundMusic(path, true)
    }

    private fun stopBGM() {
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext()).stopBackgroundMusic()
    }

    fun finish() {
        stopBGM()
        battleScene.onFinish()
    }

}