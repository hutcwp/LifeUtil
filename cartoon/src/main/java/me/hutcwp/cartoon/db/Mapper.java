package me.hutcwp.cartoon.db;

import androidx.annotation.NonNull;
import me.hutcwp.cartoon.bean.Comic;
import me.hutcwp.cartoon.net.ComicResponse;

/**
 * Created by hutcwp on 2019-06-01 23:25
 *
 *
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

    public static Comic ComicNetToBean(@NonNull ComicResponse comicResponse) {
        Comic comic = new Comic();
        comic.setName(comicResponse.getTitle());
        comic.setChapter(comicResponse.getCur_chapter());
        comic.setPage(comicResponse.getPage());
        comic.setType("0");
        comic.setUrl(comicResponse.getPage_url());
        return comic;
    }


}
