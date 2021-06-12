package com.hutcwp.read.ui.home.impl.artical

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.read.R
import com.hutcwp.read.entitys.News
import com.hutcwp.read.ui.base.BaseFragment
import com.hutcwp.read.ui.home.adpter.ArticleAdapter
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_category.*

/**
 * 阅读的子类
 */
@DelegateBind(presenter = ArticlePresenter::class)
class ArticleFragment : BaseFragment<ArticlePresenter, IArticle>(), IArticle {

    private val adapter: ArticleAdapter by lazy { ArticleAdapter() }

    override val data: List<News> = adapter.data

    override fun getLayoutId(): Int = R.layout.read_fragment_category

    override fun initViews() {
        initAdapter()
        setListener()
    }

    override fun lazyFetchData() {
        presenter.getDataFromServer(true)
    }

    override fun setListener() {
        swipeRefreshLayout.setOnRefreshListener { presenter.getDataFromServer(true) }
    }

    private fun initAdapter() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
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
        adapter.setNewInstance(data.toMutableList())
    }

    override fun addNewData(data: List<News>) {

    }

    override fun addNewData(pos: Int, data: List<News>) {
        adapter.addData(pos, data)
    }

    override fun hasMore(hasMore: Boolean) {

    }


    companion object {

        private const val TAG = "ArticleFragment"
        private const val PARAM_URL = "url"

        fun instance(url: String): ArticleFragment {
            val fragment = ArticleFragment()
            val data = Bundle().apply {
                this.putString(PARAM_URL, url)
            }
            fragment.arguments = data
            return fragment
        }
    }
}
