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

        robotSprite?.forEach {
            val x = (-1..1).random()
            val y = (-1..1).random()
            val newPos = Pos(it.pos.x + x, it.pos.y + y)
            GameMain.updateSpritePos(it, newPos)
        }

        GameMain.takeTurn()
    }

}