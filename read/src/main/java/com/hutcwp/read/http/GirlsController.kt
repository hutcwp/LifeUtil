package com.hutcwp.read.http

import com.hutcwp.read.entitys.ArticleDetailBean
import com.hutcwp.read.entitys.NewRead
import com.hutcwp.read.entitys.NewReadInfo
import com.hutcwp.read.entitys.RandomGirl
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

interface GirlsController {

    @GET("https://gank.io/api/v2/data/category/Girl/type/Girl/page/{no}/count/10")
    suspend fun getNewGankAsyc(@Path("no") page: Int): NewGankResponse

    @GET("https://gank.io/api/v2/random/category/Girl/type/Girl/count/1")
    suspend fun getNewGankRandom(): RandomGirl


    @GET("https://gank.io/api/v2/data/category/GanHuo/type/{category}/page/{no}/count/10")
    suspend fun getReadCategory(@Path("category") category: String, @Path("no") page: Int): NewReadInfo

    @GET("https://gank.io/api/v2/categories/GanHuo")
    suspend fun getReadList(): NewRead

    @GET("https://gank.io/api/v2/data/category/Article/type/{category}/page/{no}/count/10")
    suspend fun getArticleCategory(@Path("category") category: String, @Path("no") page: Int): NewReadInfo

    @GET("https://gank.io/api/v2/categories/Article")
    suspend fun getArticleList(): NewRead

    @GET("https://gank.io/api/v2/post/{id}")
    suspend fun getArticleDetail(@Path("id") id: String): ArticleDetailBean
}
