package com.hutcwp.srw.info

import java.io.Serializable

/**
 *  author : kevin
 *  date : 2022/3/10 11:54 PM
 *  description :
 */
open class BaseInfo(val type: Int): Serializable {
}




class Map(type: Int, desc: String) : BaseInfo(type)