package me.hutcwp.cartoon.webp.db;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by hutcwp on 2019-06-01 17:37
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class ComicDBRepo {

    private static final String TAG = "ComicDBRepo";

    public static void insert(Context context, ComicEntity comicEntity) {
        Log.i(TAG, "insert");
        if (AppDatabase.getInstance(context).comicDao().getByPage(
                comicEntity.getType(),
                comicEntity.getChapter(),
                comicEntity.getPage()) == null) {
            AppDatabase.getInstance(context).comicDao().insert(comicEntity);
        } else {
            Log.i(TAG, "插入数据失败，ComicEntity = " + comicEntity.toString());
        }
    }

    public static ComicEntity getComic(Context context, String type, int chapter, int page) {
        Log.i(TAG, "getComic");
        return AppDatabase.getInstance(context).comicDao().getByPage(type, chapter, page);
    }

    public static ComicEntity getComicsByChapter(Context context, String name, int chapter) {
        Log.i(TAG, "getComicsByChapter");
        return AppDatabase.getInstance(context).comicDao().getComicsByChapter(name, chapter);
    }

    public static List<ComicEntity> getAll(Context context) {
        Log.i(TAG, "getAll");
        return AppDatabase.getInstance(context).comicDao().getAll();
    }

    public static void delete(Context context, ComicEntity comicEntity) {
        Log.i(TAG, "delete");
        AppDatabase.getInstance(context).comicDao().delete(comicEntity);
    }

    public static void deleteAll(Context context) {
        Log.i(TAG, "deleteAll");
        AppDatabase.getInstance(context).comicDao().deleteAll(AppDatabase.getInstance(context).comicDao().getAll());
    }

}
