package com.hutcwp.srw.ai

import android.os.Handler
import android.os.Looper
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.controller.ITask
import com.hutcwp.srw.controller.TaskController
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotSprite

/**
 *  author : kevin
 *  date : 2022/3/13 2:23 AM
 *  description :
 */
class AI {

    var enemyActionController: TaskController = TaskController()

    var handle: Handler = Handler(Looper.getMainLooper())


    fun compute(robotSprite: List<RobotSprite>) {
        robotSprite.forEach {
            addEnemyTask(it)
        }

        enemyActionController.runTask()
//        GameMain.takeTurn()
    }

    fun addEnemyTask(robotSprite: RobotSprite) {
        val moveTask = enemyActionController.createTask(object : EnemyActionTask("moveTask") {
            override fun start() {
                //move
                nearPos(robotSprite)?.let { pos ->
                    GameMain.updateSpritePos(robotSprite, pos)
                }

                handle?.postDelayed({
                    this.end()//需要主动调用结束，用于异步的情况下触发回调时机

                },2000)
            }

            override fun end() {
                super.end()
            }
        })

        val battleTask = enemyActionController.createTask(object : EnemyActionTask("battleTask") {
            override fun start() {
                //attack
                findAttackRobotSprite(robotSprite)?.let { blueRobot ->
                    GameMain.showBattleAI(robotSprite, blueRobot)
                }
            }

            override fun end() {
                super.end()
            }
        })

        enemyActionController.addTask(moveTask)
        enemyActionController.addTask(battleTask)
    }

    /**
     * 寻找攻击目标
     */
    private fun findAttackRobotSprite(robotSprite: RobotSprite): RobotSprite? {
        return GameMain.blueRobotSpriteList().firstOrNull()
    }


    /**
     * 最近的地方机器人位置
     */
    fun nearPos(robotSprite: RobotSprite): Pos? {
        var moreLastRobotSprite: RobotSprite? = null //离他最近的机器人
        var dx = Int.MAX_VALUE
        GameMain.blueRobotSpriteList().forEach {
            val d = duration(it.pos, robotSprite.pos)
            if (d < dx) {
                moreLastRobotSprite = it
                dx = d
            }
        }

        var pos = moreLastRobotSprite?.pos
        var rang = 1
        var count = 0
        while (pos != null && GameMain.existRobot(pos)) {
            val x = (-rang..rang).random()
            val y = (-rang..rang).random()
            if (count > 8) {
                rang += 1
            }
            count += 1
            pos = Pos(pos.x + x, pos.y + y)
        }

        return pos
    }

    fun duration(pos1: Pos, pos2: Pos): Int {
        return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y)
    }

    /**
     * 外部帮忙调用下战斗任务结束
     */
    fun finishBattleTask() {
        enemyActionController.taskFinish()
        if (enemyActionController.isEmpty()) {
            GameMain.takeTurn()
        } else {
            enemyActionController.runTask()
        }
    }


    /**
     * 敌人行动任务
     */
    open inner class EnemyActionTask(val name:String) : ITask {

        override fun start() {

        }

        override fun end() {
            enemyActionController.taskFinish()
            enemyActionController.runTask()
        }
    }
}