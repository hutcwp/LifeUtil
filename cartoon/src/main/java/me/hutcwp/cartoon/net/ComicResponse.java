package me.hutcwp.cartoon.net;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * author : kevin
 * date : 2020/12/27 11:27 PM
 * description :
 */
public class ComicResponse implements Serializable {

    private String title;
    private int carton_id;
    private int cur_chapter;
    private String page_url;
    private int page;
    private int id;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCarton_id(int carton_id) {
        this.carton_id = carton_id;
    }

    public int getCarton_id() {
        return carton_id;
    }

    public void setCur_chapter(int cur_chapter) {
        this.cur_chapter = cur_chapter;
    }

    public int getCur_chapter() {
        return cur_chapter;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ComicResponse{" +
                "title='" + title + '\'' +
                ", carton_id=" + carton_id +
                ", cur_chapter=" + cur_chapter +
                ", page_url='" + page_url + '\'' +
                ", page=" + page +
                ", id=" + id +
                '}';
    }
}
