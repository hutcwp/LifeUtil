package com.hutcwp.game.sanguo.bean

/**
 *
 * Created by hutcwp on 2019-06-25 16:23
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 角色类，所有的人物都继承于这个类
 **/
open class Role() {
    var nick: String = ""
    var hp: Long = 0
    var attack: Long = 0
    var defence: Long = 0
    var speed: Long = 0

    constructor(nick: String, hp: Long, attack: Long, defence: Long, speed: Long) : this() {
        this.nick = nick
        this.hp = hp
        this.attack = attack
        this.defence = defence
        this.speed = speed
    }

    override fun toString(): String {
        return "Role(nick='$nick', hp=$hp, attack=$attack, defence=$defence)"
    }
}