package com.hutcwp.game.wuziqi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
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
    private var mCheckBoardPaint: Paint = Paint() //棋盘画笔
    private var mPointPaint: Paint = Paint() //棋子画笔
    private var mPoints: MutableList<GamePoint>? = ArrayList()
    private val mCheckBoardWidth = (ResolutionUtils.getScreenWidth(context) * 0.8).toInt() //真实棋盘的宽度

    private val padding = (mCheckBoardWidth * 0.1f / 2f) //棋盘边距
    private val boardCount = 15 //棋盘点数
    private val weight = ((mCheckBoardWidth - padding * 2) / (boardCount - 1)) //单个棋子宽度
    private val radius = (weight * 0.4).toFloat() //棋子圆幅度

    private val isDebug = false

    private var onSelectPointListener: OnSelectPointListener? = null
    private var flagPoints = listOf(Point(4, 4), Point(8, 8),
            Point(4, 12), Point(12, 12), Point(12, 4)) //棋子标点

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
        mCheckBoardPaint.isAntiAlias = true
        mCheckBoardPaint.isFakeBoldText = true
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
                    val selectX = (x / weight + ascX).toInt()
                    val selectY = (y / weight + ascY).toInt()
                    onSelectPointListener?.selectPoint(selectX, selectY)
                }
            }
            false
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mCheckBoardWidth, mCheckBoardWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCheckerBoard(canvas)
        drawFlagPoint(canvas)
        drawPoints(canvas)
    }

    /**
     * 绘制标志点
     */
    private fun drawFlagPoint(canvas: Canvas) {
        flagPoints.forEach { point ->
            val realX = (point.x - 1) * weight + padding
            val realY = (point.y - 1) * weight + padding
            mPointPaint.color = Color.BLACK
            val flagPadding = 10f
            val l = realX - flagPadding
            val t = realY - flagPadding
            val r = realX + flagPadding
            val b = realY + flagPadding
            canvas.drawRect(l, t, r, b, mPointPaint)
        }
    }

    /**
     ** 绘制对战棋盘
     */
    private fun drawCheckerBoard(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = ResolutionUtils.convertDpToPixel(12f, context)

        if (isDebug) {
            for (i in 1..boardCount) {
                val offset = ResolutionUtils.convertDpToPixel(10f, context)
                canvas.drawText(i.toString(), weight.toFloat() - offset, weight * i.toFloat(), paint)
                canvas.drawText(i.toString(), weight * i.toFloat() - offset, weight.toFloat(), paint)
            }
        }

        for (i in 1..boardCount) {
            val y = (i - 1) * weight + padding
            val x = (i - 1) * weight + padding
            mCheckBoardPaint.let {
                //竖线
                canvas.drawLine(x.toFloat(), padding.toFloat(), x.toFloat(), padding + (boardCount - 1) * weight.toFloat(), it)
                //横线
                canvas.drawLine(padding.toFloat(), y.toFloat(), padding + (boardCount - 1) * weight.toFloat(), y.toFloat(), it)
            }
        }
    }

    /**
     **  绘制棋子 Points
     **/
    private fun drawPoints(canvas: Canvas) {
        mPoints?.forEach { point ->
            val realX = (point.x - 1) * weight.toFloat() + padding
            val realY = (point.y - 1) * weight.toFloat() + padding
            mPointPaint.color = point.color
            canvas.drawCircle(realX, realY, radius, mPointPaint)
        }
    }

    /**
     *  判断该点是否可以添加棋子
     */
    fun canAddNewPoint(x: Int, y: Int): Boolean {
        if (x == 0 || x > weight * (boardCount - 1) || y == 0 || y > weight * (boardCount - 1)) {
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

    private fun judgeFinish(player: IGamePlayer) {
        mPoints?.forEach { p ->
            if (isFinished(p)) {
                Toast.makeText(context, "${player.name()} 胜利", Toast.LENGTH_SHORT).show()
                setOnTouchListener { view, motionEvent ->
                    Toast.makeText(context, "请点击开始游戏按钮", Toast.LENGTH_SHORT).show()
                    false
                }
                return
            }
        }
    }

    fun isGameOver(): Boolean {
        mPoints?.forEach { p ->
            if (isFinished(p)) {
                return true
            }
        }
        return false
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

    private fun isHavePoint(x: Int, y: Int, type: Int): Boolean {
        if (x < 1 || x > boardCount || y < 1 || y > boardCount) {
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

    override fun addNewPoint(point: Point, player: IGamePlayer): Boolean {
        MLog.info(TAG, "point=$point, player=$player")
        val x = point.x
        val y = point.y
        if (canAddNewPoint(x, y)) {
            mPoints?.add(GamePoint(x, y, player.type(), player.pointColor()))
            judgeFinish(player)
            invalidate()
            return true
        }
        return false
    }

    fun getBoardCount(): Int {
        return boardCount
    }

    fun setSelectPointListener(listener: OnSelectPointListener) {
        onSelectPointListener = listener
    }

    /**
     * 暴露接口出去
     */
    interface OnSelectPointListener {
        fun selectPoint(x: Int, y: Int)
    }

    companion object {
        const val TAG = "GameView"
    }
}