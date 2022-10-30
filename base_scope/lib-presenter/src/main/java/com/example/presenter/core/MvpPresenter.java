package com.example.presenter.core;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.presenter.util.CompatOptional;

/**
 * base presenter for referring to the attach view
 */

public class MvpPresenter<V extends MvpView> {

    private V viewRef;

    @Nullable
    protected V getView(){
        return viewRef;
    }

    /**
     * 保护空指针
     * @return
     */
    protected CompatOptional<V> getMvpView(){
        return CompatOptional.ofNullable(getView());
    }


    /**
     * attach fragment/activity  指针
     * @param view
     */
    protected void attachView(V view){
        viewRef = view;
    }

    /**
     * 对应 activity/fragment onCreate
     */
    protected void onCreate(Bundle savedInstanceState){}

    /**
     * 对应 activity/fragment onDestroy
     */
    protected void onDestroy() {
        if (viewRef != null) {
            viewRef = null;
        }
    }

    /**
     * 对应 activity/fragment onStart
     */
    protected void onStart(){}

    /**
     * 对应 activity/fragment onResume
     */
    protected void onResume(){}

    /**
     * 对应 activity/fragment onPause
     */
    protected void onPause(){}

    /**
     * 对应 activity/fragment onStop
     */
    protected void onStop(){}

}
