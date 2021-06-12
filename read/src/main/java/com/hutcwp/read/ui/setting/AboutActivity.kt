package com.hutcwp.read.ui.setting


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.hutcwp.common.mvp.BaseActivity
import com.hutcwp.read.R
import com.hutcwp.read.util.WebUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.read_activity_about.*
import me.hutcwp.constants.AppConfig
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * 关于界面
 */
class AboutActivity : BaseActivity() {

    private val imageUrls = arrayOf(
            "http://img1.imgtn.bdimg.com/it/u=3417252818,3856523523&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1386841340,1499054475&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1693590222,2802285436&fm=26&gp=0.jpg",
            "http://b-ssl.duitang.com/uploads/item/201609/06/20160906182936_NCaF8.jpeg",
            "http://b-ssl.duitang.com/uploads/item/201609/06/20160906183705_NUMVn.thumb.700_0.jpeg"
    )

    private var loadImgDisposable: Disposable? = null

    private var curURL = ""


    override fun bindLayout() = R.layout.read_activity_about

    override fun initData(savedInstanceState: Bundle?) {
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        tv_app_version.text = "v ${AppConfig.appVersionName}"
        imageSwitcher?.setFactory {
            val imageView = ImageView(this@AboutActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView
        }
        imageSwitcher?.inAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        imageSwitcher?.outAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
    }

    override fun requestData() {
        super.requestData()
        imageSwitcher?.post { loadImage() }
        loadImgDisposable = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loadImage()
                }, {
                    MLog.error(TAG, "interval load Img error, see error below:", it)
                })
    }


    private fun loadImage() {
        val randomImgUrl = getRandomImgURL()

        Glide.with(this)
                .asDrawable()
                .load(randomImgUrl)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        imageSwitcher?.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
    }

    private fun getRandomImgURL(): String {
        while (true) {
            val imgURL = imageUrls[Random().nextInt(imageUrls.size)]
            if (imgURL != curURL) {
                return imgURL
            }
        }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_web_home -> WebUtils.openInternal(this, "http://blog.csdn.net/qq_25184739")
            R.id.btn_feedback -> {
                SingleToastUtil.showToast("努力加载中～")
            }
            R.id.btn_check_update -> {
                SingleToastUtil.showToast("已经是最新版本！")
            }
            R.id.btn_share_app -> {
                SingleToastUtil.showToast("快向好友推荐吧～")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loadImgDisposable?.dispose()
    }


    companion object {
        const val TAG = "AboutActivity"
    }

}
