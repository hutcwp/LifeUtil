package me.hutcwp.cartoon.webp.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class SimpleOnGestureListener implements GestureDetector.OnGestureListener {
    private static final String TAG = "SimpleOnGestureListener";

    private boolean isVerticalScroll;
    private boolean isFirstScrollAfterDown;
    private SwipeViewDelegate mSwipeViewDelegate;

    private int mMinFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();

    public void setmSwipeViewDelegate(SwipeViewDelegate mSwipeViewDelegate) {
        this.mSwipeViewDelegate = mSwipeViewDelegate;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown");
        isFirstScrollAfterDown = true;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll");
        if (isFirstScrollAfterDown) {
            isFirstScrollAfterDown = false;
            boolean vertical = Math.abs(distanceY) >= Math.abs(distanceX);
            int mode;
            if (vertical) {
                if (distanceY < 0) {
                    //从上往下拉
                } else {
                }
            } else {
                if (distanceX < 0) {
                } else {

                }
            }
            if (isVerticalScroll != vertical) {
                isVerticalScroll = vertical;
            }
        }
        if (isVerticalScroll) {
            distanceX = 0;
        } else {
            distanceY = 0;
        }
        boolean ret = false;
        return ret;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG, "onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "onFling");
        boolean ret = false;
        if (mSwipeViewDelegate != null) {
            if (isVerticalScroll && Math.abs(velocityY) > mMinFlingVelocity ) {
                if (e2.getY() - e1.getY() > 0) {
                    ret = mSwipeViewDelegate.flingToBottom(e1, e2, velocityX, velocityY);
                } else {
                    ret = mSwipeViewDelegate.flingToTop(e1, e2, velocityX, velocityY);
                }
            } else if (!isVerticalScroll && Math.abs(velocityX) > mMinFlingVelocity) {
                if (e2.getX() - e1.getX() > 0) {
                    ret = mSwipeViewDelegate.flingToRight(e1, e2, velocityX, velocityY);
                } else {
                    ret = mSwipeViewDelegate.flingToLeft(e1, e2, velocityX, velocityY);
                }
            } else {
                ret = false;
            }
        }
        return ret;
    }

    public interface SwipeViewDelegate {
        boolean flingToLeft(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

        boolean flingToRight(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

        boolean flingToTop(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);

        boolean flingToBottom(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    }
}
