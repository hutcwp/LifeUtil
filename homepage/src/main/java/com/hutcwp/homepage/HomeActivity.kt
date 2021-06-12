package com.hutcwp.homepage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.exception.NoRouteFoundException
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.alibaba.android.arouter.launcher.ARouter
import com.drakeet.multitype.MultiTypeAdapter
import com.hutcwp.homepage.adpater.CardViewAdapter
import com.hutcwp.homepage.adpater.RvCardViewAdapter
import com.hutcwp.homepage.rv.DSVOrientation
import com.hutcwp.homepage.rv.DiscreteScrollView
import com.hutcwp.homepage.rv.ScaleTransformer
import me.hutcwp.auto.MainPageManager
import me.hutcwp.log.MLog
import me.hutcwp.view.banner.vp.CustomViewPager
import me.hutcwp.view.banner.vp.LoopTransformer

@Route(path = "/homepage/home")
class HomeActivity : AppCompatActivity() {

    private val bgColorList = listOf<Int>(
            Color.parseColor("#FFB6C1"),
            Color.parseColor("#87CEFA"),
            Color.parseColor("#7FFFD4"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_home)
//        initViewPager()
//        initListView()
        initDiscreteRv()
    }

    private fun initDiscreteRv() {
        val discreteView = findViewById<DiscreteScrollView>(R.id.discreteView)
        discreteView.setOrientation(DSVOrientation.HORIZONTAL)

        val adapter = RvCardViewAdapter(this, getEnableItemList())
        discreteView.adapter = adapter
        discreteView.setItemTransitionTimeMillis(1000)
        discreteView.setItemTransformer(ScaleTransformer.Builder().setMinScale(0.8f).build())
    }

    private fun initViewPager() {
        MLog.info(TAG, "initViewPager")
        val viewPager = findViewById<CustomViewPager>(R.id.viewPager)
        val adapter = CardViewAdapter(this, getEnableItemList())
        viewPager.adapter = adapter
        viewPager.setPageTransformer(false, LoopTransformer())
        viewPager.offscreenPageLimit = 2
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter.register(PageItemViewBinder(this@HomeActivity))
        adapter.items = getEnableItemList()
        adapter.notifyDataSetChanged()
    }

    /**
     * 判断路由是否存在
     * @param context 上下文
     * @param path 路由
     */
    private fun isExist(context: Context, path: String): Boolean {
        val pretreatmentService = ARouter.getInstance().navigation(PretreatmentService::class.java)
        if (null != pretreatmentService && !pretreatmentService.onPretreatment(context, ARouter.getInstance().build(path))) {
            return false
        }
        try {
            LogisticsCenter.completion(ARouter.getInstance().build(path))
        } catch (e: NoRouteFoundException) {
            return false
        }

        return true
    }

    private fun getEnableItemList(): MutableList<PageItem> {
        val items = mutableListOf<PageItem>()
        for (page in MainPageManager.getCategoryNames()) {
            MLog.info(TAG, "class=$page , s.key = ${page.name} , s.value = ${page.path}")
            if (isExist(this, page.path)) {
                val p = Page(page.name, page.path, "")
                val bgColor = getCoverColor()
                items.add(PageItem(p, bgColor))
            }
        }

        return items
    }

    private fun getCoverColor(): Int {
        return bgColorList.shuffled().take(1)[0]
    }


    companion object {
        private const val TAG = "HomeActivity"
    }
}
