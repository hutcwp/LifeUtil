package com.hutcwp.game.check.ui

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
import com.hutcwp.game.check.PicChecker
import com.hutcwp.game.check.bean.ResultBean
import com.hutcwp.game.check.util.GsonUtils
import com.hutcwp.game.jigsaw.GlideImageLoader
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
//            val str = "{\"reason\":\"success\",\"result\":{\"data\":[{\"score\":0.74471080303192,\"name\":\"水仙\",\"baike_info\":{\"baike_url\":\"http:\\/\\/baike.baidu.com\\/item\\/%E6%B0%B4%E4%BB%99\\/6410\",\"image_url\":\"http:\\/\\/imgsrc.baidu.com\\/baike\\/pic\\/item\\/b2de9c82d158ccbf9bee30df1bd8bc3eb0354181.jpg\",\"description\":\"水仙(Narcissus tazetta L.var.chinensis Roem.)：又名中国水仙，是多花水仙的一个变种。是石蒜科多年生草本植物。水仙的叶由鳞茎顶端绿白色筒状鞘中抽出花茎(俗称箭)再由叶片中抽出。一般每个鳞茎可抽花茎1～2枝，多者可达8～11枝，伞状花序。花瓣多为6片，花瓣末处呈鹅黄色。花蕊外面有一个如碗一般的保护罩。鳞茎卵状至广卵状球形，外被棕褐色皮膜。叶狭长带状，蒴果室背开裂。花期春季。水仙性喜温暖、湿润、排水良好。在中国已有一千多年栽培历史，为传统观赏花卉，是中国十大名花之十。水仙鳞茎多液汁，有毒，含有石蒜碱、多花水仙碱等多种生物碱；外科用作镇痛剂；鳞茎捣烂敷治痈肿。牛羊误食鳞茎，立即出现痉挛、瞳孔放大、暴泻等。(概述图片参考资料来源：)\"}},{\"score\":0.075568549335003,\"name\":\"半钟铁线莲\",\"baike_info\":{\"baike_url\":\"http:\\/\\/baike.baidu.com\\/item\\/%E5%8D%8A%E9%92%9F%E9%93%81%E7%BA%BF%E8%8E%B2\\/9265482\",\"image_url\":\"http:\\/\\/imgsrc.baidu.com\\/baike\\/pic\\/item\\/728da9773912b31b5466e5328e18367adab4e1a7.jpg\",\"description\":\"半钟铁线莲(学名：Clematis ochotensis (Pall.) Poir.)是毛茛科，铁线莲属多年生木质藤本植物。茎圆柱形，光滑无毛，当年生枝基部及叶腋有宿存的芽鳞，鳞片披针形，顶端有尖头，表面密被白色柔毛，小叶片窄卵状披针形至卵状椭圆形，顶端钝尖，上部边缘有粗牙齿，小叶柄短；花单生于当年生枝顶，钟状，萼片淡蓝色，长方椭圆形至狭倒卵形，化雄蕊成匙状条形，顶端圆形，雄蕊短于退化雄蕊，花丝线形而中部较宽，边缘被毛，花药内向着生；瘦果倒卵形，棕红色，5月至6月开花，7月至8月结果。分布于中国山西北部、河北北部、吉林东部及黑龙江省。日本、俄罗斯远东地区也有分布生于海拔600-1200米的山谷、林边及灌丛中。产于黑龙江、内蒙古、河北、山西等地；生于山坡或林下，花大而美丽，可栽培供观赏。亦可作廊架、篱笆攀援。(概述图参考来源：中国自然标本馆)\"}},{\"score\":0.015158865600824,\"name\":\"黄水仙\",\"baike_info\":{\"baike_url\":\"\",\"image_url\":\"\",\"description\":\"\"}},{\"score\":0.0097142700105906,\"name\":\"玉玲珑水仙\",\"baike_info\":{\"baike_url\":\"\",\"image_url\":\"\",\"description\":\"\"}},{\"score\":0.0082676103338599,\"name\":\"天蒜\",\"baike_info\":{\"baike_url\":\"http:\\/\\/baike.baidu.com\\/item\\/%E5%A4%A9%E9%9F%AD\\/4937169\",\"image_url\":\"https:\\/\\/bkimg.cdn.bcebos.com\\/pic\\/5ab5c9ea15ce36d3f87e940d30f33a87e950b190\",\"description\":\"天韭，中药名。为百合科葱属植物玉簪叶韭Allium funckiaefolium Hand.Mzt.的全草。植物玉簪叶韭，分布于陕西、湖北、四川等地。具有散瘀止痛，止血，解毒之功效。主治外疮肿痛，衄血，漆疮。\"}}]},\"error_code\":0}"
//            handlerJson(str)
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
            MLog.debug(TAG, "images=$images")
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
                    //                    PicChecker.checkPlant(it)
                    PicChecker.checkPlant(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    MLog.info(TAG, "it=$it")
                    handlerJson(it)

                    mIvPlant?.let { ivPlant ->
                        Glide.with(this).load(imgPath)
                                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                                .into(ivPlant)
                    }
                }
    }

    private fun handlerJson(response: String?) {
        try {
            val result = GsonUtils.fromJson(response, ResultBean::class.java)
            var content = ""
            result.result.forEach { identifyResult ->
                content += identifyResult.getName() + " : 匹配度（" + identifyResult.getScore() + "）" + '\n'
            }
            mTvResult?.text = content
        } catch (e: Exception) {
            MLog.error(TAG, "e=", e)
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