package com.hutcwp.mainpage;

import me.hutcwp.auto.IMainPage;

/**
 * Created by hutcwp on 2019-11-04 15:39
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class BlogPage implements IMainPage {

    @Override
    public String getName() {
        return "博客页面";
    }

    @Override
    public String getPath() {
        return "/blog/main";
    }
}
