package me.hutcwp.cartoon.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.drakeet.multitype.ItemViewBinder
import me.hutcwp.cartoon.R

/**
 *Author:Administrator
 *Time:2019/6/3 22:55
 *
 **/

/**
 * @author Drakeet Xu
 */
class ComicItemViewBinder(var context: Context) : ItemViewBinder<ComicItem, ComicItemViewBinder.ComicHolder>() {
    override fun onBindViewHolder(holder: ComicHolder, item: ComicItem) {
        holder.text.text = item.text

        Glide.with(context)
                .load(item.imgPath)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(holder.img)
    }

    private var lastShownAnimationPosition: Int = 0

    class ComicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text)
        val img: ImageView = itemView.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ComicHolder {
        return ComicHolder(inflater.inflate(R.layout.crt_item_comic, parent, false))
    }

//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(holder: TextHolder, item: TextItem) {
//        holder.text.text = "hello: " + item.text
//        // should show animation, ref: https://github.com/drakeet/MultiType/issues/149
//        setAnimation(holder.itemView, holder.adapterPosition)
//    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastShownAnimationPosition) {
            viewToAnimate.startAnimation(AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left))
            lastShownAnimationPosition = position
        }
    }

    override fun onViewDetachedFromWindow(holder: ComicHolder) {
        holder.itemView.clearAnimation()
    }
}