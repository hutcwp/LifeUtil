package com.hutcwp.read.ui.home.adpter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hutcwp.read.R
import com.hutcwp.read.entitys.Photo
import com.hutcwp.read.ui.other.PicDetailActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.hutcwp.other.RoutePath
import java.util.*

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class PhotoAdapter(private val mContext: Context, girlList: MutableList<Photo>?) : RecyclerView.Adapter<PhotoAdapter.GirlViewHolder>() {

    private var girlList: MutableList<Photo>? = ArrayList()

    init {
        this.girlList = girlList
    }

    fun addDatas(datas: List<Photo>?) {
        if (datas == null) {
            return
        }
        girlList!!.addAll(datas)
        notifyItemChanged(itemCount)
    }

    fun addData(position: Int, data: List<Photo>) {
        this.girlList!!.addAll(position, data)
        this.notifyItemRangeInserted(position, data.size)
    }

    /**
     * 设置新内容
     *
     * @param data 新内容
     */
    fun setNewData(data: MutableList<Photo>) {
        this.girlList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.read_item_photo, parent,
                false)//这个布局就是一个imageview用来显示图片

        return GirlViewHolder(view)
    }

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
        val params = holder.iv.layoutParams
        params.width = 520
        params.height = Random().nextInt(100) + 600
        holder.iv.layoutParams = params
        holder.name.text = girlList!![position].name
        holder.date.text = girlList!![position].date
        holder.iv.setOnClickListener {
            val imageUrl = girlList!![position].img!!
            val imageTitle = girlList!![position].name!!
            ARouter.getInstance()
                    .build(RoutePath.PIC_DETAIL_ACTIVITY)
                    .withString(PicDetailActivity.EXTRA_IMAGE_URL, imageUrl)
                    .withString(PicDetailActivity.EXTRA_IMAGE_TITLE, imageTitle)
                    .navigation()
        }
        //使用params,width 和params.heght 去加载图片
        Glide.with(mContext)
                .load(girlList!![position]
                        .img)
                .apply(RequestOptions().centerCrop())
                .into(holder.iv)//加载网络图片
    }

    override fun getItemCount(): Int {
        return if (girlList == null) 0 else girlList!!.size
    }

    //自定义ViewHolder，用于加载图片
    inner class GirlViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv: ImageView
        var name: TextView
        var date: TextView

        init {
            iv = view.findViewById<View>(R.id.iv_img) as ImageView
            name = view.findViewById<View>(R.id.tv_name) as TextView
            date = view.findViewById<View>(R.id.tv_date) as TextView
        }
    }
}
