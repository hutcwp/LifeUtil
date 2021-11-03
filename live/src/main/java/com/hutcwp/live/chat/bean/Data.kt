package com.hutcwp.live.chat.bean

import com.google.gson.annotations.SerializedName

/**
 * Description:
 *
 * Created by n24314 on 2021/11/3. E-mail: caiwenpeng@corp.netease.com
 */
class Data(
        @field:SerializedName("title") var title: String,
        @field:SerializedName("type") var type: Int
) {

    companion object {
        const val TYPE_1 = 1
        const val TYPE_2 = 2
    }
}