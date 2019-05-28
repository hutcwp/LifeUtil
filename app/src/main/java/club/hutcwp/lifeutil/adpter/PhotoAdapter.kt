package club.hutcwp.lifeutil.adpter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.ui.home.other.PicDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

    /**
     * 添加数据
     *
     * @param datas 新增的数据
     */
    fun addDatas(datas: List<Photo>?) {
        if (datas == null) {
            return
        }
        girlList!!.addAll(datas)
        notifyItemChanged(itemCount)
    }

    //添加data数据
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

        val view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent,
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
            val intent = PicDetailActivity.newIntent(mContext, girlList!![position].img, "")
            mContext.startActivity(intent)
        }


        //使用params,width 和params.heght 去加载图片
        Glide.with(mContext)
                .load(girlList!![position]
                        .img)
                .override(params.width, params.height) //设置加载尺寸
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
            iv = view.findViewById<View>(R.id.img) as ImageView
            name = view.findViewById<View>(R.id.name) as TextView
            date = view.findViewById<View>(R.id.date) as TextView
        }
    }


}
