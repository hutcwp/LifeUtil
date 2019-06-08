package me.hutcwp.cartoon.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by hutcwp on 2019-06-01 23:23
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class Comic {

    private String name; //漫画名

    private String type; // 话/印之类的

    private int chapter; // 章


    private int page; // 页


    private String url; //当前页的图片地址

    private Drawable drawable; //图片

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", chapter=" + chapter +
                ", page=" + page +
                ", url='" + url + '\'' +
                ", drawable=" + drawable +
                '}';
    }
}
