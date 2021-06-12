package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2019-06-09 23:28
 *
 *
 **/
public class CartoonPage implements IMainPage {
    @Override
    public String getName() {
        return "漫画首页";
    }

    @Override
    public String getPath() {
        return "/cartoon/demo";
    }
}
