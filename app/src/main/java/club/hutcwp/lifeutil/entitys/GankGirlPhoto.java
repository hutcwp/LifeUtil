package club.hutcwp.lifeutil.entitys;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class GankGirlPhoto extends Photo implements Serializable {

    @SerializedName("url")
    private String url;

    @SerializedName("who")
    String name;

    @SerializedName("desc")
    String date;

    public GankGirlPhoto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getImg() {
        return url;
    }

    public void setImg(String url) {
        this.url = url;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

