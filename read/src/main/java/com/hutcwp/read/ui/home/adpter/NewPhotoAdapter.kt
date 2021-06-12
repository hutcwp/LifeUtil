package com.hutcwp.read.ui.home.adpter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hutcwp.read.R
import com.hutcwp.read.entitys.Photo

/**
 *  author : kevin
 *  date : 2020/12/27 1:30 AM
 *  description :
 */
class NewPhotoAdapter : BaseQuickAdapter<Photo, BaseViewHolder>(R.layout.read_item_photo) {

    override fun convert(holder: BaseViewHolder, item: Photo) {

        holder.setText(R.id.tv_name, item.name)
                .setText(R.id.tv_date, item.date)

        Glide.with(context)
                .load(item.img)
                .into(holder.getView(R.id.iv_img))
    }
}