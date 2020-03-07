package club.hutcwp.lifeutil.ui.home.sub.news

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ReadAdapter
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.mvp.BindPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import me.hutcwp.log.MLog
import me.hutcwp.util.RxUtils

/**
 * 阅读的子类
 */
@BindPresenter(presenter = NewsPresenter::class)
class NewsFragment : BaseFragment<NewsPresenter, INews>(), INews {

    private var adapter: ReadAdapter? = null
    private var disposable: Disposable? = null
    private var recycleView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var curPage = 0

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_category
    }

    override fun initViews() {
        recycleView = rootView.findViewById(R.id.recyclerView)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        adapter = ReadAdapter(context!!, listOf<News>().toMutableList())
        setListener()
    }

    override fun lazyFetchData() {
        RxUtils.dispose(disposable)
        disposable = presenter.getDataFromServer(presenter.getUrl(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { list ->
                    setRefreshing(false)
                    setNewData(list)
                    curPage++
                }, RxUtils.errorConsumer(TAG, "getDataFromServer error, see error below."))

    }

    /**
     * 设置监听等
     */
    private fun setListener() {
        recycleView?.adapter = adapter
        recycleView?.layoutManager = LinearLayoutManager(activity)
        swipeRefreshLayout?.setOnRefreshListener {
            RxUtils.dispose(disposable)
            disposable = presenter.getDataFromServer(presenter.getUrl(0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { list ->
                        setRefreshing(false)
                        setNewData(list)
                    }, RxUtils.errorConsumer(TAG, "getDataFromServer error, see error below."))
        }
    }

    override fun setRefreshing(status: Boolean) {
        swipeRefreshLayout?.isRefreshing = status
    }

    override fun setNewData(data: List<News>) {
        MLog.info(TAG, "setNewData = " + data.size)
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(pos: Int, data: List<News>) {
        MLog.info(TAG, "addNewData = " + data.size)
        adapter?.addData(pos, data)
    }


    companion object {
        private const val TAG = "NewsFragment"
    }
}
