package club.hutcwp.lifeutil.adpter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.util.WebUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by hutcwp on 2017/4/14.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class ReadAdapter(private val context: Context, var readlist: MutableList<News>?) : RecyclerView.Adapter<ReadAdapter.ReadHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    //取得data数据
    val data: List<News>?
        get() = readlist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadHolder {
        val view = inflater.inflate(R.layout.item_read, parent, false)
        return ReadHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReadHolder, position: Int) {
        val item = readlist!![position]
        holder.rootView.setOnClickListener { WebUtils.openInternal(context, item.url!!) }
        //将标题设置为 序号.内容这种格式
        holder.tv_name.text = String.format("%s. %s", position + 1, item.name)
        holder.tv_info.text = "${item.updateTime} • ${item.from}"

        val option = RequestOptions().circleCrop()
        Glide.with(context).load(item.icon).apply(option).into(holder.iv)
    }

    /**
     * 设置新内容
     * @param data 新内容
     */
    fun setNewData(data: MutableList<News>) {
        this.readlist = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (readlist == null) 0 else readlist!!.size
    }

    //添加data数据
    fun addData(position: Int, data: List<News>) {
        this.readlist!!.addAll(position, data)
        this.notifyItemRangeInserted(position, data.size)
    }


    inner class ReadHolder(var rootView: View) : RecyclerView.ViewHolder(rootView) {

        var tv_name: TextView
        var tv_info: TextView
        var iv: ImageView

        init {
            tv_name = rootView.findViewById<View>(R.id.tv_read_name) as TextView
            tv_info = rootView.findViewById<View>(R.id.tv_read_info) as TextView
            iv = rootView.findViewById<View>(R.id.iv_read_icon) as ImageView
        }
    }


}
