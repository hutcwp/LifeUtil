package com.hutcwp.common.web

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hutcwp.common.R
import kotlinx.android.synthetic.main.activity_web_input.*
import me.hutcwp.constants.RoutePath
import me.hutcwp.util.SingleToastUtil
import java.util.regex.Pattern

@Route(path = RoutePath.WEB_MAIN)
class WebInputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_input)

        setListener()
    }

    private fun setListener() {
        btn_confirm?.setOnClickListener {
            var webStr = et_web.text.toString()
            if (webStr.contains("https://") || webStr.contains("http://")) {

            } else {
                webStr = "https://$webStr"
            }


            if (!isURLValid(webStr)) {
                SingleToastUtil.showToast("请检查输入的网址是否正确！")
                return@setOnClickListener
            }

            ARouter.getInstance()
                    .build(RoutePath.SIMPLE_WEB)
                    .withString("url", webStr)
                    .navigation()
        }
    }

    private fun isURLValid(text: String): Boolean {
        val a = arrayOf("top", "com.cn", "com", "net", "cn", "cc", "gov", "cn", "hk");
        val sb = StringBuilder();
        sb.append("(");
        for (f in a) {
            sb.append(f);
            sb.append("|");
        }
        sb.deleteCharAt(sb.length - 1);
        sb.append(")");

        val p = Pattern.compile("((https?|s?ftp|irc[6s]?|git|afp|telnet|smb)://)?((\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|((www\\.|[a-zA-Z\\.\\-]+\\.)?[a-zA-Z0-9\\-]+\\.$sb(:[0-9]{1,5})?))((/[a-zA-Z0-9\\./,;\\?'\\+&%\\$#=~_\\-]*)|([^\\u4e00-\\u9fa5\\s0-9a-zA-Z\\./,;\\?'\\+&%\\$#=~_\\-]*))", Pattern.CASE_INSENSITIVE);
        val matcher = p.matcher(text);

        return matcher.matches()
    }

}