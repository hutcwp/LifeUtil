package com.hutcwp.srw.scene

/**
 *  author : kevin
 *  date : 2022/4/9 6:31 PM
 *  description : 游戏场景接口
 */
interface IScene {

    /**
     * 初始化方法
     * @param initFirst 是否第一次
     */
    fun initWithContext(initFirst: Boolean)

    /**
     * 释放资源，清理工作
     */
    fun release()

}