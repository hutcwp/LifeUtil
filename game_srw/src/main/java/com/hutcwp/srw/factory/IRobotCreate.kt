package com.hutcwp.srw.factory

import android.content.Context
import com.hutcwp.srw.bean.RobotParams
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/6 3:18 PM
 *  description :
 */
interface IRobotCreate {

    fun createRobot(context: Context, robot: Robot, robotParams: RobotParams): RobotSprite

}