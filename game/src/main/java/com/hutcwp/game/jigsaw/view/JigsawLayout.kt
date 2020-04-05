package com.hutcwp.game.jigsaw.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
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
class JigsawLayout : RelativeLayout, View.OnClickListener, IController {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val imgViewList: MutableList<GameImageView> = mutableListOf()
    private var mWidth: Int = (ResolutionUtils.getScreenWidth(context) * 0.8).toInt() //真实棋盘的宽度
    private var mCurImageView: GameImageView? = null
    private var mPiece = 3 //分块数
    private var gapPadding = 5 //间距

    private var mFullBitmap: Bitmap? = null
    private var mGestureDetector: GestureDetector

    init {
        val jigsawGestureDetector = JigsawGestureDetector(this)
        mGestureDetector = GestureDetector(context, jigsawGestureDetector)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    fun setImgUrl(imgUrl: String) {
        val options = RequestOptions().override(mWidth, mWidth)
        Glide.with(context).asBitmap().load(imgUrl).apply(options).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(bmp: Bitmap, transition: Transition<in Bitmap>?) {
                MLog.debug(TAG, "bitmap=$bmp")
                mFullBitmap = bmp
                initViews()
            }
        })
    }

    private fun initViews() {
        if (mFullBitmap == null) {
            MLog.error(TAG, "mFullBitmap  is null, return")
        }

        mFullBitmap?.let {
            initGameImageList(it)
            addGameView()
        }
    }

    private fun addGameView() {
        val piece = mPiece
        val weight = mWidth / mPiece //单个图片的width
        var tmp = 0

        removeAllViews()
        imgViewList.forEach {
            val params = LayoutParams(weight, weight)
            val marginLeft = ((tmp % piece) * weight)
            val marginTop = ((tmp / piece) * weight)
            MLog.debug(TAG, "margin: weight=$weight left= $marginLeft, top=$marginTop")
            params.leftMargin = marginLeft
            params.topMargin = marginTop
            it.layoutParams = params
//            it.setOnClickListener(this)
            addView(it)
            tmp++
        }
    }

    private fun initGameImageList(resource: Bitmap) {
        val imgList = split(resource, mPiece)
        imgViewList.clear()
        imgList?.forEach { it ->
            val imgView = GameImageView(context)
            imgView.id = it.index
            imgView.setPadding(gapPadding, gapPadding, gapPadding, gapPadding)
            imgView.setImageBitmap(it.bitmap)
            imgViewList.add(imgView)
        }

        mCurImageView = imgViewList.last()
        mCurImageView?.setBackgroundResource(R.drawable.game_bg_border)

        imgViewList.shuffle()
        var pos = 0
        //洗牌后需要重置位置
        imgViewList.forEach {
            it.setPostion(pos++)
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
                imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue, pieceWidth, pieceWidth)
                pieces.add(imagePiece)
            }
        }
        return pieces
    }

    fun isGameOver(): Boolean {
        for (i in 0 until imgViewList.size) {
            if (imgViewList[i].getPosition() != imgViewList[i].id) {
                return false
            }
        }
        return true
    }

    override fun onClick(v: View?) {
        MLog.debug(TAG, "onClick")
//        if (v is GameImageView) {
//            mCurImageView?.let {
//                if (v != it) {
//                    swapPos(it, v)
//                    if (isGameOver()) {
//                        finishGame()
//                        showFullBitmap()
//                    }
//                }
//            }
//        }
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
        val tmpPos = it.getPosition()
        it.layoutParams = imageView.layoutParams
        imageView.layoutParams = tmpLp
        it.setPostion(imageView.getPosition())
        imageView.setPostion(tmpPos)

        removeView(it)
        removeView(imageView)
        addView(it)
        addView(imageView)
    }

    private fun showFullBitmap() {
        removeAllViews()
        val img = ImageView(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        mFullBitmap?.let {
            img.layoutParams = params
            img.setImageBitmap(mFullBitmap)
        }
        addView(img)
    }

    fun setPiece(pieve: Int) {
        mPiece = pieve
        initViews()
    }

    override fun toLeft() {
        MLog.debug(TAG, "toLeft")
        mCurImageView?.let {
            val row = it.getPosition() / 3
            val col = it.getPosition() % 3
            val curPos = it.getPosition()
            val goal = imgViewList.find { img ->
                img.getPosition() == curPos - 1
            }
            if (col in 1 until mPiece && goal != null) {
                swapPos(it, goal)
            }
        }
    }

    override fun toTop() {
        MLog.debug(TAG, "toTop")
        mCurImageView?.let {
            val row = it.getPosition() / 3
            val col = it.getPosition() % 3
            val curPos = it.getPosition()
            val goal = imgViewList.find { img ->
                img.getPosition() == curPos - mPiece
            }
            if (row in 1 until mPiece && goal != null) {
                swapPos(it, goal)
            }
        }
    }

    override fun toRight() {
        MLog.debug(TAG, "toRight")
        mCurImageView?.let {
            val row = it.getPosition() / 3
            val col = it.getPosition() % 3
            val curPos = it.getPosition()
            val goal = imgViewList.find { img ->
                img.getPosition() == curPos + 1
            }
            if (col in 0 until mPiece - 1 && goal != null) {
                swapPos(it, goal)
            }
        }
    }

    override fun toBottom() {
        MLog.debug(TAG, "toBottom")
        mCurImageView?.let {
            val row = it.getPosition() / 3
            val col = it.getPosition() % 3
            val curPos = it.getPosition()
            val goal = imgViewList.find { img ->
                img.getPosition() == curPos + mPiece
            }
            if (row in 0 until mPiece - 1 && goal != null) {
                swapPos(it, goal)
            }
        }
    }

    companion object {
        const val TAG = "JigsawLayout"
    }
}