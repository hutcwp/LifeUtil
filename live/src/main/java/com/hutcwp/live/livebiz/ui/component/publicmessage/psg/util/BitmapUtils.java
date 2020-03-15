package com.hutcwp.live.livebiz.ui.component.publicmessage.psg.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import me.hutcwp.BasicConfig;

/**
 * Created by hutcwp on 2020-03-10 16:03
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class BitmapUtils {

    public static Bitmap decodeResToBitmap(int resId) {
        if (resId <= 0) {
            return null;
        }
        return BitmapFactory.decodeResource(BasicConfig.getApplicationContext().getResources(), resId);
    }

    public static Bitmap decodeResToBitmap(int resId, int newWidth, int newHeight) {
        if (resId <= 0) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(BasicConfig.getApplicationContext().getResources(), resId);
        // 原图宽高
        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / oldWidth;
        float scaleHeight = ((float) newHeight) / oldHeight;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, oldWidth, oldHeight, matrix, true);
    }
}
