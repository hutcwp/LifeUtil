package me.hutcwp.util;


public abstract class BackgroundObserver {

    /**
     * 应用进入后台
     * Application turn to background
     */
    public void turnToBackground() {
    }

    /**
     * 应用进入前台
     * Application turn to foreground
     */
    public void turnToForeground() {
    }
}
