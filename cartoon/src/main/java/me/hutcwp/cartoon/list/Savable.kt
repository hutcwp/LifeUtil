package me.hutcwp.cartoon.list

/**
 *Author:Administrator
 *Time:2019/6/3 22:54
 *YY:909076244
 **/
interface Savable {

    fun init(data: ByteArray)

    fun toBytes(): ByteArray

    fun describe(): String
}