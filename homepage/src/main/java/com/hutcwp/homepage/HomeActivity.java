package com.hutcwp.homepage;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/homepage/home")
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ARouter.getInstance().build("/cartoon/comic").navigation();
//        ARouter.getInstance().build("/cartoon/load").navigation();
//        ARouter.getInstance().build("/comic/list").navigation();
//        ARouter.getInstance().build("/cartoon/demo").navigation();
        ARouter.getInstance().build("/read/main").navigation();
    }
}
