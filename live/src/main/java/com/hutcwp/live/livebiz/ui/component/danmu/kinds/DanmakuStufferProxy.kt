package com.hutcwp.live.livebiz.ui.component.danmu.kinds

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hutcwp.livebiz.R

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
import master.flame.danmaku.ui.widget.DanmakuView

class DanmakuStufferProxy(val context: Context, var mDanmakuView: DanmakuView) : BaseCacheStuffer.Proxy() {

    override fun prepareDrawing(danmaku: BaseDanmaku, fromWorkerThread: Boolean) {
        if (danmaku is ColorDanmaku) {
//            if (!danmaku.headerUrl.equals("")) {
//                Observable.just(danmaku.headerUrl)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe {
//                            Glide.with(context)
//                                    .asBitmap()
//                                    .load(danmaku.headerUrl)
//                                    .into(object : SimpleTarget<Bitmap>() {
//                                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                                            danmaku.tempBitmap = resource
//                                            //刷新指定弹幕
//                                            mDanmakuView.invalidateDanmaku(danmaku, false)
//                                        }
//                                    })
//                        }
//            } else {
//                danmaku.tempBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.default_portrait)
//            }
//
//            mDanmakuView.invalidateDanmaku(danmaku, false)
        }
    }

    override fun releaseResource(danmaku: BaseDanmaku) {
        danmaku.cache = null
        if (danmaku is ColorDanmaku) {
            danmaku.tempBitmap = null
            danmaku.tempLevelBitmap = null
        }
    }
}