package me.hutcwp.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.SystemClock;
import android.view.Choreographer;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import me.hutcwp.log.MLog;

/**
 * Created by hutcwp on 2020-04-01 20:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class MonitorFPS {
    private static final long FAKE_FRAME_TIME = 10;
    private static final Long MONITOR_INTERVAL = 200L;//设置获取fps的时间为200ms
    private static final Long MONITOR_INTERVAL_NANOS = MONITOR_INTERVAL * 1000L * 1000L;
    private static final Long MAX_INTERVAL = 1000L;//设置计算fps的单位时间间隔1000ms,即fps/s;
    private FPSRecordView mFPSFpsRecordView = null;
    private WindowManager mWindowManager = null;
    private volatile boolean mFPSState = false;
    private IFPSCallBack mIFPSCallBack;
    private String mType;
    private Choreographer.FrameCallback mFrameCallback;
    private static final String TAG = "MonitorFPS";

    /**
     * @param context
     * @param type
     */
    public MonitorFPS(Context context, String type) {
        mType = type;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            mWindowManager = (WindowManager) context.getSystemService(Activity.WINDOW_SERVICE);
            mFPSFpsRecordView = new FPSRecordView(context);
        }
    }

    public interface IFPSCallBack {
        void fpsCallBack(double fps);
    }

    public boolean getMonitorFPSStatus() {
        return mFPSState;
    }

    public void setIFPSCallBack(IFPSCallBack ifpsCallBack) {
        mIFPSCallBack = ifpsCallBack;
    }

    public synchronized void stop() {
        if (mFPSState) {
            mFPSState = false;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                try {
                    mWindowManager.removeView(mFPSFpsRecordView);
                    mFPSFpsRecordView.mStartTime = -1;
                    mFPSFpsRecordView.mCounter = 0;
                } catch (Exception e) {

                }
            } else if (mFrameCallback != null) {
                Choreographer.getInstance().removeFrameCallback(mFrameCallback);
            }
        }
    }

    public void start() {
        if (!mFPSState) {
            mFPSState = true;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                startBase();
            } else {
                startJellyBean();
            }
        }
    }

    private void startBase() {
        mFPSFpsRecordView.mStartTime = -1;
        // UI线程插入空view 计算fps
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, 0,
                WindowManager.LayoutParams.TYPE_TOAST, 0,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.height = 1;
        params.width = 1;
        try {
            mWindowManager.removeView(mFPSFpsRecordView);
        } catch (Exception e) {

        }
        mWindowManager.addView(mFPSFpsRecordView, params);
        mFPSFpsRecordView.postDelayed(new Runnable() {
            public void run() {
                // 停止Fps计算
                if (mFPSState) {
                    mFPSFpsRecordView.invalidate();
                    mFPSFpsRecordView.postDelayed(this, FAKE_FRAME_TIME);
                }
            }
        }, FAKE_FRAME_TIME);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void startJellyBean() {
        mFrameCallback = new Choreographer.FrameCallback() {
            private long mStartTime = -1;
            private int mCounter = 0;

            @Override
            public void doFrame(long frameTimeNanos) {
                if (mStartTime == -1) {
                    mStartTime = frameTimeNanos;
                }
                long interval = frameTimeNanos - mStartTime;
                if (interval > MONITOR_INTERVAL_NANOS) {
                    double fps = (((double) (mCounter * 1000L * 1000L)) / interval) * MAX_INTERVAL;
                    if (mIFPSCallBack != null) {
                        mIFPSCallBack.fpsCallBack(fps);
                    }
//                    MonitorUtils.monitorOnTimer(mType, "fps", (float)fps);
                    MLog.info(TAG, "fps:" + (float) fps);
                    mFPSState = false;
                } else {
                    ++mCounter;

                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        };
        Choreographer.getInstance().postFrameCallback(mFrameCallback);
    }

    private class FPSRecordView extends View {
        private long mStartTime = -1;
        private int mCounter = 0;

        public FPSRecordView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (mStartTime == -1) {
                mStartTime = SystemClock.elapsedRealtime();
                mCounter = 0;
            }
            long realInterval = SystemClock.elapsedRealtime() - mStartTime;
            if (realInterval > MONITOR_INTERVAL) {
                double fps = ((double) mCounter / realInterval) * MAX_INTERVAL;
                if (mIFPSCallBack != null) {
                    mIFPSCallBack.fpsCallBack(fps);
                }
//                MonitorUtils.monitorOnTimer(mType, "fps", (float) fps);
                MLog.info(TAG, "2fps:"+ (float) fps);
                MonitorFPS.this.stop();
            }
            mCounter++;
        }

    }
}