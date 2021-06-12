package com.hutcwp.read.ui.home.impl.news

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.read.R
import com.hutcwp.read.entitys.News
import com.hutcwp.read.ui.base.BaseFragment
import com.hutcwp.read.ui.home.adpter.NewsAdapter
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_category.*

/**
 * 阅读的子类
 */
@DelegateBind(presenter = NewsPresenter::class)
class NewsFragment : BaseFragment<NewsPresenter, INews>(), INews {

    private val adapter by lazy { NewsAdapter() }


    override val data: List<News> get() = adapter.data.toList()


    override fun initViews() {
        initAdapter()
        setListener()
    }


    override fun getLayoutId(): Int {
        return R.layout.read_fragment_category
    }

    override fun lazyFetchData() {
        presenter.getDataFromServer(true)
    }

    override fun setListener() {
        swipeRefreshLayout.setOnRefreshListener { presenter.getDataFromServer(true) }
    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getDataFromServer(false)
                }
            }
        })
    }

    override fun setRefreshing(status: Boolean) {
        swipeRefreshLayout?.isRefreshing = status
    }

    override fun setNewData(data: List<News>) {
        Log.i(TAG, "setNewData = " + data.size)
        adapter.setNewInstance(data.toMutableList())
    }

    override fun addNewData(data: List<News>) {
    }

    override fun addNewData(pos: Int, data: List<News>) {
        Log.i(TAG, "addNewData = " + data.size)
        adapter.addData(pos, data)
    }

    override fun hasMore(hasMore: Boolean) {

    }


    companion object {
        const val TAG = "NewsFragment"
        private const val PARAM_URL = "url"

        fun instance(url: String): NewsFragment {
            val fragment = NewsFragment()
            val data = Bundle().apply {
                this.putString(PARAM_URL, url)
            }
            fragment.arguments = data
            return fragment
        }
    }

}
