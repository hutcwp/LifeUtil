package me.hutcwp.cartoon.webp.core

import androidx.annotation.NonNull
import me.hutcwp.cartoon.webp.bean.Comic

/**
 * Created by hutcwp on 2019-06-01 23:09
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
object ComicCore {
    var mCurrentChapter = 1
    var mCurrentPage = 1
    var mCurrentName = "comic"
    var mPages = mutableListOf<Comic>()


    fun init(@NonNull params: Params) {
        mCurrentName = params.name
        mCurrentChapter = params.chapter
        mCurrentPage = params.page

        initPagesByDB()
    }

    private fun initPagesByDB() {
//        ComicDBRepo.getComicsByChapter()
    }


}
