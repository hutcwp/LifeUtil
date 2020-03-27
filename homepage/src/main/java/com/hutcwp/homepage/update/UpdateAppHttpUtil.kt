package com.hutcwp.homepage.update


import com.vector.update_app.HttpManager
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.FileCallBack
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import java.io.File


/**
 *
 * Created by hutcwp on 2020-03-27 17:47
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class UpdateAppHttpUtil : HttpManager {

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    override fun download(url: String, path: String, fileName: String, callback: HttpManager.FileCallback) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(object : FileCallBack(path, fileName) {
                    override fun inProgress(progress: Float, total: Long, id: Int) {
                        callback.onProgress(progress, total)
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
                        callback.onError(validateError(e, response))
                    }

                    override fun onResponse(response: File?, id: Int) {
                        callback.onResponse(response)
                    }

                    override fun onBefore(request: Request?, id: Int) {
                        super.onBefore(request, id)
                        callback.onBefore()
                    }
                })
    }

    override fun asyncGet(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
                        callBack.onError(validateError(e, response))
                    }

                    override fun onResponse(response: String?, id: Int) {
                        callBack.onResponse(response)
                    }
                })
    }

    override fun asyncPost(url: String, params: MutableMap<String, String>, callBack: HttpManager.Callback) {
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(object : StringCallback() {
                    override fun onError(call: Call?, response: Response?, e: Exception?, id: Int) {
                        callBack.onError(validateError(e, response))
                    }

                    override fun onResponse(response: String?, id: Int) {
                        callBack.onResponse(response)
                    }
                })
    }
}