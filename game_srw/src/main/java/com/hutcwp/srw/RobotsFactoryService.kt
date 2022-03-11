package com.hutcwp.srw

import com.hutcwp.srw.factory.RobotFactory

/**
 *  author : kevin
 *  date : 2022/3/6 6:22 PM
 *  description :
 */
object RobotsFactoryService {

    val friendRobotFactory: RobotFactory by lazy { RobotFactory() }
    val enemyRobotFactory: RobotFactory by lazy { RobotFactory() }

}