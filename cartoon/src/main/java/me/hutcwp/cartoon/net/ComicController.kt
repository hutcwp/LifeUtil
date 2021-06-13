package me.hutcwp.cartoon.net

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */
interface ComicController {

    //http://localhost:8080/carton/getpages

    @GET("http://192.168.1.104:8080/carton/getpages")
    suspend fun getCartonByChapter(@Query("carton_id") page: Int, @Query("chapter") rows: Int): List<ComicResponse>

}
