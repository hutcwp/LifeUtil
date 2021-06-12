package com.hutcwp.read.ui.other

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hutcwp.read.R
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.read_activity_girl_picture.*
import me.hutcwp.constants.RoutePath


@Route(path = RoutePath.PIC_DETAIL_ACTIVITY, name = "图片详情页面")
class PicDetailActivity : AppCompatActivity() {

    @JvmField
    @Autowired(name = EXTRA_IMAGE_URL)
    var imageUrl: String = ""

    @JvmField
    @Autowired(name = EXTRA_IMAGE_TITLE)
    var imageTitle: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_activity_girl_picture)
        ARouter.getInstance().inject(this)
        initFragment()
        initData()
    }

    private fun initData() {
        tv_title?.text = imageTitle
    }

    private fun initFragment() {
        val fragment = PicDetailFragment.newInstance(imageUrl)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content2, fragment)
        transaction.commit()
    }


    companion object {
        const val EXTRA_IMAGE_URL = "image_url"
        const val EXTRA_IMAGE_TITLE = "image_title"
    }
}
