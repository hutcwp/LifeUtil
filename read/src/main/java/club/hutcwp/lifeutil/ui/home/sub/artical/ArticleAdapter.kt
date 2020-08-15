package club.hutcwp.lifeutil.ui.home.sub.artical

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.entitys.News
import club.hutcwp.lifeutil.http.ApiFactory
import club.hutcwp.lifeutil.util.WebUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog

/**
 * Created by hutcwp on 2017/4/14.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class ArticleAdapter(private val context: Context, var readlist: MutableList<News>?) : RecyclerView.Adapter<ArticleAdapter.ReadHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    //取得data数据
    val data: List<News>?
        get() = readlist

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadHolder {
        val view = inflater.inflate(R.layout.read_item_read, parent, false)
        return ReadHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReadHolder, position: Int) {
        val item = readlist!![position]
        holder.rootView.setOnClickListener {
            MLog.info("ReadAdapter", "item.url = ${item.url}")
//            val url = "https://gank.io/api/v2/post/${item.url}"
//            val url = "https://gank.io/post/${item.url}"
            GlobalScope.launch {
                val articleDetailBean = withContext(Dispatchers.IO) {
                    ApiFactory.getGirlsController().getArticleDetail(item.url!!)
                }
                val html = Html.fromHtml(articleDetailBean.data.markdown)
                WebUtils.load(context, html.toString())
            }
        }
        //将标题设置为 序号.内容这种格式
        holder.tv_name.text = String.format("%s. %s", position + 1, item.name)
        holder.tv_info.text = "${item.updateTime} • ${item.from}"

        val option = RequestOptions()
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
