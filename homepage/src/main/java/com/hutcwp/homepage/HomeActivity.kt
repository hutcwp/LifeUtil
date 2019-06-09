package com.hutcwp.homepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import me.drakeet.multitype.MultiTypeAdapter
import me.hutcwp.auto.MainPageManager
import me.hutcwp.log.MLog

@Route(path = "/homepage/home")
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hp_activity_home)
        initListView()
    }

    private fun initListView() {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
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
