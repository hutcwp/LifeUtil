package me.hutcwp.cartoon.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.crt_activity_comic.*
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.core.ComicCore
import me.hutcwp.cartoon.core.Params
import me.hutcwp.cartoon.db.ComicDBRepo
import me.hutcwp.cartoon.gesture.SimpleOnGestureListener

@Route(path = "/cartoon/comic")
class ComicActivity : AppCompatActivity(), SimpleOnGestureListener.SwipeViewDelegate {


    private var mChapter = 2
    private var mPage = 2

    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mSimpleOnGestureListener: SimpleOnGestureListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crt_activity_comic)
        initComicCore()
        initData()
        showCurrentPage()
    }

    private fun initComicCore() {
        val params = Params()
        params.name = "妖神记"
        params.chapter = 2
        params.page = 1
        ComicCore.init(params)
    }

    private fun initData() {
        mSimpleOnGestureListener = SimpleOnGestureListener()
        mSimpleOnGestureListener.setmSwipeViewDelegate(this)
        mGestureDetector = GestureDetector(mSimpleOnGestureListener)
    }

    private fun loadComic(imgPath: String?) {
        Log.i(TAG, "loadComic imgPath")
        Glide.with(this)
                .load(imgPath)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(ivComic)
    }

    private fun loadComic(drawable: Drawable?) {
        Log.i(TAG, "loadComic drawable")
        ivComic.setImageDrawable(drawable)
    }

    private fun showCurrentPage() {
        ComicCore.getCurPage().subscribe(Consumer {
            Log.i(TAG, "showCurrentPage = $it")
            loadComic(it)
        })
    }


    override fun flingToLeft(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        ComicCore.getNextPage()
                .subscribe(Consumer {
                    Log.i(TAG, "showCurrentPage = $it")
                    loadComic(it)
                })
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    override fun flingToRight(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        ComicCore.getNextChapter()
        ComicCore.getCurPage().subscribe(Consumer {
            Log.i(TAG, "showCurrentPage = $it")
            loadComic(it)
        }, Consumer {
            Log.e(TAG, "error = ", it)
        })

//        ComicCore.getPrePage()
//                .subscribe(Consumer {
//                    Log.i(TAG, "showCurrentPage = $it")
//                    loadComic(it)
//                })
        return true
    }

    override fun flingToTop(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun flingToBottom(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    companion object {
        private const val TAG = "ComicActivity"
    }
}
