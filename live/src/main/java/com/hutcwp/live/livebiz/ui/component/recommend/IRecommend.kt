package com.hutcwp.live.livebiz.ui.component.recommend

import com.hutcwp.live.livebiz.ui.component.bean.PlayInfo
import hut.cwp.mvp.MvpView

/**
 *
 * Created by hutcwp on 2020-03-08 02:31
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
interface IRecommend : MvpView {
    fun updatePlayList(playInfo: PlayInfo?)
}