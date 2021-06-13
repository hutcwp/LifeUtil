package me.hutcwp.cartoon.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.drakeet.multitype.MultiTypeAdapter
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.crt_activity_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.core.ComicNetCore
import me.hutcwp.cartoon.core.Params
import me.hutcwp.cartoon.list.ComicItem
import me.hutcwp.cartoon.list.ComicItemViewBinder
import me.hutcwp.cartoon.util.SpUtils

@Route(path = "/comic/list")
class ListComicActivity : AppCompatActivity() {

    private var adapter: MultiTypeAdapter? = null
    private var items: MutableList<Any> = mutableListOf()

    var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersive()
        setContentView(R.layout.crt_activity_list_comic)
        initRefreshLayout()
        initListView()
        initComicCore()
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter?.register(ComicItemViewBinder(this@ListComicActivity))
        adapter?.items = items
        adapter?.notifyDataSetChanged()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (hasMore) {

                    }
                } else if (!recyclerView.canScrollVertically(-1)) {
                    if (hasMore) {
                    }
                }
            }
        })
    }

    private fun initRefreshLayout() {
        refresh_layout
                .setEnableNestedScroll(true)
                .setEnableRefresh(true)
                .setEnableLoadMore(true)
                .setRefreshHeader(ClassicsHeader(this))
                .setRefreshFooter(ClassicsFooter(this))
                .setOnRefreshListener {
                    GlobalScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.IO) {
                            loadPreData(true)
                        }
                        refresh_layout?.finishRefresh()
                    }
                }
                .setOnLoadMoreListener {
                    GlobalScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.IO) {
                            loadPreData(false)
                        }
                        refresh_layout?.finishLoadMore()
                    }
                }
    }

    private suspend fun loadPreData(isPre: Boolean) {
        var count = 0
        if (isPre) {
            ComicNetCore.getPreChapter().forEach {
                val title = "${it.chapter}节/${it.page}页"
                val img = it.url
                val comicItem = ComicItem(title, img)
                items.add(0, comicItem)
                count++
            }
        } else {
            ComicNetCore.getNextChapter().forEach {
                val title = "${it.chapter}节/${it.page}页"
                val img = it.url
                val comicItem = ComicItem(title, img)
                items.add(comicItem)
                count++
            }
        }

        runOnUiThread {
            adapter?.items = items
            if (isPre) {
                adapter?.notifyItemRangeInserted(0, count)
            } else {
                adapter?.notifyItemRangeInserted(items.size - count, count)
                adapter?.notifyItemRangeInserted(items.size - count, count)
            }
        }
    }

    private suspend fun initData() {
        items = ArrayList()
        ComicNetCore.getCurrentChapter()?.forEach {
            val title = "${it.chapter}节/${it.page}页"
            val img = it.url
            val comicItem = ComicItem(title, img)
            items.add(comicItem)
        }
        runOnUiThread {
            adapter?.items = items
            adapter?.notifyDataSetChanged()
        }
    }

    private fun initComicCore() {
        val params = Params()
        params.name = "妖神记"
        params.chapter = SpUtils.getInt(KEY_CHAPTER, 1, this@ListComicActivity)
        params.page = SpUtils.getInt(KEY_PAGE, 1, this@ListComicActivity)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                ComicNetCore.init(params)
                initData()
            }
        }
    }

    /**
     * 全屏
     */
    private fun immersive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LOW_PROFILE
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onDestroy() {
        super.onDestroy()
        SpUtils.putInt(KEY_PAGE, ComicNetCore.mCurrentPage, this)
        SpUtils.putInt(KEY_CHAPTER, ComicNetCore.mCurrentChapter, this)
    }

    companion object {
        const val TAG = "ListComicActivity"
        private const val KEY_PAGE = "page"
        private const val KEY_CHAPTER = "chapter"
    }
}
