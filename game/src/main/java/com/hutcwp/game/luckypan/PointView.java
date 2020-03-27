package com.hutcwp.game.luckypan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hutcwp on 2020-03-26 19:56
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PointView extends View {

    boolean isOpenScroll;

    public PointView(Context context) {
        super(context);
        init();
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path mPath = new Path();

        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        int ridus = 40;
        float arc = (float) (Math.PI / 180 * 60);

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        int pathStartX = (int) (x + (Math.cos(arc) * ridus));
        int pathStartY = (int) (y - (Math.sin(arc) * ridus));
        int pathAfterX = (int) (x - (Math.cos(arc) * ridus));

        int pathEndX = x;
        int pathEndY = y - ridus * 4;


        mPath.moveTo(pathStartX, pathStartY);
        mPath.lineTo(pathEndX, pathEndY);
        mPath.lineTo(pathAfterX, pathStartY);

        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(x, y, ridus, mPaint);

    }

    public void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpenScroll) {
                    isOpenScroll = true;
                    LuckyPan.startLuckyPan();
                } else {
                    isOpenScroll = false;
                    LuckyPan.stopLuckyPan((int) (Math.random() * 5));
                }
            }
        });
    }

}
