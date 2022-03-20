package com.hutcwp.srw.constants

import android.content.Context
import com.hutcwp.srw.RobotsFactoryService
import com.hutcwp.srw.bean.Pos
import com.hutcwp.srw.bean.RobotParams
import com.hutcwp.srw.bean.RobotSprite
import com.hutcwp.srw.info.Robot
import me.hutcwp.BaseConfig

/**
 * author : kevin
 * date : 2022/3/20 11:54 PM
 * description :
 */
object LevelConfig {

    fun createRobot(context: Context, robot: Robot, pos: Pos): RobotSprite {
        val params = RobotParams.Builder().apply {
            this.pos = Pos(pos.x, pos.y)
            this.resId = robot.attribute.iconId
        }.build()

        val robot = RobotsFactoryService.friendRobotFactory.createRobot(context, robot, params)
        return robot
    }

    fun createRobotSprite(robot: Robot, pos: Pos): RobotSprite {
        return createRobot(
                BaseConfig.getApplicationContext(),
                robot, pos
        )
    }


    object No1 {

        val mapList = MapConstants.N0_1

        val blueRobotList = listOf(createRobotSprite(Robot(
                RobotConstants.GangDa,
                listOf(WeaponConstant.Daodan),
                PeopleConstants.DaWei),
                Pos(12, 14)))


        val redRobotList = listOf(createRobotSprite(Robot(
                RobotConstants.ZhaGu,
                listOf(WeaponConstant.Daodan),
                PeopleConstants.AI),
                Pos(10, 14))
        )
    }
}