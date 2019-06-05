package com.hutcwp.homepage

import android.os.Bundle

import com.alibaba.android.arouter.facade.annotation.Route

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import me.drakeet.multitype.MultiTypeAdapter

@Route(path = "/homepage/home")
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_home)
        //        ARouter.getInstance().build("/cartoon/comic").navigation();
        //        ARouter.getInstance().build("/cartoon/load").navigation();
        //        ARouter.getInstance().build("/comic/list").navigation();
        //        ARouter.getInstance().build("/cartoon/demo").navigation();
        //        ARouter.getInstance().build("/read/main").navigation()
        initListView()
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val items = mutableListOf<PageItem>()
        items.add(PageItem(Page("阅读首页", "/read/main", "")))
        items.add(PageItem(Page("漫画首页", "/cartoon/demo", "")))
        items.add(PageItem(Page("漫画爬虫", "/cartoon/load", "")))

        val adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter.register(PageItemViewBinder(this@HomeActivity))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }
}
