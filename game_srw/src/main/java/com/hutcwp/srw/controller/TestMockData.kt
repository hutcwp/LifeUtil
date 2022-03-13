package com.hutcwp.srw.controller

import android.content.Context
import com.hutcwp.srw.GameMain
import com.hutcwp.srw.R
import com.hutcwp.srw.RobotsFactoryService
import com.hutcwp.srw.bean.*
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
            it.add(createRobot(context,
                    Robot(1, 1, 1, "刚达R", 4, 100, 400),
                    R.drawable.robot_1, 4, 2))
            it.add(createRobot(context,
                    Robot(0, 1, 1, "扎古", 4, 80, 340),
                    R.drawable.icon_enemy_33, 6, 6))
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