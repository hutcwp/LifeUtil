package com.hutcwp.srw.controller

import android.widget.Toast
import com.hutcwp.srw.BattleUtil
import com.hutcwp.srw.R
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.info.Robot
import com.hutcwp.srw.view.IControllerMenu
import com.hutcwp.srw.view.MapView
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/6 6:49 PM
 *  description :
 */
class GameController(private val mapView: MapView) : IControllerMenu, IGameController {

    private var robotSpriteList: MutableList<RobotSprite> = mutableListOf()
    private var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    private var selectSprite: SelectSprite? = null
    private var curSprite: BaseSprite? = null

    private var curRobotSprite: RobotSprite? = null

    private var menuStatus: MenuStatus = MenuStatus.Normal


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
                    Robot(1, 1, "刚达R", 4, 100, 400), R.drawable.robot_1, 4, 2))
            it.add(dataMock.createRobot(mapView.context,
                    Robot(1, 1, "魔神Z", 4, 80, 340), R.drawable.robot_2, 6, 6))

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

    fun changeMapSelectStatus(status: MenuStatus) {
        menuStatus = status
    }

    fun canMoveToPos(pos: Pos): Boolean {
        return (pos.x in 0..MAP_WIDTH_SIZE) && (pos.y in 0..MAP_HEIGHT_SIZE)
    }

    fun findRobotByPos(pos: Pos): RobotSprite? {
        return robotSpriteList?.find { it.pos == pos }
    }

    fun findMapByPos(pos: Pos): MapSprite? {
        return mapSpriteList?.find { it.pos == pos }
    }

    override fun move() {
        changeMapSelectStatus(MenuStatus.Move)
        mapView.dismissMenu()

        val range = 3
        mapView.showMoveRange(selectSprite!!.pos, range)
    }

    override fun attack() {
        changeMapSelectStatus(MenuStatus.Attack)
        mapView.dismissMenu()

        val range = 3
        mapView.showAttackRange(selectSprite!!.pos, range)
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
        when (menuStatus) {
            MenuStatus.Normal -> {
                mapView.selectSprite(sprite, false)
                mapView.showNormalRange()
                if (sprite is RobotSprite) {
                    curRobotSprite = sprite
                }
            }
            MenuStatus.Move -> {
                if (!mapView.canMove(sprite.pos)) {
                    return
                }
                updateSpritePos(curSprite!!, sprite.pos)
                updateSpritePos(selectSprite!!, sprite.pos)
                mapView.showNormalRange()
                changeMapSelectStatus(MenuStatus.Normal)
            }
            MenuStatus.Attack -> {
                if (sprite is RobotSprite) {
                    Toast.makeText(mapView.context, "攻击它！", Toast.LENGTH_SHORT).show()
                    BattleUtil.attack(curRobotSprite!!, sprite)
                    mapView.showNormalRange()
                    changeMapSelectStatus(MenuStatus.Normal)

                }
            }
        }

        curSprite = sprite
        status()

    }


    override fun up() {
        MLog.debug(TAG, "up")
        val pos = Pos(selectSprite!!.pos.x, selectSprite!!.pos.y - 1)
        if (canMoveToPos(pos)) {
            updateSpritePos(selectSprite!!, pos)
        }
    }

    override fun down() {
        MLog.debug(TAG, "down")
        val pos = Pos(selectSprite!!.pos.x, selectSprite!!.pos.y + 1)
        if (canMoveToPos(pos)) {
            updateSpritePos(selectSprite!!, pos)
        }
    }

    override fun left() {
        MLog.debug(TAG, "left")
        val pos = Pos(selectSprite!!.pos.x - 1, selectSprite!!.pos.y)
        if (canMoveToPos(pos)) {
            updateSpritePos(selectSprite!!, pos)
        }
    }

    override fun right() {
        MLog.debug(TAG, "right")
        val pos = Pos(selectSprite!!.pos.x + 1, selectSprite!!.pos.y)
        if (canMoveToPos(pos)) {
            updateSpritePos(selectSprite!!, pos)
        }
    }

    override fun ok() {
        MLog.debug(TAG, "ok")
        when (menuStatus) {
            MenuStatus.Normal -> {
                findRobotByPos(selectSprite!!.pos)?.let {
                    select(it)
                }
            }
            MenuStatus.Move -> {
                findMapByPos(selectSprite!!.pos)?.let {
                    select(it)
                }
            }
        }
    }

    override fun cancel() {
        MLog.debug(TAG, "cancel")
        resetToNormalStatus()
    }

    private fun resetToNormalStatus() {
        changeMapSelectStatus(MenuStatus.Normal)
        mapView.showNormalRange()
    }


    companion object {
        const val TAG = "GameController"
        const val MAP_WIDTH_SIZE = 16
        const val MAP_HEIGHT_SIZE = 16
    }
}