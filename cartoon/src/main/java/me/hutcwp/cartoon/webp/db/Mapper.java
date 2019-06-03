package me.hutcwp.cartoon.webp.db;

import androidx.annotation.NonNull;
import me.hutcwp.cartoon.webp.bean.Comic;

/**
 * Created by hutcwp on 2019-06-01 23:25
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class Mapper {

    public static Comic ComicDBToBean(@NonNull ComicEntity comicEntity) {
        Comic comic = new Comic();
        comic.setName(comicEntity.getName());
        comic.setChapter(comicEntity.getChapter());
        comic.setPage(comicEntity.getPage());
        comic.setType(comicEntity.getType());
        comic.setUrl(comicEntity.getUrl());
        return comic;
    }


}
