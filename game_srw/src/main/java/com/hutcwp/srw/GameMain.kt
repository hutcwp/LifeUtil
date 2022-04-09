package com.hutcwp.srw

import com.hutcwp.srw.bean.*
import com.hutcwp.srw.constants.RobotConstants
import com.hutcwp.srw.controller.ISceneSwitch
import com.hutcwp.srw.service.ActionManager
import com.hutcwp.srw.service.LevelManager
import com.hutcwp.srw.view.MapView
import me.hutcwp.log.MLog

/**
 *  author : kevin
 *  date : 2022/3/13 12:37 AM
 *  description :
 */
object GameMain {

    private const val TAG = "GameMain"


    var robotSpriteList: MutableList<RobotSprite> = mutableListOf()
    var mapSpriteList: MutableList<MapSprite> = mutableListOf()
    var selectSprite: SelectSprite? = null

    var switchScene: ISceneSwitch? = null

    var mapView: MapView? = null


    /**
     * 这个方法只在关卡重置时调用
     */
    fun switchLevel(mapView: MapView, no: Int) {
        MLog.info(TAG, "switchLevel")
        initGame(mapView, mapView.activity as ISceneSwitch, no)
    }

    private fun initGame(mapView: MapView, switchScene: ISceneSwitch, no: Int) {
        mapSpriteList = LevelManager.getMapSprite(mapView.context)
        robotSpriteList = LevelManager.getRobotSprite().toMutableList()
        selectSprite = LevelManager.getSelectSprite(mapView.context)

        this.mapView = mapView
        this.switchScene = switchScene
        mapView.clearMap()
        mapView.initMap(mapSpriteList)
        mapView.initRobots(robotSpriteList)
        mapView.initSelect(selectSprite!!)
        updateActionStatus()
    }

    fun takeTurn() {
        ActionManager.takeTurn()
    }

    fun finishBattleTaskAI() {
        ActionManager.finishBattleTaskAI()
    }

    fun showBattleAI(leftRobotSprite: RobotSprite, rightRobotSprite: RobotSprite) {
        switchScene?.switchBattleScene(false, leftRobotSprite, rightRobotSprite)
    }

    fun updateActionStatus() {
        robotSpriteList.forEach {
            it.updateAction(true)
        }
    }


    //更新UI
    fun updateSpritePos(sprite: BaseSprite, pos: Pos) {
        mapView?.updatePosWithAnim(sprite, sprite.pos, pos)
    }

    fun destroyRobot(robotSprite: RobotSprite) {
        mapView?.removeRobotSprite(robotSprite)
        robotSpriteList.remove(robotSprite)
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

    fun existRobot(pos: Pos): Boolean {
        return robotSpriteList.find { it.pos == pos } != null
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