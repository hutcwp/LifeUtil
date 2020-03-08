package com.hutcwp.live.livebiz.ui.component.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by hutcwp on 2020-03-07 23:53
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 可播放，mp3或者mp4
 */
data class Playable(
        @SerializedName("name")
        val name: String = "",
        @SerializedName("url")
        val path: String = "",
        @SerializedName("mv")
        val mv: String = ""
)