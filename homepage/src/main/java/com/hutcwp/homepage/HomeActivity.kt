package com.hutcwp.homepage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.homepage.rv.DSVOrientation
import com.hutcwp.homepage.rv.DiscreteScrollView
import com.hutcwp.homepage.rv.ScaleTransformer
import me.drakeet.multitype.MultiTypeAdapter
import me.hutcwp.auto.MainPageManager
import me.hutcwp.constant.ARoutePath
import me.hutcwp.log.MLog
import me.hutcwp.view.banner.vp.CustomViewPager
import me.hutcwp.view.banner.vp.LoopTransformer

@Route(path = ARoutePath.HomePage)
class HomeActivity : AppCompatActivity() {

    private val instance by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_home)
//        initViewPager()
        initListView()
//        initDiscreteRv()
    }

    private fun initDiscreteRv() {
        val discreteView = findViewById<DiscreteScrollView>(R.id.discreteView)
        discreteView.visibility= View.VISIBLE
        discreteView.setOrientation(DSVOrientation.HORIZONTAL)
        discreteView.addOnItemChangedListener { _, _ -> }
        val items = mutableListOf<PageItem>()
        for (page in MainPageManager.getCategoryNames()) {
            MLog.info(TAG, "class=$page , s.key = ${page.name} , s.value = ${page.path}")
            items.add(PageItem(Page(page.name, page.path, "")))
        }

        val adapter = RvAdapter(items)
        discreteView.adapter = adapter
        discreteView.setItemTransitionTimeMillis(1000)
        discreteView.setItemTransformer(
                ScaleTransformer.Builder()
                        .setMinScale(0.8f)
                        .build()
        )
    }


    private fun initViewPager() {
        MLog.info(TAG, "initViewPager")
        val viewPager = findViewById<CustomViewPager>(R.id.viewPager)
        viewPager.visibility= View.VISIBLE
        val items = mutableListOf<PageItem>()
        for (page in MainPageManager.getCategoryNames()) {
            MLog.info(TAG, "class=$page , s.key = ${page.name} , s.value = ${page.path}")
            items.add(PageItem(Page(page.name, page.path, "")))
        }

        val adapter = ViewAdapter(instance, items)
        viewPager.adapter = adapter
        viewPager.setPageTransformer(false, LoopTransformer())
        viewPager.offscreenPageLimit = 2
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.visibility= View.VISIBLE
        val items = mutableListOf<PageItem>()

        for (page in MainPageManager.getCategoryNames()) {
            MLog.info(TAG, "class=$page , s.key = ${page.name} , s.value = ${page.path}")
            items.add(PageItem(Page(page.name, page.path, "")))
        }

        val adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter.register(PageItemViewBinder(this@HomeActivity))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "HomeActivity"
    }
}
