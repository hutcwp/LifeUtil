package com.hutcwp.mlive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.tcp.MsgQueueManager
import com.hutcwp.tcp.protocol.TcpProtocol

/**
 * 预览界面
 */
@Route(path = "/mlive/preview")
class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)


        findViewById<Button>(R.id.btn_start_live)?.setOnClickListener {
            MsgQueueManager.sendMsg(TcpProtocol(1,0,"发送的消息"))
        }
    }
}