package com.hutcwp.live.livebiz.ui.component.recommend

import android.os.Bundle
import com.example.presenter.core.MvpPresenter
import com.google.gson.Gson
import com.hutcwp.live.livebiz.ui.component.bean.PlayInfo
import me.hutcwp.log.MLog
import okhttp3.*
import java.io.IOException

/**
 *
 * Created by hutcwp on 2020-03-08 02:31
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class RecommendPresenter : MvpPresenter<IRecommend?>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestData()
    }

    private fun requestData() {
        val url = MUSIC_URL
        val client = OkHttpClient()
        val request: Request = Request.Builder().get().url(url).build()
        val call: Call = client.newCall(request)
        //异步调用并设置回调函数
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                MLog.error(TAG, "parseHtmlFromUrl error", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val content = response.body()?.string() ?: ""
                MLog.info(TAG, "parseHtmlFromUrl onResponse, response=${content}")
                val gson = Gson()
                try {
                    val playable = gson.fromJson(content, PlayInfo::class.java)
                    MLog.info(TAG, "palyable = $playable")
                    view?.updatePlayList(playable)
                } catch (e: Exception) {
                    MLog.error(TAG, "parse Json error e = $e")
                }
            }
        })
    }


    companion object {
        private const val TAG = "VideoComponentPresenter"
        private const val MUSIC_URL = "https://raw.githubusercontent.com/hutcwp/img-floder/master/lifeutil/musics.json"
    }
}