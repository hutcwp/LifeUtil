package me.hutcwp.cartoon.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yy.mobile.widget.SlideDirection
import com.yy.mobile.widget.SlideFragmentAdapter
import me.hutcwp.cartoon.bean.ComicPageInfo
import me.hutcwp.cartoon.bean.SimpleQueue

/**
 *
 * Created by hutcwp on 2019-06-02 18:24
 *
 *
 *
 **/
class FragmentAdapter(
        private val data: SimpleQueue<ComicPageInfo>, fm: FragmentManager
) : SlideFragmentAdapter(fm) {

    override fun onCreateFragment(context: Context): Fragment = ComicSlideFragment()

    override fun onBindFragment(fragment: Fragment, direction: SlideDirection) {
        super.onBindFragment(fragment, direction)
        (fragment as ComicSlideFragment).setCurrentData(getData(direction)!!)
    }

    override fun canSlideTo(direction: SlideDirection): Boolean {
        return getData(direction) != null
    }

    override fun finishSlide(direction: SlideDirection) {
        if (direction == SlideDirection.Next) {
            data.moveToNext()
        } else if (direction == SlideDirection.Prev) {
            data.moveToPrev()
        }
    }

    fun getData(direction: SlideDirection): ComicPageInfo? {
        return if (direction == SlideDirection.Next) {
            data.next()
        } else if (direction == SlideDirection.Prev) {
            data.prev()
        } else {
            data.current()
        }
    }
}