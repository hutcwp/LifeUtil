package com.hutcwp.live.livebiz.ui.component.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hutcwp on 2020-03-08 00:03
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PlayInfo {

    @SerializedName("musics")
    public List<Playable> playInfoList = null;

    public List<Playable> getPlayInfoList() {
        return playInfoList;
    }

    public void setPlayInfoList(List<Playable> playInfoList) {
        this.playInfoList = playInfoList;
    }
}
