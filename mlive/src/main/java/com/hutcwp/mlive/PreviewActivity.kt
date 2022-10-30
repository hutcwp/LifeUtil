package com.hutcwp.mlive

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.hutcwp.tcp.TcpManager
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
            TcpManager.sendMsg(TcpProtocol(sid = 1, cid = 0, content = "发送的消息"))
        }
    }
}