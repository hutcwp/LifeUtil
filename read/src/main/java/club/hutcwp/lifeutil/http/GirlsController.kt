package club.hutcwp.lifeutil.http

import club.hutcwp.lifeutil.entitys.ArticleDetailBean
import club.hutcwp.lifeutil.entitys.NewRead
import club.hutcwp.lifeutil.entitys.NewReadInfo
import club.hutcwp.lifeutil.entitys.RandomGirl
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
    suspend fun getNewGankAsyc(@Path("no") page: Int): NewGankRepsponse

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
