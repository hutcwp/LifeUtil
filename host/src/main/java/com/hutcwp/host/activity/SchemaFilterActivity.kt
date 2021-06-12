package com.hutcwp.host.activity

import android.app.Activity
import android.os.Bundle
import android.widget.RelativeLayout

import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.host.R

import com.just.agentweb.AgentWeb

import me.hutcwp.log.MLog

/**
 * Created by hutcwp on 2019-11-04 16:06
 *
 *
 */
@Route(path = "/host/schema")
class SchemaFilterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schema)
        val rootView = findViewById<RelativeLayout>(R.id.rootView)

        val url = intent.getStringExtra("url")
        MLog.info("SchemaFilterActivity", "url=$url")
        val mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(rootView as RelativeLayout, RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }
}
