package com.hutcwp.srw

import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/12 11:48 PM
 *  description :
 */
object BattleUtil {

    fun attack(attacker: RobotSprite, robot: RobotSprite) {
        robot.beAttack(attacker.robot)
    }

}