package com.hutcwp.srw.util

import com.hutcwp.srw.info.Robot

/**
 *  author : kevin
 *  date : 2022/3/23 11:07 PM
 *  description :
 */


fun Robot.Attributes.robotNameStr(): String {
    return this.name
}

fun Robot.Attributes.levelStr(): String {
    return "等级    ${this.level}"
}

fun Robot.Attributes.mobilityStr(): String {
    return "机动    ${this.move}"
}

fun Robot.Attributes.skillStr(): String {
    return "精神    0 / 0" //todo
}

fun Robot.Attributes.typeStr(): String {
    val type = when (this.type) {
        0 -> {
            "海"
        }
        1 -> {
            "陆"
        }
        2 -> {
            "空"
        }
        else -> "未知"
    }
    return "类型    $type"
}

fun Robot.Attributes.strengthStr(): String {
    return "强度    ${this.attack}"
}

fun Robot.Attributes.defendStr(): String {
    return "防卫    ${this.defend}"
}

fun Robot.Attributes.speedStr(): String {
    return "速度    ${this.speed}"
}

fun Robot.Attributes.hpStr(): String {
    return "HP    ${this.hp}/${this.maxHp}"
}

fun Robot.Attributes.expStr(): String {
    return "Exp    ${this.exp}"
}

fun Robot.Attributes.upExpStr(): String {
    return "升级还需    ？？？"
}

