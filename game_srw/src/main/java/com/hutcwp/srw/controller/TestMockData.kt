package com.hutcwp.srw.controller

import android.content.Context
import com.hutcwp.srw.BgmConstants
import com.hutcwp.srw.R
import com.hutcwp.srw.RobotsFactoryService
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.info.Operator
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.info.battle.Weapon

/**
 *  author : kevin
 *  date : 2022/3/9 10:59 PM
 *  description :
 */
class TestMockData {

    fun createRobot(context: Context, robot: Robot, pos: Pos): RobotSprite {
        val params = RobotParams.Builder().apply {
            this.pos = Pos(pos.x, pos.y)
            this.resId = robot.attribute.iconId
        }.build()

        val robot = RobotsFactoryService.friendRobotFactory.createRobot(context, robot, params)
        return robot
    }

    fun createMapList(context: Context): MutableList<MapSprite> {
        val mapSpriteList = mutableListOf<MapSprite>()
        for (x in 0..GameController.MAP_WIDTH_SIZE) {
            for (y in 0..GameController.MAP_HEIGHT_SIZE) {
                mapSpriteList.add(MapSprite(context, R.drawable.a_01, Pos(x, y)))
            }
        }

        return mapSpriteList
    }

    fun createRobotList(context: Context): MutableList<RobotSprite> {
        val robotSpriteList = mutableListOf<RobotSprite>()
        robotSpriteList.let {
            it.add(createRobot(
                    context,
                    Robot(
                            Robot.Attributes(1, 1, R.drawable.img_blue_gangda, R.drawable.robot_1, 1, "刚达R", 4, 100, 400),
                            listOf(Weapon(80, "光剑")),
                            Operator("大卫", R.drawable.peple_46, BgmConstants.DaWei)
                    ), Pos(3, 5)
            )
            )
            it.add(createRobot(context,
                    Robot(
                            Robot.Attributes(0, 1, R.drawable.img_enemy_kulou, R.drawable.icon_enemy_33, 1, "扎古", 4, 80, 340),
                            listOf(Weapon(120, "光剑")),
                            Operator("AI", R.drawable.peple_3, BgmConstants.DaWei)), Pos(3, 6)
            )
            )
        }

        return robotSpriteList
    }

    fun createSelectSprite(context: Context): SelectSprite {
        val params = RobotParams.Builder().apply {
            this.pos = Pos(2, 3)
            this.resId = R.drawable.icon_select
        }.build()
        return SelectSprite(context, params)
    }

}