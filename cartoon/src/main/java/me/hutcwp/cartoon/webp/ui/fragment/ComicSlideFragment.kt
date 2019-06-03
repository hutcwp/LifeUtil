package me.hutcwp.cartoon.webp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yy.mobile.widget.SlidableUI
import com.yy.mobile.widget.SlideDirection
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.webp.bean.ComicPageInfo
import me.hutcwp.cartoon.webp.util.startAnimation

/**
 *
 * Created by hutcwp on 2019-06-02 18:17
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/

class ComicSlideFragment : Fragment(), SlidableUI {

    private var currentInfo: ComicPageInfo? = null

    private lateinit var content_player: ImageView
    private lateinit var content_title: TextView


    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.page_main_content, null, false) as View
        content_player = root.findViewById(R.id.content_player)
        content_title = root.findViewById(R.id.content_title)
        return root
    }

    fun setCurrentData(data: ComicPageInfo) {
        currentInfo = data
    }

    override fun startVisible(direction: SlideDirection) {
        currentInfo?.let {
            content_title.text = it.title
            content_player.setImageDrawable(null) //should be snapshot
//            content_player.setGifResource(it.drawableRes)
            Glide.with(this)
                    .load(it.imagePath)
                    .transition(DrawableTransitionOptions().crossFade(200))
                    .into(content_player)
        }
    }

    override fun completeVisible(direction: SlideDirection) {
        content_player.setTag(R.id.crt_completeVisible, true)
        content_player.startAnimation()
    }

    override fun invisible(direction: SlideDirection) {
        //clean up resource
        content_player.setImageDrawable(null)
        content_player.setTag(R.id.crt_completeVisible, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("SlidableLayout", "onCreate")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i("SlidableLayout", "onHiddenChanged " +
                "${if (hidden) "->hidden" else "->show"} " +
                "$currentInfo")
    }

    override fun onResume() {
        super.onResume()
        Log.i("SlidableLayout", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.i("SlidableLayout", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("SlidableLayout", "onDestroy")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i("SlidableLayout", "setUserVisibleHint isVisible = $isVisibleToUser $currentInfo")
    }
}