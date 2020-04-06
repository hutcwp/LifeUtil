package com.hutcwp.game.jigsaw

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.hutcwp.framwork.imagepicker.ImagePicker
import com.hutcwp.framwork.imagepicker.bean.ImageItem
import com.hutcwp.framwork.imagepicker.ui.ImageGridActivity
import com.hutcwp.framwork.imagepicker.view.CropImageView
import com.hutcwp.game.R
import com.hutcwp.game.jigsaw.view.JigsawLayout
import me.hutcwp.constant.Constants
import me.hutcwp.log.MLog
import me.hutcwp.util.ResolutionUtils


@Route(path = Constants.RoutePath.JIGSAW_MAIN_PAGE)
class MainActivity : AppCompatActivity() {

    private var jigsawLayout: JigsawLayout? = null
    private var fullImageView: ImageView? = null
    private var btnSelect: Button? = null
    private val REQUEST_CODE_SELECT = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity_main_jigsaw)
        initView()
        initData()
    }

    private fun initView() {
        fullImageView = findViewById(R.id.fullImageView)
        jigsawLayout = findViewById(R.id.jigsawLayout)
        btnSelect = findViewById(R.id.btnSelect)
        btnSelect?.setOnClickListener {
            selectPic()
        }
        jigsawLayout?.setPiece(3)
    }

    private fun initData() {
        resetJigsaw(IMG_URL)
    }

    private fun resetJigsaw(uri: String) {
        jigsawLayout?.setImgUrl(uri)
        Glide.with(this).load(uri).into(fullImageView!!)
    }

    private fun selectPic() {
        val mWidth = (ResolutionUtils.getScreenWidth(this) * 0.8).toInt() //真实棋盘的宽度
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = GlideImageLoader() //设置图片加载器
        imagePicker.isShowCamera = false //显示拍照按钮
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
            MLog.debug(TAG, "images=$images")
            if (images.isNotEmpty()) {
                resetJigsaw(images[0].path)
            }
        } else {
            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        const val TAG = "MainActivity"
        const val IMG_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ1kIpNpzm1gnCQ0CdH3RxEfMS1csBr70GYOhqnDnZAf7xB0UDm"
    }
}
