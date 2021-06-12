package com.hutcwp.read.ui.home.sub.news

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.read.R
import com.hutcwp.read.ui.home.adpter.NewsAdapter
import com.hutcwp.read.entitys.News
import com.hutcwp.read.ui.base.BaseFragment
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_category.*

/**
 * 阅读的子类
 */
@DelegateBind(presenter = NewsPresenter::class)
class NewsFragment : BaseFragment<NewsPresenter, INews>(), INews {
    override val data: List<News>
        get() = if (adapter != null && adapter!!.data != null) {
            adapter!!.data!!
        } else {
            listOf()
        }

    private var adapter: NewsAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_category
    }

    override fun initViews() {
        setListener()
    }

    override fun lazyFetchData() {
        presenter.getDataFromServer(false)
    }

    private fun setListener() {
        Log.i(TAG, "setListener run")
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
        Log.i(TAG, "setNewData = " + data.size)
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(data: List<News>) {
    }

    override fun addNewData(pos: Int, data: List<News>) {
        Log.i(TAG, "addNewData = " + data.size)
        adapter?.addData(pos, data)
    }

    companion object {
        const val TAG = "NewsFragment"
    }
}
