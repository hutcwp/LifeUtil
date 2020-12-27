package me.hutcwp.cartoon.net

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

object ApiFactory {

    //用于同步处理
    private val monitor = Any()

    private var comicController: ComicController? = null

    fun getComicController(): ComicController {
        if (comicController == null) {
            synchronized(monitor) {
                comicController = RetrofitManager.getInstance().create(ComicController::class.java)
            }
        }
        return comicController!!
    }
}
