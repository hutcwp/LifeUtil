package com.hutcwp.tcp.transform

import android.util.Log
import com.hutcwp.tcp.protocol.TcpProtocol
import com.hutcwp.tcp.protocol.TransformData

/**
 *  author : kevin
 *  date : 2022/10/29 03:05
 *  description :
 *
 */
private const val TAG = "TcpProtocol"


/**
 * 将protocol编码成传输用的格式，将传输内容解码成protocol
 */
object TcpTransformUtil {

    private val dataFormat by lazy { DataFormat() }

    fun transToData(protocol: TcpProtocol): TransformData {
        return dataFormat.formatStr(protocol)
    }

    fun transToProtocol(data: TransformData): TcpProtocol {
        return dataFormat.formatToProtocol(data)
    }
}

/**
 * 传输格式
 */
class DataFormat {

    /**
     * 转换封装成协议格式
     */
    fun formatStr(protocol: TcpProtocol): TransformData {

        val data = """
         start:
         protocol:sid=${protocol.sid},cid=${protocol.cid}
         content:
         {
                "code": 0, 
                "message": "操作成功",
                "data": {
                    content:"${protocol.content}"
                }
        }
        end:
        """.trimIndent()

        return TransformData(data)
    }

    /**
     * 将data转成协议
     */
    fun formatToProtocol(transformData: TransformData): TcpProtocol {
        var data = transformData.data
        val startFlag = "start:"
        val endFlag = "end:"

        val protocolFlag = "protocol:"
        val contentFlag = "content:"

        if ((data.startsWith(startFlag) && data.endsWith(endFlag).not())) {
            Log.w(TAG, "协议格式有问题, data=$data")
            throw Exception("Invalid Protocol Format")
        }

        data = data.replace(startFlag, "").replace(endFlag, "")

        val contentIndex = data.indexOf(contentFlag)
        val protocolIndex = data.indexOf(protocolFlag)

        val protocolStr =
            data.substring(protocolIndex, contentIndex).replace(protocolFlag, "").trim()
        val contentStr = data.substring(contentIndex + contentFlag.length)

        val sid = protocolStr.split(",")[0].replace("sid=", "").toInt()
        val cid = protocolStr.split(",")[1].replace("cid=", "").toInt()

        Log.i(TAG, "sid=$sid,cid=$cid, protocol=$protocolStr , content=$contentStr")

        return TcpProtocol(sid = sid, cid = cid, content = contentStr)
    }

}
