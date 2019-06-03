package me.hutcwp.cartoon.webp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by hutcwp on 2019-06-01 17:07
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
@Dao
public interface ComicDao {
    @Insert
    void insert(ComicEntity comicEntity);

    @Insert
    void insertAll(List<ComicEntity> comicEntities);

    @Delete
    void delete(ComicEntity comicEntity);

    @Delete
    void deleteAll(List<ComicEntity> comicEntities);

    @Update
    void update(ComicEntity comicEntity);

    @Query("SELECT * FROM ComicEntity")
    List<ComicEntity> getAll();

    @Query("SELECT * FROM ComicEntity WHERE type = :type and chapter = :chapter and page  = :page")
    ComicEntity getByPage(String type, int chapter, int page);

     @Query("SELECT * FROM ComicEntity WHERE name = :name and chapter = :chapter")
    List<ComicEntity> getComicsByChapter(String name, int chapter);


}