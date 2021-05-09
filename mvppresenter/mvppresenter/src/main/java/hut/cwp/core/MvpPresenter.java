package hut.cwp.core;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hut.cwp.util.CompatOptional;

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
     * 获取activity/fragment中Bundle
     * @return Bundle
     */
    public Bundle getArguments(){
        if(viewRef != null){
            if(viewRef instanceof Activity && ((Activity) viewRef).getIntent() != null){
                return ((Activity) viewRef).getIntent().getExtras();
            }else if(viewRef instanceof Fragment){
                return ((Fragment) viewRef).getArguments();
            }
        }
        return null;
    }

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
