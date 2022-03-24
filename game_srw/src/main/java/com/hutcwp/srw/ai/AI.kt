package com.hutcwp.srw.ai

import com.hutcwp.srw.GameMain
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotSprite
import kotlin.random.Random

/**
 *  author : kevin
 *  date : 2022/3/13 2:23 AM
 *  description :
 */
class AI {

    fun compute(robotSprite: List<RobotSprite>) {
        robotSprite.forEach {
            nearPos(it)?.let { pos ->
                GameMain.updateSpritePos(it, pos)
            }
        }

        GameMain.takeTurn()
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
        while (pos != null && GameMain.hasRobot(pos)) {
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

}