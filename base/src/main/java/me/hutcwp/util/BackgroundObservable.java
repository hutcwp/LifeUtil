package me.hutcwp.util;

import android.database.Observable;
import android.os.Looper;


public class BackgroundObservable extends Observable<BackgroundObserver> {

    /**
     * 应用进入后台
     * Application turn to background
     */
    public void turnToBackground() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).turnToBackground();
            }
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = mObservers.size() - 1; i >= 0; i--) {
                        mObservers.get(i).turnToBackground();
                    }
                }
            });
        }
    }

    /**
     * 应用进入前台
     * Application turn to foreground
     */
    public void turnToForeground() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).turnToForeground();
            }
        } else {
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = mObservers.size() - 1; i >= 0; i--) {
                        mObservers.get(i).turnToForeground();
                    }
                }
            });
        }
    }

    @Override
    public void registerObserver(BackgroundObserver observer) {
        if (!mObservers.contains(observer)) {
            super.registerObserver(observer);
        }
    }

    @Override
    public void unregisterObserver(BackgroundObserver observer) {
        if (mObservers.contains(observer)) {
            super.unregisterObserver(observer);
        }
    }
}
