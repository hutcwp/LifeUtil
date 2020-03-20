package com.hutcwp.live.livebiz.ui.component.danmu.kinds

import android.graphics.Bitmap
import android.graphics.Color
import com.hutcwp.live.livebiz.ui.component.publicmessage.psg.viewbinder.msg.MyChatMsg
import master.flame.danmaku.danmaku.model.R2LDanmaku

/**
 * 彩色弹幕
 */
class ColorDanmaku : R2LDanmaku(null) {

    var msg: MyChatMsg? = null
    var headerUrl: String? = null
    var name: String? = null
    var nameColor: Int = Color.WHITE
    var background: Int = 0x66000000
    var level: Int = 1
    var messageColor: Int = Color.WHITE

    var tempBitmap: Bitmap? = null //用于存放头像资源的缓存
    var tempLevelBitmap: Bitmap? = null //用于存放等级资源的缓存

}