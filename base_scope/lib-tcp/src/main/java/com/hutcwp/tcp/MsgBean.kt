package com.hutcwp.tcp

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * json格式
 * data:{
 * header{
 * }
 * content:{
 *      data:{
 *      }
 * }
 * }
 */
data class MsgBean(val content: String) {


    fun formatStr(): String {
        return """
         start:
         {
                "code": 0, 
                "message": "操作成功",
                "data": {
                    content:"$content"
                }
        }
        end:
        """.trimIndent()
    }

}



fun String.decodeToTcpRsp(): TcpRsp? {
    return Gson().fromJson(this, TcpRsp::class.java)
}


data class TcpRsp(
    @SerializedName("code")
    var code: Int,
    @SerializedName("message")
    var message: String?,
    @SerializedName("data")
    var data: Any?
) : java.io.Serializable {

}
