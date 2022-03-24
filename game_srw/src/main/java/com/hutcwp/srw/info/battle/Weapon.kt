package com.hutcwp.srw.info.battle

import java.io.Serializable

/**
 *  author : kevin
 *  date : 2022/3/13 4:10 PM
 *  description : 武器 ：海陆空 伤害不一样
 */
open class Weapon(val attackValue: Int, val Name: String) : Serializable {

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