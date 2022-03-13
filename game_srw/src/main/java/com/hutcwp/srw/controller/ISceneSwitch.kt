package com.hutcwp.srw.controller

import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/13 2:26 PM
 *  description :
 */
interface ISceneSwitch {

    fun switchMainScene()

    fun switchBattleScene(leftRobot: Robot, rightRobot: Robot)

}