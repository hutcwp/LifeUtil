package com.hutcwp.game.core

import android.widget.TextView
import com.hutcwp.game.bean.Role
import me.hutcwp.log.MLog
import java.lang.StringBuilder

/**
 *
 * Created by hutcwp on 2019-06-25 22:06
 *
 *
 * 战斗系统类
 **/
enum class BattleSystem {

    INSTANCE;

    fun getBattleDetail(own: Role, enemy: Role) {
        own.hp
    }

    /**
     * atk 是攻击方，def是被攻击方
     */
    fun battle(own: Role, enemy: Role, action: (battleDetail: String) -> Unit): Boolean {
        var atk = own
        var def = enemy
        if (own.speed < enemy.speed) {
            atk = enemy
            def = own
        }

        val sb = StringBuilder()
        while (own.hp > 0 && enemy.hp > 0) {
            var hurt = atk.attack - def.defence
            if (hurt < 0) {
                hurt = 0
            }
            def.hp = def.hp - hurt
            sb.append("${def.nick} 受到来自 ${atk.nick} 的伤害").append("\n")
            sb.append("${def.nick} 生命值减少 $hurt ").append("\n")
            action(sb.toString())
            // 改变下角色
            val tmp = atk
            atk = def
            def = tmp
            Thread.sleep(500)
        }
        MLog.info(TAG, "battle end")
        return own.hp > 0

    }

    companion object {
        const val TAG = "BattleSystem"
    }
}