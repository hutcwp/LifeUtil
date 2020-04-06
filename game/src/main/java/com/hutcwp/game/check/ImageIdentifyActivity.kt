package com.hutcwp.game.check

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hutcwp.framwork.imagepicker.ImagePicker
import com.hutcwp.framwork.imagepicker.bean.ImageItem
import com.hutcwp.framwork.imagepicker.ui.ImageGridActivity
import com.hutcwp.framwork.imagepicker.view.CropImageView
import com.hutcwp.game.R
import com.hutcwp.game.jigsaw.GlideImageLoader
import com.hutcwp.game.jigsaw.MainActivity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.hutcwp.constant.Constants
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils
import me.hutcwp.util.RxUtils

/**
 *
 * Created by hutcwp on 2020/4/6 19:32
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
@Route(path = Constants.RoutePath.IDENTIFY_MAIN_PAGE)
class ImageIdentifyActivity : AppCompatActivity() {

    private var mIdentifyDisposable: Disposable? = null
    private var mBtnSelect: Button? = null
    private var mIvPlant: ImageView? = null
    private var mTvResult: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout_identify)
        initView()
        initListener()
    }

    private fun initListener() {
        mBtnSelect?.setOnClickListener {
            selectPic()
        }
    }

    private fun initView() {
        mBtnSelect = findViewById(R.id.btnSelect)
        mIvPlant = findViewById(R.id.ivPlant)
        mTvResult = findViewById(R.id.tvResult)
    }

    private fun selectPic() {
        val mWidth = (ResolutionUtils.getScreenWidth(this) * 0.8).toInt() //真实棋盘的宽度
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader() //设置图片加载器
        imagePicker.isShowCamera = true //显示拍照按钮
        imagePicker.isCrop = true //允许裁剪（单选才有效）
        imagePicker.isMultiMode = false
        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.selectLimit = 1 //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE //裁剪框的形状
        imagePicker.focusWidth = mWidth //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.focusHeight = mWidth //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.outPutX = mWidth //保存文件的宽度。单位像素
        imagePicker.outPutY = mWidth //保存文件的高度。单位像素

        val intent = Intent(this, ImageGridActivity::class.java)
        startActivityForResult(intent, ImagePicker.RESULT_CODE_ITEMS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == ImagePicker.RESULT_CODE_ITEMS) {
            val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
            MLog.debug(MainActivity.TAG, "images=$images")
            if (images.isNotEmpty()) {
                onSelectPic(images[0].path)
            }
        } else {
            Toast.makeText(this, "没有图片被选择", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSelectPic(imgPath: String) {
        RxUtils.dispose(mIdentifyDisposable)
        mIdentifyDisposable = Observable.just(imgPath)
                .map {
                    PicChecker.checkPlant(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    MLog.info(TAG, "it=$it")
                    mTvResult?.text = it
                    mIvPlant?.let { ivPlant ->
                        Glide.with(this).load(imgPath)
                                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                                .into(ivPlant)
                    }
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxUtils.dispose(mIdentifyDisposable)
    }


    companion object {
        const val TAG = "ImageIdentifyActivity"
    }
}