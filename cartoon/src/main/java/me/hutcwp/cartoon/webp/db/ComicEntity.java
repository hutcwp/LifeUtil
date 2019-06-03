package me.hutcwp.cartoon.webp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by hutcwp on 2019-06-01 16:53
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 漫画实体类
 **/
@Entity
public class ComicEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name; //漫画名

    @ColumnInfo(name = "type")
    private String type; // 话/印之类的

    @ColumnInfo(name = "chapter")
    private int chapter; // 章

    @ColumnInfo(name = "page")
    private int page; // 页

    @ColumnInfo(name = "url")
    private String url; //当前页的图片地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ComicEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", chapter=" + chapter +
                ", page=" + page +
                ", url='" + url + '\'' +
                '}';
    }
}
