package com.hutcwp.srw

import com.hutcwp.srw.bean.*
import com.hutcwp.srw.controller.TestMockData
import com.hutcwp.srw.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/13 12:37 AM
 *  description :
 */
object GameMain {

    private val dataMock = TestMockData()

    var robotSpriteList: MutableList<RobotSprite> = mutableListOf()
    var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    var selectSprite: SelectSprite? = null


    var isPlayerTurn = true //是否是玩家回合

    var mapView: MapView? = null


    fun takeTurn() {
        isPlayerTurn = !isPlayerTurn
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

    fun getSelectPos(): Pos {
        return Pos(selectSprite!!.pos)
    }

}