package com.hutcwp.mlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.tcp.MsgBean
import com.hutcwp.tcp.MsgQueueManager

/**
 * 预览界面
 */
@Route(path = "/mlive/preview")
class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)


        findViewById<Button>(R.id.btn_start_live)?.setOnClickListener {
            MsgQueueManager.sendMsg(MsgBean("发送的消息"))
        }
    }
}