package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2019-06-09 23:33
 *
 *
 **/

public class CartoonLoadPage implements IMainPage {
    @Override
    public String getName() {
        return "漫画List";
    }

    @Override
    public String getPath() {
        return "/comic/list";
    }
}
