package com.hutcwp.live.livebiz.ui.component.emoji;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-20
 * Time: 上午9:52
 * To change this template use File | Settings | File Templates.
 */
public interface IEmoticonsDataInterface {
    public static final int COMPLETE_OPAQUE = 255;
    public static final int LIGHT_TRANSPARENT = 35;

    public Bitmap getBitmap();

    public String getText();

    public int getAlphaValue();
}
