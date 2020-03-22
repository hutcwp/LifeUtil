package com.hutcwp.game.wuziqi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.hutcwp.game.wuziqi.player.IGamePlayer
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils
import java.util.*

/**
 * Created by hutcwp on 2020-03-21 14:35
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class GameView : View, IGameController {
    private var mPaint: Paint? = null
    private var mPointPaint: Paint = Paint()
    private var mPoints: MutableList<GamePoint>? = ArrayList()
    private val mWidth = (ResolutionUtils.getScreenWidth(context) * 0.9).toInt()

    //实际格子数比count少2 ，用于左右边缘
    private val count = 15
    private val weight = mWidth / count

    private val radius = (weight * 0.4).toFloat()

    private var onSelectPointListener: OnSelectPointListener? = null

    constructor(context: Context?) : super(context) {
        initPaint()
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
        initData()
        initListener()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initPaint()
        initData()
    }

    private fun initData() {
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.isFakeBoldText = true
        mPointPaint = Paint()
        mPointPaint.isAntiAlias = true
        mPointPaint.color = Color.WHITE
    }

    private fun initListener() {
        setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    var x = event.x
                    var y = event.y
                    MLog.debug(TAG, "onTouch" + "x: " + x + "y: " + y)
                    var ascX = 0
                    var ascY = 0
                    if (x % weight > weight / 2) {
                        ascX = 1
                    }
                    if (y % weight > weight / 2) {
                        ascY = 1
                    }
                    x = (x / weight + ascX).toInt().toFloat()
                    y = (y / weight + ascY).toInt().toFloat()
                    onSelectPointListener?.selectPoint(x, y)
                }
            }
            false
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth, mWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCheckerBoard(canvas)
        drawPoints(canvas)
    }

    /**
     ** 绘制对战棋盘
     */
    private fun drawCheckerBoard(canvas: Canvas) {
        for (i in 1 until count) {
            var y = i * weight
            var x = i * weight
            if (i == count) {
                x--
                y--
            }

            mPaint?.let {
                //竖线
                canvas.drawLine(x.toFloat(), weight.toFloat(), x.toFloat(), (count - 1) * weight.toFloat(), it)
                //横线
                canvas.drawLine(weight.toFloat(), y.toFloat(), (count - 1) * weight.toFloat(), y.toFloat(), it)
            }
        }
    }

    /**
     **  绘制棋子 Points
     **/
    private fun drawPoints(canvas: Canvas) {
        mPoints?.forEach { point ->
            val realX = point.x * weight
            val realY = point.y * weight
            mPointPaint.color = point.color
            canvas.drawCircle(realX, realY, radius, mPointPaint)
        }
    }

    /**
     *  判断该点是否可以添加棋子
     */
    fun iFAddPoint(x: Float, y: Float): Boolean {
        if (x.toInt() == 0 || x > weight * (count - 1) || y == 0f || y > weight * (count - 1)) {
            return false
        }

        mPoints?.forEach {
            if (it.compare(x, y)) {
                return false
            }
        }
        return true
    }

    //重置棋盘
    fun resetGame() {
        mPoints?.clear()
        initListener()
        invalidate()
    }

    private fun judgeFinish() {
        mPoints?.forEach { p ->
            if (isFinished(p)) {
                Toast.makeText(context, "胜利", Toast.LENGTH_SHORT).show()
                setOnTouchListener { view, motionEvent ->
                    Toast.makeText(context, "请点击开始游戏按钮", Toast.LENGTH_SHORT).show()
                    false
                }
                return
            }
        }
    }

    private fun isFinished(point: GamePoint): Boolean {
        val x = point.x
        val y = point.y
        val type = point.type

        // 计算横的时候
        if (isHavePoint(x - 1, y, type) && isHavePoint(x - 2, y, type) && isHavePoint(x + 1, y, type) && isHavePoint(x + 2, y, type)) {
            return true
        }

        // 计算竖的时候
        if (isHavePoint(x, y - 1, type) && isHavePoint(x, y - 2, type) && isHavePoint(x, y + 1, type) && isHavePoint(x, y + 2, type)) {
            return true
        }

        // 计算\的时候
        if (isHavePoint(x - 1, y - 1, type) && isHavePoint(x - 2, y - 2, type)
                && isHavePoint(x + 1, y + 1, type) && isHavePoint(x + 2, y + 2, type)) {
            return true
        }

        // 计算/的时候
        return (isHavePoint(x - 1, y + 1, type) && isHavePoint(x - 2, y + 2, type)
                && isHavePoint(x + 1, y - 1, type) && isHavePoint(x + 2, y - 2, type))
    }

    private fun isHavePoint(x: Float, y: Float, type: Int): Boolean {
        if (x.toInt() <= 0 || x == mWidth.toFloat() || y <= 0 || y == mWidth.toFloat()) {
            // 出界
            return false
        }

        mPoints?.forEach {
            if (it.isHaving(x, y, type)) {
                return true
            }
        }
        return false
    }

    private fun getScore(player: IGamePlayer, point: GamePoint) {
        val score = 0
        val x = point.x
        val y = point.y
        val type = point.type

        if (isHavePoint(x - 1, y, type)){

        }
    }


    override fun changePlayer() {

    }

    override fun addNewPoint(point: GamePoint, player: IGamePlayer) {
        MLog.debug(TAG, "addPoint")
        val x = point.x
        val y = point.y
        if (iFAddPoint(x, y)) {
            mPoints?.add(GamePoint(x, y, player.type(), point.color))
            judgeFinish()
            invalidate()
        }
    }


    fun setSelectPointListener(listener: OnSelectPointListener) {
        onSelectPointListener = listener
    }

    /**
     * 暴露接口出去
     */
    interface OnSelectPointListener {
        fun selectPoint(x: Float, y: Float)
    }

    companion object {
        const val TAG = "GameView"
    }
}