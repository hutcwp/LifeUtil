package club.hutcwp.lifeutil.ui.home.sub.artical

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_category.*

/**
 * 阅读的子类
 */
@DelegateBind(presenter = ArticlePresenter::class)
class ArticleFragment : BaseFragment<ArticlePresenter, IArticle>(), IArticle {
    override val data: List<News>
        get() = if (adapter != null && adapter!!.data != null) {
            adapter!!.data!!
        } else {
            listOf()
        }

    private var adapter: ArticleAdapter? = null

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
            adapter = ArticleAdapter(context!!, listOf<News>().toMutableList())
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
        TODO("Not yet implemented")
    }

    override fun addNewData(pos: Int, data: List<News>) {
        Log.i(TAG, "addNewData = " + data.size)
        adapter?.addData(pos, data)
    }

    companion object {
        const val TAG = "NewsFragment"
    }
}
