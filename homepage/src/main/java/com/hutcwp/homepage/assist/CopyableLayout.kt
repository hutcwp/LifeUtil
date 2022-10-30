package com.hutcwp.homepage.assist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hutcwp.homepage.R
import kotlinx.android.synthetic.main.hp_layout_copy.view.*
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil


/**
 *
 * Created by hutcwp on 2019-12-10 17:50
 * email: caiwenpeng@yy.com
 * YY: 909076244
 * 可以复制的Layout
 **/
class CopyableLayout : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        inflaterLayout()
    }

    private fun inflaterLayout() {
        View.inflate(context, R.layout.hp_layout_copy, this)
        setListener()
    }

    private fun setListener() {
        setOnClickListener {
            SingleToastUtil.showToast(ivContent?.text.toString().trim())
        }
        setOnLongClickListener {
            SingleToastUtil.showToast("已经复制到剪切板")
            MLog.info(TAG, "text=${ivContent.text}")
            copyToClip(ivContent.text)
            true
        }
    }

    fun setText(text: CharSequence) {
        ivContent?.text = text
    }

    fun getText(): CharSequence {
        return ivContent?.text ?: ""
    }

    private fun copyToClip(str: CharSequence?) {
        val cm = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val mClipData = ClipData.newPlainText("Label", str)
//        cm?.primaryClip = mClipData
    }

    companion object {
        const val TAG = "CopyableLayout"
    }

}