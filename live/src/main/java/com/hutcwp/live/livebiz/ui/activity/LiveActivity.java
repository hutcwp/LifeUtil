package com.hutcwp.live.livebiz.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hutcwp.live.livebiz.ui.component.danmu.DanmuComponent;
import com.hutcwp.live.livebiz.ui.component.publicmessage.PublicMessageComponent;
import com.hutcwp.live.livebiz.ui.component.recommend.RecommendComponent;
import com.hutcwp.live.livebiz.ui.component.video.VideoComponent;
import com.hutcwp.livebiz.R;
import com.hutcwp.livebiz.R2;

import androidx.annotation.Nullable;
import hut.cwp.annotations.InitAttrConfig;
import hut.cwp.annotations.InitAttrConfigs;
import hut.cwp.api.Injector;
import hut.cwp.mvp.BindPresenter;
import hut.cwp.mvp.MvpActivity;


@InitAttrConfigs({
        @InitAttrConfig(component = PublicMessageComponent.class, resourceId = R2.id.public_message_view),
//        @InitAttrConfig(component = RecommendComponent.class, resourceId = R2.id.recommend_view),
//        @InitAttrConfig(component = VideoComponent.class, resourceId = R2.id.video_view),
        @InitAttrConfig(component = DanmuComponent.class, resourceId = R2.id.danmu_view)
})
@BindPresenter(presenter = LivePresenter.class)
@Route(path = "/live/main")
public class LiveActivity extends MvpActivity<LivePresenter, ILiveActivity> implements ILiveActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        Injector.injectContainer(this);
    }
}


