package com.hutcwp.srw.info

/**
 *  author : kevin
 *  date : 2022/3/10 11:54 PM
 *  description :
 */
open class BaseInfo(val type: Int) {
}

class Robot(type: Int, val level: Int=1, val name: String, val move: Int) : BaseInfo(type)

class Map(type: Int, desc: String) : BaseInfo(type)