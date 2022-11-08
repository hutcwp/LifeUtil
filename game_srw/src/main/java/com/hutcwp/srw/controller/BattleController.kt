package com.hutcwp.srw.controller

import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.compute.BattleCenter
import com.hutcwp.srw.music.BackgroundMusic
import com.hutcwp.srw.scene.BattleScene
import me.hutcwp.BasicConfig

/**
 *  author : kevin
 *  date : 2022/3/20 12:12 AM
 *  description :
 */
class BattleController(private val battleScene: BattleScene,
                       private val leftRobotSprite: RobotSprite,
                       private val rightRobotSprite: RobotSprite) : IGameController {


    private var isUserCmd = true //是否用户操作
    private var taskController = TaskController()


    fun initBattle(isAuto: Boolean) {
        this.isUserCmd = isAuto
        battleScene.showChatMsg("开始战斗...")
        battleScene.showNextTip(true)

        if (isUserCmd) {
            createBattleStep(rightRobotSprite, leftRobotSprite)
        } else {
            createBattleStep(leftRobotSprite, rightRobotSprite)
        }
        playBGM(rightRobotSprite.robot.operator.bgmPath)
    }

    /**
     * 播放战斗步骤
     */
    private fun playBattle() {
        if (taskController.isEmpty()) {
            battleScene.switchMainScene() //触发切换页面
            if (isUserCmd.not()) {
                GameMain.finishBattleTaskAI()
            }
            return
        }

        taskController.runTask()
    }

    /**
     * 生成战斗步骤
     */
    private fun createBattleStep(attacker: RobotSprite, defender: RobotSprite) {
        val defendTask = object : BattleTask() {
            override fun start() {
                super.start()
                battleScene.showChatMsg("接招！", defender.robot.operator)
                battleScene.showAttackAnim(defender, defender.useWeapon()!!, this)
            }

            override fun end() {
                super.end()
                attacker.beAttackByWeapon(defender, defender.useWeapon()!!)
                battleScene.updateRobotInfo(leftRobotSprite, rightRobotSprite)
                val attackValue = BattleCenter.attackValue(defender, attacker, defender.useWeapon()!!)
                battleScene.showChatMsg("${attacker.robot.attribute.name} 受到 " +
                        "$attackValue 伤害", attacker.robot.operator)

                if (!attacker.isAlive()) {
                    battleScene.showChatMsg("${defender.robot.attribute.name} 倒下了～")
                }
            }
        }

        val attackTask = object : BattleTask() {
            override fun start() {
                super.start()
                battleScene.showChatMsg("来啊，看我的！", attacker.robot.operator)
                battleScene.showAttackAnim(attacker, attacker.useWeapon()!!, this)
            }

            override fun end() {
                super.end()
                defender.beAttackByWeapon(attacker, attacker.useWeapon()!!)

                battleScene.updateRobotInfo(leftRobotSprite, rightRobotSprite)

                val attackValue = BattleCenter.attackValue(attacker, defender, attacker.useWeapon()!!)

                battleScene.showChatMsg("${defender.robot.attribute.name} 受到 " +
                        "$attackValue 伤害", defender.robot.operator)

                if (!defender.isAlive()) {
                    battleScene.showChatMsg("${defender.robot.attribute.name} 倒下了～")
                } else {
                    addBattleTask(defendTask)
                }
            }
        }

        addBattleTask(attackTask)
    }


    /**
     * 添加战斗任务
     */
    private fun addBattleTask(battleTask: ITask) {
        val task = taskController.createTask(battleTask)
        taskController.addTask(task)
    }

    private fun playBGM(path: String?) {
        path ?: return
        BackgroundMusic.getInstance(BasicConfig.getApplicationContext()).playBackgroundMusic(path, true)
    }

    private fun stopBGM() {
        BackgroundMusic.getInstance(BasicConfig.getApplicationContext()).stopBackgroundMusic()
    }

    //回收工作
    fun recycle() {
        stopBGM()
        this.isUserCmd = false
    }

//    =====================操作控制器==============

    override fun up() {
    }

    override fun down() {
    }

    override fun left() {
    }

    override fun right() {
    }

    override fun ok() {
        playBattle()
    }

    override fun cancel() {
    }


    /**
     * 封装用来战斗的任务；
     * 处理了控制开关，继续提示等逻辑
     * 业务只需要关心真是的战斗逻辑
     */
    open inner class BattleTask : ITask {
        override fun start() {
            GameMain.gameControllerEnable(false)
            battleScene.showNextTip(false)
        }

        override fun end() {
            taskController.taskFinish()
            GameMain.gameControllerEnable(true)
            battleScene.showNextTip(true)
        }
    }

}