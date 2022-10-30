package com.hutcwp.srw.factory

import android.content.Context
import com.hutcwp.srw.bean.RobotParams
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/6 6:19 PM
 *  description :
 */
class RobotFactory() : IRobotCreate {


    override fun createRobot(context: Context, robot: Robot, robotParams: RobotParams): RobotSprite {

        return RobotSprite(context, robot, robotParams)
    }
}