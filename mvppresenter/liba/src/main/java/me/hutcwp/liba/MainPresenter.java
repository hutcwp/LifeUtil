package me.hutcwp.liba;

import android.os.Bundle;

import hut.cwp.core.MvpPresenter;

/**
 * Created by hutcwp on 2018/8/17 23:44
 **/
public class MainPresenter extends MvpPresenter<IMain> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void click(){
        getView().changeText();
    }
}
