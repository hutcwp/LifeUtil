package com.hutcwp.live.livebiz.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.annotations.InitAttrConfig;
import com.example.annotations.InitAttrConfigs;
import com.example.annotations.mvp.DelegateBind;
import com.example.presenter.Injector;
import com.example.presenter.core.MvpActivity;
import com.hutcwp.live.livebiz.ui.component.danmu.DanmuComponent;
import com.hutcwp.live.livebiz.ui.component.publicmessage.PublicMessageComponent;
import com.hutcwp.live.livebiz.ui.component.publicmessage.publicchatinput.PublicChatInputComponent;
import com.hutcwp.live.livebiz.ui.component.recommend.RecommendComponent;
import com.hutcwp.live.livebiz.ui.component.video.VideoComponent;
import com.hutcwp.livebiz.R;
import com.hutcwp.livebiz.R2;


@InitAttrConfigs({
    @InitAttrConfig(component = PublicMessageComponent.class, resourceId = R2.id.public_message_view),
    @InitAttrConfig(component = RecommendComponent.class, resourceId = R2.id.recommend_view),
    @InitAttrConfig(component = VideoComponent.class, resourceId = R2.id.video_view),
    @InitAttrConfig(component = DanmuComponent.class, resourceId = R2.id.danmu_view),
    @InitAttrConfig(component = PublicChatInputComponent.class, resourceId = R2.id.public_chat_input)
})
@DelegateBind(presenter = LivePresenter.class)
@Route(path = "/live/main")
public class LiveActivity extends MvpActivity<LivePresenter, ILiveActivity> implements ILiveActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        Injector.injectContainer(this);
    }

    public void autoLoadComponent(int resId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(resId, fragment).commitAllowingStateLoss();
    }

}


