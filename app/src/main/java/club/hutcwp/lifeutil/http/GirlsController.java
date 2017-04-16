package club.hutcwp.lifeutil.http;

import java.util.List;

import club.hutcwp.lifeutil.model.Girl;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public interface GirlsController {

    @GET ("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/{page}")
    Observable<BaseGankResponse<List<Girl>>> getGank(@Path("page") String page);

    @GET ("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/{page}")
    Observable<ResponseBody> getGankBody(@Path("page") String page);

}
