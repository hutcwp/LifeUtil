package club.hutcwp.lifeutil.http

import com.google.gson.annotations.SerializedName

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class BaseGankResponse<T> {

    var error: Boolean = false

    @SerializedName("results")
    var datas: T? = null
}
