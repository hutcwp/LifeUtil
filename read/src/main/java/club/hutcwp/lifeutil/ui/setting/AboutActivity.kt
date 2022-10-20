package club.hutcwp.lifeutil.ui.setting


import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
//import club.hutcwp.lifeutil.BuildConfig
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.ui.base.BaseActivity
import club.hutcwp.lifeutil.util.WebUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by liyu on 2016/11/28.
 */

class AboutActivity : BaseActivity() {

    private var tvVersion: TextView? = null
    private var imageSwitcher: ImageSwitcher? = null
    private val imageUrls = arrayOf(
            "http://img1.imgtn.bdimg.com/it/u=3417252818,3856523523&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1386841340,1499054475&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1693590222,2802285436&fm=26&gp=0.jpg",
            "http://b-ssl.duitang.com/uploads/item/201609/06/20160906182936_NCaF8.jpeg",
            "http://b-ssl.duitang.com/uploads/item/201609/06/20160906183705_NUMVn.thumb.700_0.jpeg"
    )

    private var disposable: Disposable? = null

    override val layoutId: Int
        get() = R.layout.read_activity_about

    override fun initViews(savedInstanceState: Bundle?) {
        setDisplayHomeAsUpEnabled(true)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        tvVersion = findViewById<View>(R.id.tv_app_version) as TextView
//        tvVersion!!.text = "v" + BuildConfig.VERSION_NAME
        imageSwitcher = findViewById<View>(R.id.imageSwitcher) as ImageSwitcher
        imageSwitcher!!.setFactory {
            val imageView = ImageView(this@AboutActivity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView
        }
        imageSwitcher!!.inAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zoom_in)
        imageSwitcher!!.outAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zoom_out)
    }

    private fun loadImage() {
        Glide.with(this).asDrawable()
                .load(imageUrls[Random().nextInt(imageUrls.size)])
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        imageSwitcher?.setImageDrawable(resource)
                    }
                })
    }

    override fun loadData() {
        imageSwitcher!!.post { loadImage() }
        disposable = Observable.interval(5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadImage() }
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_web_home -> WebUtils.openInternal(this, "http://blog.csdn.net/qq_25184739")
            R.id.btn_feedback -> feedBack()
            R.id.btn_check_update -> {
            }
            R.id.btn_share_app -> {
            }
        }//                UpdateUtil.check(AboutActivity.this, false);
        //                ShareUtils.shareText(this, "来不及了，赶紧上车！https://github.com/li-yu/FakeWeather");
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable!!.isDisposed)
            disposable!!.dispose()
    }

    private fun feedBack() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "hutcwp@foxmail.com", null))
        intent.putExtra(Intent.EXTRA_EMAIL, "hutcwp@foxmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "反馈")
        startActivity(Intent.createChooser(intent, "反馈"))
    }

}
