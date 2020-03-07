package club.hutcwp.lifeutil.ui.home.top

import android.widget.Toast
import club.hutcwp.lifeutil.entitys.ReadCategory
import hut.cwp.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.hutcwp.BaseConfig
import me.hutcwp.log.MLog
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*


/**
 * Created by hutcwp on 2018/10/13 17:05
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
class ReadPresenter : MvpPresenter<ReadFragment>() {

    private var disposable: Disposable? = null

    /**
     * 获取分类
     */
    fun getCategory() {
        val host = "http://gank.io/xiandu"
        disposable?.dispose()
        disposable = Observable.just(host).subscribeOn(Schedulers.io()).map {
            val list = ArrayList<ReadCategory>()
            try {
                val doc = Jsoup.connect(host).timeout(5000).get()
                val cate = doc.select("div#xiandu_cat").first()
                val links = cate.select("a[href]")
                for (element in links) {
                    val category = ReadCategory()
                    category.name = element.text()
                    category.url = element.attr("abs:href")
                    list.add(category)
                    MLog.debug("test", "name: " + category.name!!)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            if (list.size > 0) {
                list[0].url = "$host/wow"
            }
            list
        }.observeOn(AndroidSchedulers.mainThread()).subscribe({ readCategories ->
            view?.initTabLayout(readCategories)
        }, { throwable ->
            MLog.info("cwp", "t = $throwable")
            Toast.makeText(BaseConfig.getApplicationContext(), "解析发生过程!", Toast.LENGTH_SHORT).show()
        })
    }

    public override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

}
