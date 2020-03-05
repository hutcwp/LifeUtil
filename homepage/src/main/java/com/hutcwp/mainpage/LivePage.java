package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2020-03-05 17:43
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class LivePage implements IMainPage {
    @Override
    public String getName() {
        return "直播间";
    }

    @Override
    public String getPath() {
        return "/live/main";
    }
}
