package club.hutcwp.lifeutil.ui.home.sub.news

import club.hutcwp.lifeutil.core.NewsParseImpl
import club.hutcwp.lifeutil.entitys.News
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers


/**
 * Created by hutcwp on 2018/10/13 22:33
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class NewsPresenter : MvpPresenter<INews>() {

    private var disposable: Disposable? = null
    private var curPage = 0

    fun getDataFromServer() {
        val baseUrl = arguments.getString("url")
        val url = "$baseUrl/page/$curPage"

        view!!.setRefreshing(true)
        disposable = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map { url -> NewsParseImpl().parseHtmlFromUrl(url) }.observeOn(AndroidSchedulers.mainThread()).subscribe { list ->
                    view!!.setRefreshing(false)
                    curPage++
                    if (view!!.data == null || view!!.data.size == 0) {
                        view!!.setNewData(list)
                    } else {
                        view!!.addNewData(view!!.data.size, list)
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed)
            disposable!!.dispose()
    }
}
