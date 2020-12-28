package me.hutcwp.cartoon.core

import android.util.Log
import androidx.annotation.NonNull
import io.reactivex.Observable
import me.hutcwp.cartoon.bean.Comic

import me.hutcwp.cartoon.db.Mapper
import me.hutcwp.cartoon.net.ComicNetRepo

/**
 *  author : kevin
 *  date : 2020/12/27 11:15 PM
 *  description :
 */
object ComicNetCore {

    private const val TAG = "ComicNetCore"

    var mCurrentChapter = 1
    var mCurrentPage = 1
    var mCurrentName = "comic"
    var mPages = mutableListOf<Comic>()

    suspend fun init(@NonNull params: Params) {
        mCurrentName = params.name
        mCurrentChapter = params.chapter
        mCurrentPage = params.page

        initPagesByNet()
    }

    private suspend fun initPagesByNet() {
        mPages.clear()
        ComicNetRepo.getComicsByChapter(mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicNetToBean(it))
                }
    }

    suspend fun getNextChapter(): MutableList<Comic> {
        mPages.clear()
        mCurrentChapter++
        Log.i(TAG, "before getComicsByChapter")
        ComicNetRepo.getComicsByChapter(mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicNetToBean(it))
                }
        Log.i(TAG, "after getComicsByChapter")

        return mPages
    }


    suspend fun getCurrentChapter(): MutableList<Comic> {
        if (mPages.isNullOrEmpty()) {
            return mPages
        }

        mPages.clear()
        ComicNetRepo.getComicsByChapter(mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicNetToBean(it))
                }
        return mPages
    }

    suspend fun getPreChapter(): MutableList<Comic> {
        mPages.clear()
        mCurrentChapter--
        ComicNetRepo.getComicsByChapter(mCurrentChapter)
                .forEach {
                    Log.i(TAG, "add: id = $it")
                    mPages.add(Mapper.ComicNetToBean(it))
                }

        return mPages
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
