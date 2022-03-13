package com.hutcwp.srw

import com.hutcwp.srw.ai.AI
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.controller.TestMockData
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 12:37 AM
 *  description :
 */
object GameMain {

    private val dataMock = TestMockData()

    private val ai = AI()

    var robotSpriteList: MutableList<RobotSprite> = mutableListOf()
    var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    var selectSprite: SelectSprite? = null


    var isPlayerTurn = true //是否是玩家回合

    var mapView: MapView? = null


    fun takeTurn() {
        isPlayerTurn = !isPlayerTurn
        if (!isPlayerTurn) {
            robotSpriteList?.filter { it.robot.attribute.code == 0 }?.let {
                ai.compute(it)
            }
        }
    }


    fun initGame(mapView: MapView) {
        this.mapView = mapView
        mapSpriteList = dataMock.createMapList(mapView.context)
        robotSpriteList = dataMock.createRobotList(mapView.context)
        selectSprite = dataMock.createSelectSprite(mapView.context)

        mapSpriteList.let {
            mapView.initMap(it)
        }
        robotSpriteList.let {
            mapView.initRobots(it)
        }
        mapView.initSelect(selectSprite!!)
    }

    fun updateSpritePos(sprite: BaseSprite, pos: Pos) {
        sprite.pos = pos
        mapView?.updateViewPos(sprite)
    }

    fun findRobotByPos(pos: Pos): RobotSprite? {
        return robotSpriteList.find { it.pos == pos }
    }


    fun findMapByPos(pos: Pos): MapSprite? {
        return mapSpriteList.find { it.pos == pos }
    }

    fun findSpriteByRobot(robot: Robot): RobotSprite? {
        return robotSpriteList?.find { it.robot == robot }
    }

    fun getSelectPos(): Pos {
        return Pos(selectSprite!!.pos)
    }

    fun isAlive(robot: Robot): Boolean {
        return robotSpriteList.find { it.robot == robot } != null
    }

    fun attack(attacker: Robot, defender: Robot) {
        defender.beAttacked(attacker.useWeapon()!!.attackValue)
    }

    fun destroyRobot(robot: Robot) {
        robotSpriteList?.find { it.robot == robot }?.let {
            robotSpriteList.remove(it)
            findSpriteByRobot(robot)?.let {
                mapView?.removeRobotSprite(it)
            }
        }
    }

}