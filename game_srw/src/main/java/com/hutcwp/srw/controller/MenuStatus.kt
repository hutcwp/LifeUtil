package com.hutcwp.srw.controller

/**
 *  author : kevin
 *  date : 2022/3/12 10:17 PM
 *  description :
 *  这里可能有多种状态
 *  normal: 普通选中
 *  move: 操作移动时选中目的地
 *  Attack: 攻击时选择目标
 */
enum class MenuStatus {
    Normal,
    Move,
    Attack
}