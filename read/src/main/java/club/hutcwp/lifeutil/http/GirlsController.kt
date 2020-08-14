package club.hutcwp.lifeutil.http

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

}
