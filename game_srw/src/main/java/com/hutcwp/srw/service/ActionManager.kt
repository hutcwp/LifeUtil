package com.hutcwp.srw.service

import com.hutcwp.srw.GameMain
import com.hutcwp.srw.ai.AI

/**
 *  author : kevin
 *  date : 2022/4/9 3:19 PM
 *  description :
 */
object ActionManager {

    private val ai = AI()

    var isPlayerTurn = true //是否是玩家回合


    fun takeTurn() {
        isPlayerTurn = !isPlayerTurn
        GameMain.updateActionStatus()
        if (!isPlayerTurn) {
            GameMain.robotSpriteList.filter { it.robot.attribute.team == 0 }?.let {
                ai.compute(it)
            }
        }
    }


    fun finishBattleTaskAI() {
        ai.finishBattleTask()
    }



}