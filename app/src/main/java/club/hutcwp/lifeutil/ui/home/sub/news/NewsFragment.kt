package club.hutcwp.lifeutil.ui.home.sub.news

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.ReadAdapter
import club.hutcwp.lifeutil.databinding.FragmentCategoryBinding
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.mvp.BindPresenter

/**
 * 阅读的子类
 */
@BindPresenter(presenter = NewsPresenter::class)
class NewsFragment : BaseFragment<NewsPresenter, INews>(), INews {
    override val data: List<News>
        get() = if (adapter != null && adapter!!.data != null) {
            adapter!!.data!!
        } else {
            listOf()
        }

    private var adapter: ReadAdapter? = null

    private var fragmentCategoryBinding: FragmentCategoryBinding? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initViews() {
        fragmentCategoryBinding = binding as FragmentCategoryBinding
        setListener()
    }

    override fun lazyFetchData() {
        presenter.getDataFromServer()
    }

    /**
     * 设置监听等
     */
    private fun setListener() {
        fragmentCategoryBinding?.run {
            Log.i(TAG, "setListener run")
            if (adapter == null) {
                adapter = ReadAdapter(context!!, listOf<News>().toMutableList())
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
            swipeRefreshLayout.setOnRefreshListener { presenter.getDataFromServer() }
        }
    }

    override fun setRefreshing(status: Boolean) {
        fragmentCategoryBinding?.swipeRefreshLayout?.isRefreshing = status
    }

    override fun setNewData(data: List<News>) {
        Log.i(TAG, "setNewData = " + data.size)
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(pos: Int, data: List<News>) {
        Log.i(TAG, "addNewData = " + data.size)
        adapter?.addData(pos, data)
    }

    companion object {
        const val TAG = "NewsFragment"
    }
}
