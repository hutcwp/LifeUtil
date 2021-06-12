package com.hutcwp.read.entitys

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class GankGirlPhoto : Photo(), Serializable {

    @SerializedName("url")
    override var img: String? = null

    @SerializedName("who")
    override var name: String? = null

    @SerializedName("desc")
    override var date: String? = null
}

