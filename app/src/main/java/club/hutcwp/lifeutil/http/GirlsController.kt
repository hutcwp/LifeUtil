package club.hutcwp.lifeutil.http

import club.hutcwp.lifeutil.entitys.GankGirlPhoto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

interface GirlsController {
    @GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/{page}")
    fun getGank(@Path("page") page: String): Observable<BaseGankResponse<List<GankGirlPhoto>>>

    @GET("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/{page}")
    fun getGankBody(@Path("page") page: String): Observable<ResponseBody>
}
