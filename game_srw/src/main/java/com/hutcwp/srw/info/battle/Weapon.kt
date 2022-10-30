package com.hutcwp.srw.info.battle

import java.io.Serializable

/**
 *  author : kevin
 *  date : 2022/3/13 4:10 PM
 *  description : 武器 ：海陆空 伤害不一样
 *  @param name 名称
 *  @param attackValue 伤害
 *  @param range 射程
 *  @param aim 命中
 */
open class Weapon(val name: String, val attackValue: Int, val range: Int,val aim:Int) : Serializable {

    open fun weaAttack(): Int {
        return attackValue
    }

    open fun landAttack(): Int {
        return attackValue
    }

    open fun airAttack(): Int {
        return attackValue
    }
}