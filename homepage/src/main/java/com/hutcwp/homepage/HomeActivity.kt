package com.hutcwp.homepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.drakeet.multitype.MultiTypeAdapter

@Route(path = "/homepage/home")
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_home)
        initListView()
//        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val items = mutableListOf<PageItem>()
        items.add(PageItem(Page("阅读首页", "/read/main", "")))
        items.add(PageItem(Page("漫画首页", "/cartoon/demo", "")))
        items.add(PageItem(Page("漫画爬虫", "/cartoon/load", "")))
        items.add(PageItem(Page("今日天气", "/weather/select", "")))

        val adapter = MultiTypeAdapter()
        recyclerView.adapter = adapter
        adapter.register(PageItemViewBinder(this@HomeActivity))
        adapter.items = items
        adapter.notifyDataSetChanged()
    }
}
