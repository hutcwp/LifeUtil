package com.hutcwp.live.livebiz.ui.component.emoji;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.text.style.ImageSpan;

import com.hutcwp.live.livebiz.base.util.MLog;

import androidx.annotation.NonNull;


/**
 * create by chenrenzhan
 * 自定义 ImageSpan
 */
public class CustomImageSpan extends ImageSpan {
    public static final int ALIGN_VERTICAL_CENTER = 2; //SpannableString 图文居中对齐
    public float mMarginLeft = 0; //左边距
    public float mMarginRight = 0; //右边距
    public float mMargin = 0; //左右边距

    public CustomImageSpan(Drawable d, int verticalAlignment, float marginLeft, float marginRight) {
        super(d, verticalAlignment);
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
    }

    public CustomImageSpan(Drawable d, int verticalAlignment, float margin) {
        super(d, verticalAlignment);
        mMargin = margin;
    }

    public CustomImageSpan(Drawable d, float marginLeft, float marginRight) {
        super(d, ALIGN_VERTICAL_CENTER); //默认居中
        mMarginLeft = marginLeft;
        mMarginRight = marginRight;
    }

    public CustomImageSpan(Drawable d, float margin) {
        super(d, ALIGN_VERTICAL_CENTER); //默认居中
        mMargin = margin;
    }

    public CustomImageSpan(Context context, int resourceId) {
        super(context, resourceId, ALIGN_VERTICAL_CENTER);
    }

    public CustomImageSpan(Drawable d) {
        super(d, ALIGN_VERTICAL_CENTER); //默认居中
    }

    public CustomImageSpan(Drawable d, String source) {
        super(d, source, ALIGN_VERTICAL_CENTER);
    }

    public int getSize(
            @NonNull Paint paint,
            CharSequence text,
            int start,
            int end,
            Paint.FontMetricsInt fm
    ) {
        if (mVerticalAlignment != ALIGN_VERTICAL_CENTER) {
            return super.getSize(paint, text, start, end, fm);
        }

        Drawable d = getDrawable();
        //vivo手机在7.1的room下，这里可能拿到NUL， 因为如果非ui线程在openRawResource的时候可能会报indexOutOfBunds的崩溃，super里面又try住了
        if (d == null) {
            MLog.error("CustomImageSpan", "vivo7.1 crash protected");
            return 0;
        }
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        if (mMarginLeft == 0 && mMarginRight == 0) {
            return (int) (rect.right + mMargin * 2);
        }
        return (int) (rect.right + mMarginLeft + mMarginRight);
    }

    @Override
    public void draw(
            @NonNull Canvas canvas,
            CharSequence text,
            int start,
            int end,
            float x,
            int top,
            int y,
            int bottom,
            @NonNull Paint paint
    ) {
        if (mVerticalAlignment != ALIGN_VERTICAL_CENTER) {
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            return;
        }
        Drawable d = getDrawable();
        if (d == null) {
            //drawable 已经为NULL了 没有继续draw的意义了
            return;
        }
        canvas.save();
        if (mMarginLeft == 0 && mMarginRight == 0) {
            x += mMargin;
        }
        x += mMarginLeft;
        float transY = ((bottom - top) - d.getBounds().bottom) / 2f + top;
        canvas.translate(x, transY);
        if (d instanceof BitmapDrawable) {
            BitmapDrawable drawable = (BitmapDrawable) d;
            if (drawable.getBitmap() != null && !drawable.getBitmap().isRecycled()) {
                d.draw(canvas);
            }
        } else {
            d.draw(canvas);
        }
        canvas.restore();
    }
}  
