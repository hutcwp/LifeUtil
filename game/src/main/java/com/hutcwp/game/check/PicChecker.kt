package com.hutcwp.game.check

import com.hutcwp.game.check.util.Base64Util
import com.hutcwp.game.check.util.FileUtil
import com.hutcwp.game.check.util.HttpUtil
import me.hutcwp.log.MLog
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder


/**
 *
 * Created by hutcwp on 2020/4/6 15:36
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
object PicChecker {

    const val TAG = "PicChecker"

    fun checkObject(filePath: String): String {
        // 请求url
        val url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general"
        try { // 本地文件路径
            val imgData: ByteArray = FileUtil.readFileByBytes(filePath)
            val imgStr: String = Base64Util.encode(imgData)
            val imgParam: String = URLEncoder.encode(imgStr, "UTF-8")
            val param = "image=$imgParam"
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            val accessToken = AuthService.getAuth()
            val result: String = HttpUtil.post(url, accessToken, param)
            MLog.info(TAG, "result=$result")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "未知"
    }

    fun checkPlant(filePath: String): String {
        // 请求url
        val url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant"
        try { // 本地文件路径
            val imgData: ByteArray = FileUtil.readFileByBytes(filePath)
            val imgStr: String = Base64Util.encode(imgData)
            val imgParam: String = URLEncoder.encode(imgStr, "UTF-8")
            val param = "image=$imgParam"
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            val accessToken = AuthService.getAuth()
            val result: String = HttpUtil.post(url, accessToken, param)
            MLog.info(TAG, "result=$result")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "未知"
    }
}