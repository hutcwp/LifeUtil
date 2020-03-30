package com.hutcwp.game.jigsaw.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 *
 * Created by hutcwp on 2020-03-30 20:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 拼图小块View
 **/
class GameImageView : ImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var position: Int = -1

    fun setPostion(pos: Int) {
        position = pos
    }

    fun getPosition(): Int {
        return position
    }

}