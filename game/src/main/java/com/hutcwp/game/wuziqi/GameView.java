package com.hutcwp.game.wuziqi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.hutcwp.log.MLog;

/**
 * Created by hutcwp on 2020-03-21 14:35
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class GameView extends View {

    private Paint mPaint;
    private Paint mWhitePointPaint;
    private Paint mBlackPointPaint;

    List<Point> mPoints = new ArrayList<>();

    private int width = (int) (getScreenWidth() * 0.8);
    //实际格子数比count少2 ，用于左右边缘
    private int count = 13;
    private int weight = width / count;

    /*
     *   padding 棋盘的边距，以满格显示棋子
     *   paddingLeft 距屏幕左边宽度
     *   paddingTop 距屏幕上边宽度
     */
    private int padding = 0;
    private float ridus = (float) (weight * 0.4);

    //默认球的颜色为白色
    private int cureentType = 0;


    public GameView(Context context) {
        super(context);
        initPaint();
        initData();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initData();
        initListener();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initData();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width, width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGamePan(canvas);
        drawPoints(canvas);


    }

    /*
     ** 绘制对战棋盘
     */
    private void drawGamePan(Canvas canvas) {

        /*
         **    5个格子
         *    需要画6条线
         */
        for (int i = 1; i < count; i++) {

            int y = i * weight;
            int x = i * weight;
            if (i == count) {
                x--;
                y--;
            }

            //竖线
            canvas.drawLine(x, weight, x + padding, (count - 1) * weight, mPaint);
            //横线
            canvas.drawLine(weight, y, (count - 1) * weight, y, mPaint);
        }
    }

    /*
     **  绘制棋子 Points
     *
     */
    private void drawPoints(Canvas canvas) {

        if (mPoints != null)
            if (mPoints.size() != 0) {

                for (Point point : mPoints)
                    if (point.getType() == 0) {
                        canvas.drawCircle(point.getX(), point.getY(), point.getRidus(), mWhitePointPaint);
                    } else {
                        canvas.drawCircle(point.getX(), point.getY(), point.getRidus(), mBlackPointPaint);
                    }
            }
    }


    /*
     **
     *  初始化棋子
     */
    private void initData() {

//        mPoints.add(new Point(weight, weight, ridus, 0));
//        mPoints.add(new Point(0, 0, ridus, 1));

    }


    /*
     **
     *  初始化画笔
     */

    public void initPaint() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);

        mWhitePointPaint = new Paint();
        mWhitePointPaint.setAntiAlias(true);
        mWhitePointPaint.setColor(Color.WHITE);

        mBlackPointPaint = new Paint();
        mBlackPointPaint.setAntiAlias(true);
        mBlackPointPaint.setColor(Color.BLACK);
    }

    public void initListener() {

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        float x = event.getX();
                        float y = event.getY();


                        MLog.debug("TEST", "onTouch" + "x: " + x + "y: " + y);
                        int ascX = 0;
                        int ascY = 0;

                        if (x % weight > weight / 2) {
                            ascX = 1;
                        }

                        if (y % weight > weight / 2) {
                            ascY = 1;
                        }

                        x = (int) ((x / weight) + ascX) * weight + padding;
                        y = (int) ((y / weight) + ascY) * weight + padding;


                        addPoint(x, y);

                        MLog.debug("TEST", "onTouch");

                        JudgeFinish();

                        invalidate();
                        break;
                }
                return false;
            }
        });


    }

    /*
     *
     *  添加棋子
     */
    private void addPoint(float x, float y) {
        MLog.debug("TEST", "addPoint");
        if (iFAddPoint(x, y)) {
            mPoints.add(new Point((int) x, (int) y, ridus, cureentType));
            changeType();

        }

    }

    /*
     **
     *  判断该点是否可以添加棋子
     */
    private boolean iFAddPoint(float x, float y) {
        if ((int) x == 0 || x > weight * (count - 1) || y == 0 || y > weight * (count - 1)) {
            return false;
        }

        if (mPoints != null) {
            if (mPoints.size() != 0) {
                for (Point point : mPoints) {
                    if (point.Compare(x, y)) {
                        return false;

                    }
                }
            }
            return true;
        }

        return false;
    }


    private void changeType() {
        if (cureentType == 0) {
            cureentType = 1;
        } else {
            cureentType = 0;
        }
    }


    /*
     ** 获得屏幕宽度
     */
    private int getScreenWidth() {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void resetGame() {

        initListener();

        if (mPoints != null && mPoints.size() != 0) {

            mPoints = new ArrayList<>();
            invalidate();
        }
    }


    public void JudgeFinish() {

        for (Point p : mPoints) {

            if (isFinished(p)) {

                Toast.makeText(getContext(), "胜利", Toast.LENGTH_SHORT).show();
//                this.setFocusable(false);
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Toast.makeText(getContext(), "请点击开始游戏按钮", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                return;
            }

        }

    }

    private boolean isFinished(Point point) {

        float x = point.getX();
        float y = point.getY();
        int type = point.getType();
        /*
         **  计算横的时候
         */

        if (isHaved(x - weight, y, type) && isHaved(x - 2 * weight, y, type) && isHaved(x + weight, y, type) && isHaved(x + 2 * weight, y, type)) {
            return true;
        }
        /*
         **  计算竖的时候
         */

        if (isHaved(x, y - weight, type) && isHaved(x, y - 2 * weight, type) && isHaved(x, y + weight, type) && isHaved(x, y + 2 * weight, type)) {
            return true;
        }

        /*
         **  计算、的时候
         */

        if (isHaved(x - weight, y - weight, type) && isHaved(x - 2 * weight, y - 2 * weight, type)
                && isHaved(x + weight, y + weight, type) && isHaved(x + 2 * weight, y + 2 * weight, type)) {
            return true;
        }

        /*
         **  计算/的时候
         */
        if (isHaved(x - weight, y + weight, type) && isHaved(x - 2 * weight, y + 2 * weight, type)
                && isHaved(x + weight, y - weight, type) && isHaved(x + 2 * weight, y - 2 * weight, type)) {
            return true;
        }

        return false;

    }

    private boolean isHaved(float x, float y, int type) {
        if ((int) x <= 0 || x == width || y <= 0 || y == width) {

            /*
             ** 出界
             */
            return false;
        }

        if (mPoints != null) {
            if (mPoints.size() != 0) {
                for (Point p : mPoints) {
                    if (p.isHaving(x, y, type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
