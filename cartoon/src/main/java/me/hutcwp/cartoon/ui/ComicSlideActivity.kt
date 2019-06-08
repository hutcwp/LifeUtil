package me.hutcwp.cartoon.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.yy.mobile.widget.SlidableLayout
import com.yy.mobile.widget.SlideDirection
import kotlinx.android.synthetic.main.crt_activity_demo.*
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.bean.ComicPageInfo
import me.hutcwp.cartoon.core.ComicCore
import me.hutcwp.cartoon.core.Params
import me.hutcwp.cartoon.ui.fragment.FragmentAdapter
import me.hutcwp.cartoon.ui.fragment.SimpleListQueue
import me.hutcwp.cartoon.util.SpUtils

@Route(path = "/cartoon/demo")
class ComicSlideActivity : FragmentActivity() {

    //    private val dataList = SimpleListQueue<PageInfo>()
    private val dataList = SimpleListQueue<ComicPageInfo>()

//    private val repo = PageInfoRepository()

//    private var offset = 0 //当前移动量？

    private lateinit var slidableLayout: SlidableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersive()
        setContentView(R.layout.crt_activity_demo)
        slidableLayout = findViewById(R.id.slidable_layout)

        initComicCore()
        loadCurrentPage()
        initRefreshLayout()
        val adapter = FragmentAdapter(dataList, supportFragmentManager)
        slidableLayout.setAdapter(adapter)

    }

    private fun initComicCore() {
        val params = Params()
        params.name = "妖神记"
        params.chapter = SpUtils.getInt(KEY_CHAPTER, 1, this@ComicSlideActivity)
        params.page = SpUtils.getInt(KEY_PAGE, 1, this@ComicSlideActivity)
        ComicCore.init(params)
    }

    /**
     * 这里可替换为任意支持NestedScroll的刷新布局
     */
    private fun initRefreshLayout() {
        refresh_layout
                .setEnableNestedScroll(true)
                .setEnableRefresh(true)
                .setEnableLoadMore(true)
                .setRefreshHeader(ClassicsHeader(this))
                .setRefreshFooter(ClassicsFooter(this))
                .setOnRefreshListener {
                    requestDataAndAddToAdapter(true, 1000L)
                }
                .setOnLoadMoreListener {
                    requestDataAndAddToAdapter(false, 1000L)
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

    fun loadCurrentPage() {
        val newDatas = mutableListOf<ComicPageInfo>()
        ComicCore.mPages.forEach {
            val title = "${it.chapter}节/${it.page}页"
            val img = it.url
            val comicPageInfo = ComicPageInfo(img, title, it)
            newDatas.add(comicPageInfo)
        }
        dataList.addLast(newDatas)
        val page = SpUtils.getInt(KEY_PAGE, 1, this@ComicSlideActivity)
        dataList.setCurPage(page)
    }

    private fun requestDataAndAddToAdapter(insertToFirst: Boolean = true, delayMills: Long = 0L) {
        if (insertToFirst) {
            ComicCore.getPreChapter()
            Log.i(TAG, "load pre chapter.")
            val newDatas = mutableListOf<ComicPageInfo>()
            ComicCore.mPages.forEach {
                val title = "${it.chapter}节/${it.page}页"
                val img = it.url
                val comicPageInfo = ComicPageInfo(img, title, it)
                newDatas.add(comicPageInfo)
            }
            dataList.addFirst(newDatas)
            val isLastPage = dataList.size == 0
            refresh_layout.finishRefresh(0, true, isLastPage)
            if (!isLastPage) {
                slidableLayout.slideTo(SlideDirection.Prev)
            }
        } else {
            ComicCore.getNextChapter()
            val newDatas = mutableListOf<ComicPageInfo>()
            Log.i(TAG, "load next chapter.")
            ComicCore.mPages.forEach {
                val title = "${it.chapter}节/${it.page}页"
                val img = it.url
                val comicPageInfo = ComicPageInfo(img, title, it)
                newDatas.add(comicPageInfo)
            }
            dataList.addLast(newDatas)
            val isLastPage = dataList.size == 0
            if (!isLastPage) {
                slidableLayout.slideTo(SlideDirection.Next)
            }
            refresh_layout.finishLoadMore(0, true, isLastPage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SpUtils.putInt(KEY_PAGE, ComicCore.mCurrentPage, this@ComicSlideActivity)
        SpUtils.putInt(KEY_CHAPTER, ComicCore.mCurrentChapter, this@ComicSlideActivity)
    }

    companion object {
        const val TAG = "ComicSlideActivity"
        private const val KEY_PAGE = "page"
        private const val KEY_CHAPTER = "chapter"

    }

}
