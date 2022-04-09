package com.hutcwp.srw.controller

import android.content.Context
import com.hutcwp.srw.R
import com.hutcwp.srw.service.RobotsFactoryService
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.info.Robot

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

    fun createMapListFromLevel(context: Context, mapList: Array<IntArray>): MutableList<MapSprite> {
        val mapSpriteList = mutableListOf<MapSprite>()

        for (y in mapList.indices) {
            for (x in mapList[y].indices) {
                val res = mapList[y][x]
                mapSpriteList.add(MapSprite(context, res, Pos(x, y)))
            }
        }

        return mapSpriteList
    }

    fun createMapList(context: Context): MutableList<MapSprite> {
        val mapSpriteList = mutableListOf<MapSprite>()
        for (x in 0..GameController.MAP_WIDTH_SIZE) {
            for (y in 0..GameController.MAP_HEIGHT_SIZE) {
                mapSpriteList.add(MapSprite(context, R.drawable.map_a_01, Pos(x, y)))
            }
        }

        return mapSpriteList
    }


    fun createSelectSpriteFromPos(context: Context,pos: Pos): SelectSprite {
        val params = RobotParams.Builder().apply {
            this.pos = pos
            this.resId = R.drawable.icon_select
        }.build()
        return SelectSprite(context, params)
    }

}