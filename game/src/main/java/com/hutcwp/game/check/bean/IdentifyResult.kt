package com.hutcwp.game.check.bean

/**
 *
 * Created by hutcwp on 2020/4/7 19:52
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class IdentifyResult {

    private var score = 0.0
    private var name: String? = null

    fun setScore(score: Double) {
        this.score = score
    }

    fun getScore(): Double {
        return score
    }

    fun setName(keyword: String?) {
        this.name = keyword
    }

    fun getName(): String? {
        return name
    }
}