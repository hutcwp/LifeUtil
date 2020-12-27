package me.hutcwp.cartoon.net

import android.content.Context
import android.util.Log
import me.hutcwp.cartoon.db.AppDatabase
import me.hutcwp.cartoon.db.ComicEntity

/**
 *  author : kevin
 *  date : 2020/12/27 11:16 PM
 *  description :
 */
object ComicNetRepo {

    private val TAG = "ComicNetRepo"

    fun getComic(context: Context?, type: String?, chapter: Int, page: Int): ComicEntity? {
        Log.i(TAG, "getComic")
        return AppDatabase.getInstance(context).comicDao().getByPage(type, chapter, page)
    }

    suspend fun getComicsByChapter(chapter: Int): List<ComicResponse> {
        Log.i(TAG, "getComicsByChapter: chapter=$chapter")
        return ApiFactory.getComicController().getCartonByChapter(0, chapter)
    }

    fun getAll(context: Context?): List<ComicEntity?>? {
        Log.i(TAG, "getAll")
        return AppDatabase.getInstance(context).comicDao().all
    }


}