package com.hutcwp.srw

import com.hutcwp.srw.ai.AI
import com.hutcwp.srw.bean.*
import com.hutcwp.srw.constants.LevelConfig
import com.hutcwp.srw.constants.RobotConstants
import com.hutcwp.srw.controller.ISceneSwitch
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

    var robotSpriteList: List<RobotSprite> = mutableListOf()
    var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    var selectSprite: SelectSprite? = null
    var switchScene: ISceneSwitch? = null


    var isPlayerTurn = true //是否是玩家回合

    var mapView: MapView? = null

    private var hasInit = false


    fun takeTurn() {
        isPlayerTurn = !isPlayerTurn
        updateActionStatus()
        if (!isPlayerTurn) {
            robotSpriteList?.filter { it.robot.attribute.team == 0 }?.let {
                ai.compute(it)
            }
        }
    }

    fun updateActionStatus() {
        robotSpriteList?.forEach {
            it.updateAction(true)
        }
    }

    fun switchLevel(mapView: MapView, no: Int) {
        initGame(mapView, no)
    }


    private fun initGame(mapView: MapView, no: Int) {
        if (!hasInit) {
            mapSpriteList = dataMock.createMapListFromLevel(mapView.context, LevelConfig.No1.mapList)
            robotSpriteList = LevelConfig.No1.blueRobotList + LevelConfig.No1.redRobotList
            selectSprite = dataMock.createSelectSpriteFromPos(mapView.context, LevelConfig.No1.blueRobotList[0].pos)
        }

        hasInit = true
        this.mapView = mapView
        this.switchScene = mapView.activity as ISceneSwitch
        this.mapSpriteList.let {
            mapView.initMap(it)
        }
        robotSpriteList.let {
            mapView.initRobots(it)
        }
        mapView.initSelect(selectSprite!!)
        updateActionStatus()
    }

    fun updateSpritePos(sprite: BaseSprite, pos: Pos) {
        mapView?.updatePosWithAnim(sprite, sprite.pos, pos)


//        mapView?.updateViewPos(sprite)
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

//    fun attack(attacker: RobotSprite, defender: RobotSprite) {
//        defender.beAttacked(attacker.useWeapon()!!.attackValue)
//    }

    fun blueRobotSpriteList(): List<RobotSprite> {
        return robotSpriteList.filter {
            it.robot.attribute.team == RobotConstants.TEAM_BLUE
        }
    }

    fun redRobotSpriteList(): List<RobotSprite> {
        return robotSpriteList.filter {
            it.robot.attribute.team == RobotConstants.TEAM_RED
        }
    }

    fun hasRobot(pos: Pos): Boolean {
        return robotSpriteList.find { it.pos == pos } != null
    }

    fun destroyRobot(robotSprite: RobotSprite) {
        mapView?.removeRobotSprite(robotSprite)
    }


    /**
     * 是否启用游戏控制器
     */
    fun gameControllerEnable(enAble: Boolean) {
        if (enAble) {
            (mapView?.activity as? MainGameActivity)?.froze()
        } else {
            (mapView?.activity as? MainGameActivity)?.unfroze()
        }
    }



}