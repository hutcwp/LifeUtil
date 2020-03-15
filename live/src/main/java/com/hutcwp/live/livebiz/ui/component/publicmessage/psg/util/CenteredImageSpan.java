package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import java.lang.ref.WeakReference;

import androidx.annotation.DrawableRes;

/**
 * Created by hutcwp on 2020-03-10 16:02
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class CenteredImageSpan extends ImageSpan {
    private WeakReference<Drawable> mDrawableRef;

    public CenteredImageSpan(Drawable drawable) {
        super(drawable, ImageSpan.ALIGN_BOTTOM);
    }

    public CenteredImageSpan(Context context, final Bitmap bm) {
        super(context, bm, ImageSpan.ALIGN_BOTTOM);
    }

    public CenteredImageSpan(Context context, @DrawableRes int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end,
                       Paint.FontMetricsInt fm) {
        Drawable d = getCachedDrawable();
        Rect rect = d.getBounds();

        if (fm != null) {
            Paint.FontMetricsInt pfm = paint.getFontMetricsInt();
            // keep it the same as paint's fm
            fm.ascent = pfm.ascent;
            fm.descent = pfm.descent;
            fm.top = pfm.top;
            fm.bottom = pfm.bottom;
        }

        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, Paint paint) {
        Drawable b = getCachedDrawable();
        canvas.save();
        int transY = top + (bottom - top - b.getBounds().bottom) / 2;

//        int transY = bottom - b.getBounds().bottom // align bottom to bottom // at least , this work
//                - Math.abs(bottom - b.getBounds().bottom ) / 2;  // align center to center
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    // Redefined locally because it is a private member from DynamicDrawableSpan
    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;

        if (wr != null) {
            d = wr.get();
        }

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }
}
