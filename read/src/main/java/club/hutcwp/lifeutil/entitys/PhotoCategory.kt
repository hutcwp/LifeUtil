package club.hutcwp.lifeutil.entitys

/**
 * Created by hutcwp on 2017/4/16.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class PhotoCategory(type: Int, var name: String?, var url: String?) {
    var type = 0 // 0表示走api ，1表示抓包

    init {
        this.type = type
    }
}
