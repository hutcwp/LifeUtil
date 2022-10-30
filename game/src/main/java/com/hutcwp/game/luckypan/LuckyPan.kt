package com.hutcwp.game.luckypan

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hutcwp.game.R
import me.hutcwp.log.MLog
import kotlin.concurrent.thread
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Created by hutcwp on 2020-03-26 19:53
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class LuckyPan : SurfaceView, SurfaceHolder.Callback, Runnable {

    private var mCanvas: Canvas? = null
    private var mIsDrawing = false
    private var mBackgroundPaint: Paint = Paint()
    private var mTextPaint: Paint = Paint()
    private var mArcPaint: Paint = Paint()
    private var padding = 40
    private var radius = 400
    private var startAngle = 0
    private var recf: RectF? = null
    private var mArcColor = intArrayOf(Color.RED, Color.YELLOW, Color.CYAN, Color.GREEN, Color.BLUE, Color.LTGRAY)
    private var mTextContent = arrayOf("一等奖", "二等奖", "谢谢参与", "三等奖", "优胜奖", "谢谢参与")

    constructor(context: Context?) : this(context, null) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        holder.addCallback(this)
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
        initPaint()
    }

    private fun initPaint() {
        mBackgroundPaint.isAntiAlias = true
        mBackgroundPaint.color = Color.RED
        mArcPaint.isAntiAlias = true
        mArcPaint.color = Color.GREEN
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics)
        mTextPaint.color = Color.WHITE
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        mIsDrawing = true
        thread(start = true) { run() }
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        mIsDrawing = false
    }

    override fun run() {
        while (mIsDrawing) {
            val startTime = System.currentTimeMillis()
            draw()
            val endTime = System.currentTimeMillis()
            if (endTime - startTime < 100) {
                try {
                    Thread.sleep(100 - (endTime - startTime))
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun draw() {
        try {
            mCanvas = holder?.lockCanvas()
            drawBackground()
            drawArc()
        } catch (e: Exception) {

        } finally {
            if (mCanvas != null) {
                holder?.unlockCanvasAndPost(mCanvas)
            }
        }
    }

    private fun drawBitmap(angle: Float) {
        val imgWidth = radius / 8
        //取一半
        val arc = (angle * Math.PI / 180).toFloat()
        val y = (sin(arc.toDouble()) * radius / 2 + height / 2).toInt()
        val x = (cos(arc.toDouble()) * radius / 2 + width / 2).toInt()
        val rect = Rect(x - imgWidth, y - imgWidth, x + imgWidth, y + imgWidth)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.enemy)
        mCanvas?.drawBitmap(bitmap, null, rect, Paint())
    }

    private fun drawBackground() {
        mCanvas?.drawColor(Color.WHITE)
        mCanvas?.drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius.toFloat(), mBackgroundPaint)
    }

    private fun drawPathText(startAngle: Int, sweepAngle: Int, pos: Int) {
        val hOffset = Math.PI.toFloat() * radius * 60 / 180 / 2 - mTextPaint.measureText(mTextContent[pos]) / 2 - 20
        val vOffset = padding.toFloat() + 30
        val path = Path()
        path.arcTo(recf, startAngle.toFloat(), sweepAngle.toFloat())
        mCanvas?.drawTextOnPath(mTextContent[pos], path, hOffset, vOffset, mTextPaint)
    }

    private fun drawArc() {
        val sweepAngle = 60
        if (isStopLuckyPan) {
            if (speed > 0) {
                speed--
            } else {
                isStopLuckyPan = false
            }
        }
        if (isStartAngle) {
            startAngle = 0
            isStartAngle = false
        }

        recf = RectF((width / 2 - radius + padding).toFloat(), (height / 2 - radius + padding).toFloat(), (width / 2 + radius - padding).toFloat(), (height / 2 + radius - padding).toFloat())
        for (i in 0..5) {
            startAngle += 60
            //            Log.e("aaaa", "startAngle: " + startAngle);
            mArcPaint.color = mArcColor[i]
            mCanvas?.drawArc(recf!!, startAngle.toFloat(), sweepAngle.toFloat(), true, mArcPaint)
            drawPathText(startAngle, sweepAngle, i)
            drawBitmap(startAngle + sweepAngle / 2.toFloat())
        }
        startAngle += speed
    }


    companion object {
        private const val TAG = "LuckyPan"
        private var speed = 0
        private var isStopLuckyPan = true
        private var isStartAngle = false
        private var Angle = intArrayOf(240, 180, 120, 60, 360, 300)

        @JvmStatic
        fun startLuckyPan() {
            speed = 50
            isStopLuckyPan = false
        }


        @JvmStatic
        fun stopLuckyPan(dex: Int) {
            /*
             ** index :  0   所在的盘区域
             * angle1 :  210  到达目标所需要的最小角度
             * angle2 :  270  到达目标所需要的最大角度
             *  v1    : v1 = -1 +- sqr(1-4(-2dy))/2(-2dy)   dy=210  最小speed
             *  v2    : v2 = -1 +- sqr(1-4(-2dy))/2(-2dy)   dy=270  最大speed
             */
            val angle = 60
            val angleCanch = 360
            val dAngle1 = Angle[dex]
            val dAngle2 = Angle[dex] + angle
            val v0 = (-1 + sqrt(1 + 8 * angleCanch.toDouble())).toInt() / 2
            val v1 = (-1 + sqrt(1 + 8 * dAngle1.toDouble())).toInt() / 2 + 1
            val v2 = (-1 + sqrt(1 + 8 * dAngle2.toDouble())).toInt() / 2 - 1
            speed = (v1 + Math.random() * (v2 - v1 + 2)).toInt()
            MLog.info(TAG, "speed: $speed")
            isStartAngle = true
            isStopLuckyPan = true
        }
    }
}