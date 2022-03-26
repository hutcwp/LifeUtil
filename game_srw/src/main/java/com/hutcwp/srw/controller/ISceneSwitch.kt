package com.hutcwp.srw.controller

import com.hutcwp.srw.bean.RobotSprite

/**
 *  author : kevin
 *  date : 2022/3/13 2:26 PM
 *  description :
 */
interface ISceneSwitch {

    fun switchMainScene()

    fun switchBattleScene(isAuto: Boolean, leftRobot: RobotSprite, rightRobot: RobotSprite)

    //冻结控制器
    fun froze()

    //解冻控制器
    fun unfroze()
//    fun showBattleScene(leftRobot: Robot, rightRobot: Robot)
}