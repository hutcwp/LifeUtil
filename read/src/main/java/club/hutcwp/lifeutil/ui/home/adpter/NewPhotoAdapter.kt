package club.hutcwp.lifeutil.ui.home.adpter

import android.content.Context
import android.widget.ImageView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.ui.home.other.PicDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

/**
 *  author : kevin
 *  date : 2020/12/27 1:30 AM
 *  description :
 */
class NewPhotoAdapter : BaseQuickAdapter<Photo, BaseViewHolder>(R.layout.read_item_photo) {

    override fun convert(holder: BaseViewHolder, item: Photo) {
        val img = holder.getView<ImageView>(R.id.img)
        val params = img.layoutParams
        params.width = 520
        params.height =  600
//        params.height = Random().nextInt(100) + 600
        img.layoutParams = params

        holder.setText(R.id.name, item.name)
                .setText(R.id.date, item.date)

        Glide.with(context)
                .load(item.img ?: "")
                .into(holder.getView(R.id.img))

//        holder.iv.setOnClickListener {
//            val intent = PicDetailActivity.newIntent(mContext, girlList!![position].img!!, "")
//            mContext.startActivity(intent)
//        }

    }
}