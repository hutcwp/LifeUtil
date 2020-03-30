package com.hutcwp.game.jigsaw.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.hutcwp.game.R
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils
import me.hutcwp.util.SingleToastUtil

/**
 *
 * Created by hutcwp on 2020-03-29 17:42
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 拼图游戏
 **/
class JigsawLayout : RelativeLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val imgViewList: MutableList<GameImageView> = mutableListOf()

    private var mWidth: Int = (ResolutionUtils.getScreenWidth(context) * 0.8).toInt() //真实棋盘的宽度
    private var mCurImageView: GameImageView? = null

    init {
        val url = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ1kIpNpzm1gnCQ0CdH3RxEfMS1csBr70GYOhqnDnZAf7xB0UDm"
        val options = RequestOptions().override(mWidth, mWidth)
        Glide.with(context).asBitmap().load(url).apply(options).into(object : SimpleTarget<Bitmap?>() {
            override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap?>?) {
                MLog.debug(TAG, "bitmap=$bmp")
                initViews(bmp)
            }
        })
    }

    private fun initViews(resource: Bitmap) {
        val piece = 3f
        val gameImageWidth = mWidth / piece
        val weight = gameImageWidth.toInt()
        var tmp = 0


        val imgList = split(resource, 3)
        imgList?.forEach { it ->
            val imgView = GameImageView(context)
            imgView.id = it.index
            imgView.setPostion(it.index)
            imgView.setPadding(10, 10, 10, 10)
            imgView.setImageBitmap(it.bitmap)
            imgViewList.add(imgView)
        }

        mCurImageView = imgViewList.last()
        mCurImageView?.setImageResource(R.drawable.enemy)

//        imgViewList.shuffle()

        removeAllViews()
        imgViewList.forEach {
            val params = LayoutParams(weight, weight)
            val marginLeft = ((tmp % piece) * weight).toInt()
            val marginTop = ((tmp / piece.toInt()) * weight).toInt()
            MLog.debug(TAG, "margin: weight=$weight left= $marginLeft, top=$marginTop")
            params.leftMargin = marginLeft
            params.topMargin = marginTop
            it.layoutParams = params
            it.setOnClickListener(this)
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
            if (imgViewList[i].getPosition() != i) {
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

    override fun onClick(v: View?) {
        if (v is GameImageView) {
            mCurImageView?.let {
                if (v != it) {
                    swapPos(it, v)
                    if (isGameOver()) {
                        finishGame()
                    }
                }
            }
        }
    }

    private fun finishGame() {
        SingleToastUtil.showToast("游戏结束")
        MLog.info(TAG, "游戏结束")
        imgViewList.forEach {
            it.isEnabled = false
        }
    }

    private fun swapPos(it: GameImageView, imageView: GameImageView) {
        val tmpLp = it.layoutParams
        val tmpPos= it.getPosition()
        it.layoutParams = imageView.layoutParams
        imageView.layoutParams = tmpLp
        it.setPostion(imageView.getPosition())
        imageView.setPostion(tmpPos)

        removeView(it)
        removeView(imageView)
        addView(it)
        addView(imageView)
    }
}