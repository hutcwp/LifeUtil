package com.hutcwp.srw.controller

import android.content.Context
import com.hutcwp.srw.RobotsFactoryService
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotParams
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/9 10:59 PM
 *  description :
 */
class TestMockData {

    fun createRobot(context: Context, robot: Robot, resId: Int, posX: Int, posY: Int): RobotSprite {
        val params = RobotParams.Builder().apply {
            this.pos = Pos(posX, posY)
            this.resId = resId
        }.build()

        val robot = RobotsFactoryService.friendRobotFactory.createRobot(context, robot, params)
        return robot
    }

}