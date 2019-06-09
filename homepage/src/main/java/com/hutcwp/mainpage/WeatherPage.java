package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2019-06-09 23:28
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class WeatherPage implements IMainPage {
    @Override
    public String getName() {
        return "今日天气";
    }

    @Override
    public String getPath() {
        return "/weather/select";
    }
}
