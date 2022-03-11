package com.hutcwp.srw.controller

import com.hutcwp.srw.R
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.view.IControllerMenu
import com.hutcwp.srw.view.MapView

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description :
 */
class GameController(private val mapView: MapView) : IControllerMenu {

    private var robotSpriteList: MutableList<RobotSprite> = mutableListOf()
    private var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    private var selectSprite: SelectSprite? = null
    private var curSprite: BaseSprite? = null

    private var isSelectStatus = false //是否是二级操作选择状态

    private val dataMock = TestMockData()

    init {
        mapView.gameController = this
        initMap()
        initRobot()
        initSelect()
    }

    private fun initSelect() {
        val params = RobotParams.Builder().apply {
            this.pos = Pos(2, 3)
            this.resId = R.drawable.icon_select
        }.build()
        selectSprite = SelectSprite(mapView.context, params)
        mapView.initSelect(selectSprite!!)
    }

    private fun initRobot() {
        robotSpriteList.let {
            it.add(dataMock.createRobot(mapView.context,
                    Robot(1, 1,"刚达R", 4), R.drawable.robot_1, 4, 2))
            it.add(dataMock.createRobot(mapView.context,
                    Robot(1, 1,"魔神Z", 4), R.drawable.robot_2, 4, 3))

            mapView.initRobots(it)
        }
    }

    private fun initMap() {
        mapSpriteList.let {
            for (x in 0..MAP_WIDTH_SIZE) {
                for (y in 0..MAP_HEIGHT_SIZE) {
                    it.add(createMap(R.drawable.a_01, Pos(x, y)))
                }
            }
            mapView.initMap(it)
        }
    }


    private fun createMap(resId: Int, pos: Pos): MapSprite {
        return MapSprite(mapView.context, resId, pos)
    }


    fun updateSpritePos(sprite: BaseSprite, pos: Pos) {
        sprite.pos = pos
        mapView.updateViewPos(sprite)
    }

    fun updateSelectSpritePos(pos: Pos) {
        updateSpritePos(selectSprite!!, pos)
    }

    fun changeMapSelectStatus(isMove: Boolean) {
        isSelectStatus = isMove
        if (isMove) {
            val range = 3
            mapView.showMoveRange(selectSprite!!.pos, range)
        } else {
            mapView.showNormalRange()
        }
    }

    override fun move() {
        changeMapSelectStatus(true)
        mapView.dismissMenu()
    }

    override fun status() {
        curSprite ?: return
        when (curSprite) {
            is MapSprite -> {

            }
            is RobotSprite -> {
                mapView.post {
                    mapView.showRobotView((curSprite as RobotSprite).robot)

                }
            }
        }
    }

    override fun turn() {

    }

    override fun skill() {

    }

    override fun select(sprite: BaseSprite) {

        if (!isSelectStatus) {
            mapView.selectSprite(sprite, isSelectStatus)
        } else {
            if (!mapView.canMove(sprite.pos)) {
                return
            }
            updateSpritePos(curSprite!!, sprite.pos)
            updateSpritePos(selectSprite!!, sprite.pos)
            changeMapSelectStatus(false)
        }
        curSprite = sprite

        status()
    }


    companion object {
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16
    }
}