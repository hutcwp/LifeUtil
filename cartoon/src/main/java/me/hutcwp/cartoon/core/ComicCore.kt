package me.hutcwp.cartoon.core

import android.util.Log
import androidx.annotation.NonNull
import io.reactivex.Observable
import me.hutcwp.BasicConfig
import me.hutcwp.cartoon.bean.Comic
import me.hutcwp.cartoon.db.ComicDBRepo
import me.hutcwp.cartoon.db.Mapper

/**
 * Created by hutcwp on 2019-06-01 23:09
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
object ComicCore {

    private const val TAG = "ComicCore"
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
        mPages.clear()
        ComicDBRepo.getComicsByChapter(
                BasicConfig.getApplicationContext(),
                mCurrentName,
                mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicDBToBean(it))
                }
    }

    fun getNextChapter() {
        mPages.clear()
        mCurrentChapter++
        ComicDBRepo.getComicsByChapter(
                BasicConfig.getApplicationContext(),
                mCurrentName,
                mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicDBToBean(it))
                }
    }

    fun getCurrentChapter() {
        mPages.clear()
        ComicDBRepo.getComicsByChapter(
                BasicConfig.getApplicationContext(),
                mCurrentName,
                mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicDBToBean(it))
                }
    }

    fun getPreChapter() {
        mPages.clear()
        mCurrentChapter--
        ComicDBRepo.getComicsByChapter(
                BasicConfig.getApplicationContext(),
                mCurrentName,
                mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicDBToBean(it))
                }
    }

    fun getNextPage(): Observable<String> {
        mCurrentPage++
        if (mCurrentPage <= mPages.size) {
            if (mPages[mCurrentPage - 1].drawable == null) {

            }

            return Observable.just(mPages[mCurrentPage - 1].url)
        }
        return Observable.just("")
    }

    fun getCurPage(): Observable<String> {
        mCurrentPage
        if (mCurrentPage > 0 && mCurrentPage <= mPages.size) {
            return Observable.just(mPages[mCurrentPage - 1].url)
        }
        return Observable.just("")
    }

    fun getPrePage(): Observable<String> {
        mCurrentPage--
        if (mCurrentPage > 0 && mCurrentPage <= mPages.size) {
            return Observable.just(mPages[mCurrentPage - 1].url)
        }
        return Observable.just("")
    }
}
