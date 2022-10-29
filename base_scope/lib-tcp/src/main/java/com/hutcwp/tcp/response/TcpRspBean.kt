package com.hutcwp.tcp.response

import com.google.gson.annotations.SerializedName


/**
 * 接口返回格式定义bean，对应data字段
 */
data class TcpRspBean(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var data: Any?
) : java.io.Serializable {

}
