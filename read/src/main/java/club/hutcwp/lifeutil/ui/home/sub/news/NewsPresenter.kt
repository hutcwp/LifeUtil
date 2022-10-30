package club.hutcwp.lifeutil.ui.home.sub.news

import androidx.fragment.app.Fragment
import club.hutcwp.lifeutil.core.NewsParseImpl
import club.hutcwp.lifeutil.entitys.News
import com.example.presenter.core.MvpPresenter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog


/**
 * Created by hutcwp on 2018/10/13 22:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */


class NewsPresenter : MvpPresenter<INews>() {

    /**
     * 请求特定的某一页数据
     * @param url
     */
    fun getDataFromServer(url: String): Flowable<List<News>> {
        view?.setRefreshing(true)
        return Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map {
                    MLog.info(TAG, "getDataFromServer:  url= $it")
                    NewsParseImpl().parse(it)
                }.toFlowable(BackpressureStrategy.MISSING)
    }

    fun getUrl(curPage: Int): String {
        val baseUrl =( mvpView as Fragment).arguments?.getString("url")
        return "$baseUrl/page/$curPage"
    }

    companion object {
        private const val TAG = "NewsPresenter"
    }
}
