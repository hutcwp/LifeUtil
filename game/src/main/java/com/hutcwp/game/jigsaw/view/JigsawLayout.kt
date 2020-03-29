package com.hutcwp.game.jigsaw.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils

/**
 *
 * Created by hutcwp on 2020-03-29 17:42
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 拼图游戏
 **/
class JigsawLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val imgList: List<Drawable> = arrayListOf()
    private val imgViewList: MutableList<ImageView> = mutableListOf()

    private var mWidth: Int = (ResolutionUtils.getScreenWidth(context) * 0.3).toInt() //真实棋盘的宽度
    private var mHeight: Int = (ResolutionUtils.getScreenWidth(context) * 0.2).toInt() //真实棋盘的宽度

    init {
        val url = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2445923081,4019730289&fm=26&gp=0.jpg"
        val options = RequestOptions().override(mWidth, mWidth)
        Glide.with(context).asBitmap().load(url).apply(options).into(object : SimpleTarget<Bitmap?>() {
            override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap?>?) {
                MLog.debug(TAG, "bitmap=$bmp")
                initViews(bmp)
            }
        })
    }

    fun cutOutPic() {

    }


    private fun initViews(resource: Bitmap) {
        val w = mWidth / 3
        val weight = ResolutionUtils.convertDpToPixel(w.toFloat(), context).toInt()
        var tmp = 0
        val m = 3f

        val imgList = split(resource, 3)
        imgList?.forEach { it ->
            val imgview = ImageView(context)
            imgview.id = it.index
            imgview.setPadding(10, 10, 10, 10)
            imgview.setImageBitmap(it.bitmap)
            imgViewList.add(imgview)
        }

//        imgViewList.shuffle()

        removeAllViews()
        imgViewList.forEach {
            val params = LayoutParams(weight, weight)
            val marginLeft = ((tmp % m) * weight).toInt()
            val marginTop = ((tmp / m.toInt()) * weight).toInt()
            MLog.debug(TAG, "margin: weight=$weight left= $marginLeft, top=$marginTop")
            params.leftMargin = marginLeft
            params.topMargin = marginTop
            it.layoutParams = params
            addView(it)
            tmp++
        }
    }

   private fun split(bitmap: Bitmap, piece: Int): List<ImagePiece>? {
        val pieces: MutableList<ImagePiece> = ArrayList(piece * piece)
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        val pieceWidth = width.coerceAtMost(height) / piece
        for (i in 0 until piece) {
            for (j in 0 until piece) {
                val imagePiece = ImagePiece()
                imagePiece.index = j + i * piece
                MLog.debug(TAG, "imagePiece.index" + (j + i * piece))
                val xValue = j * pieceWidth
                val yValue = i * pieceWidth
                MLog.debug(TAG, "xValue=$xValue,yValue=$yValue")
                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceWidth)
                pieces.add(imagePiece)
            }
        }
        return pieces
    }

    fun isGameOver(): Boolean {
        for (i in 0 until imgViewList.size) {
            if (imgViewList[i].id != i) {
                return false
            }
        }
        return true
    }

    class ImagePiece {
        var index: Int = 0
        var bitmap: Bitmap? = null
    }


    companion object {
        const val TAG = "JigsawLayout"
    }
}