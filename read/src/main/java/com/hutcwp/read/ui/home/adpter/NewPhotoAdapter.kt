package com.hutcwp.read.ui.home.adpter

import android.widget.ImageView
import com.hutcwp.read.R
import com.hutcwp.read.entitys.Photo
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

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