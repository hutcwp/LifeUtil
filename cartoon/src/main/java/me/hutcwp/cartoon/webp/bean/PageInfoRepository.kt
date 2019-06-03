package me.hutcwp.cartoon.webp.bean

import android.os.Handler
import android.os.Looper
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.webp.core.ComicCore

/**
 *
 * Created by hutcwp on 2019-06-02 16:34
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class PageInfoRepository {

//    private val fakeRemoteResource = listOf(
//            PageInfo(R.drawable.b, "接受挑战"),
//            PageInfo(R.drawable.b, "钢铁侠指日可待"),
//            PageInfo(R.drawable.b, "才知道原来有这个功能"),
//            PageInfo(R.drawable.b, "google拍下摔跤瞬间"),
//            PageInfo(R.drawable.b, "惊天一锤"),
//            PageInfo(R.drawable.b, "为了吃提高智商"),
//            PageInfo(R.drawable.b, "趁没人注意赶紧走"),
//            PageInfo(R.drawable.b, "可以说明质量好多了")
//    )

    private var fakeRemoteResource = mutableListOf<ComicPageInfo>()

    private val handler = Handler(Looper.getMainLooper())



    fun loadCurrentData() {
        val datas: MutableList<ComicPageInfo> = mutableListOf()
        ComicCore.mPages.forEach {
            val title = "chapter=${it.chapter} , page=${it.page}"
            val data = ComicPageInfo(it.url, title)
            datas.add(data)
        }
        fakeRemoteResource = datas
    }

    private fun loadNextChapter(){
        ComicCore.getNextChapter()
        val datas: MutableList<ComicPageInfo> = mutableListOf()
        ComicCore.mPages.forEach {
            val title = "chapter=${it.chapter} , page=${it.page}"
            val data = ComicPageInfo(it.url, title)
            datas.add(data)
        }
        fakeRemoteResource.addAll(datas)
    }

    private fun loadPreChapter(){
        val datas: MutableList<ComicPageInfo> = mutableListOf()
        ComicCore.mPages.forEach {
            val title = "chapter=${it.chapter} , page=${it.page}"
            val data = ComicPageInfo(it.url, title)
            datas.add(data)
        }
        datas.addAll(fakeRemoteResource)
        fakeRemoteResource = datas
    }
}