package club.hutcwp.lifeutil.ui.home.sub

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.ui.base.BaseFragment
import club.hutcwp.lifeutil.ui.home.adpter.NewsAdapter
import club.hutcwp.lifeutil.ui.home.sub.news.INews
import hut.cwp.mvp.BindPresenter
import kotlinx.android.synthetic.main.read_fragment_category.*
import me.hutcwp.log.MLog

/**
 * 阅读的子类
 */
@BindPresenter(presenter = SubPresenter::class)
open abstract class SubFragment : BaseFragment<SubPresenter, INews>(), INews {
    override val data: List<News>
        get() = if (adapter != null && adapter!!.data != null) {
            adapter!!.data!!
        } else {
            listOf()
        }

    private var adapter: NewsAdapter? = null

//    override fun getLayoutId(): Int {
//        return R.layout.read_fragment_category
//    }

    override fun initViews() {
        setListener()
    }

    override fun lazyFetchData() {
        presenter.getDataFromServer(false)
    }

    private fun setListener() {
        MLog.info(TAG, "setListener run")
        if (adapter == null) {
            adapter = NewsAdapter(context!!, listOf<News>().toMutableList())
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        swipeRefreshLayout.setOnRefreshListener { presenter.getDataFromServer(false) }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getDataFromServer(true)
                }
            }
        })
    }

    override fun setRefreshing(status: Boolean) {
        swipeRefreshLayout?.isRefreshing = status
    }

    override fun setNewData(data: List<News>) {
        MLog.info(TAG, "setNewData = " + data.size)
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(data: List<News>) {
    }

    override fun addNewData(pos: Int, data: List<News>) {
        MLog.info(TAG, "addNewData = " + data.size)
        adapter?.addData(pos, data)
    }

    companion object {
        const val TAG = "SubFragment"
    }
}
