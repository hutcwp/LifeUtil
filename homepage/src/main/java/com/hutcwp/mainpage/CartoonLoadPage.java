package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2019-06-09 23:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/

public class CartoonLoadPage implements IMainPage {
    @Override
    public String getName() {
        return "漫画爬虫";
    }

    @Override
    public String getPath() {
        return "/cartoon/load";
    }
}
