package me.hutcwp.cartoon.webp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.drakeet.multitype.MultiTypeAdapter
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.list.ComicItem
import me.hutcwp.cartoon.list.ComicItemViewBinder
import me.hutcwp.cartoon.webp.core.ComicCore
import me.hutcwp.cartoon.webp.core.Params
import me.hutcwp.cartoon.webp.util.SpUtils

@Route(path = "/comic/list")
class ListComicActivity : AppCompatActivity() {

    private lateinit var adapter: MultiTypeAdapter
    private lateinit var items: MutableList<Any>

    private val url = "https://upload.jianshu.io/users/upload_avatars/1846413/f6cede61-2e06-4300-b397-f8c1abff55f2.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersive()
        setContentView(R.layout.crt_activity_list_comic)
        initComicCore()
        initData()
        initListView()
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter.register(ComicItemViewBinder(this@ListComicActivity))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        items = ArrayList()
        ComicCore.mPages.forEach {
            val title = "${it.chapter}节/${it.page}页"
            val img = it.url
            val comicItem = ComicItem(title, img)
            items.add(comicItem)
        }
    }

    private fun initComicCore() {
        val params = Params()
        params.name = "妖神记"
        params.chapter = SpUtils.getInt(KEY_CHAPTER, 1, this@ListComicActivity)
        params.page = SpUtils.getInt(KEY_PAGE, 1, this@ListComicActivity)
        ComicCore.init(params)
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
        SpUtils.putInt(KEY_PAGE, ComicCore.mCurrentPage, this)
        SpUtils.putInt(KEY_CHAPTER, ComicCore.mCurrentChapter, this)
    }

    companion object {
        const val TAG = "ListComicActivity"
        private const val KEY_PAGE = "page"
        private const val KEY_CHAPTER = "chapter"
    }
}
