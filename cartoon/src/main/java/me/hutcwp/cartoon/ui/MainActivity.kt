package me.hutcwp.cartoon.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.util.SpUtils
import me.hutcwp.cartoon.gesture.SimpleOnGestureListener
import java.util.*

class MainActivity : AppCompatActivity(), SimpleOnGestureListener.SwipeViewDelegate {

    private lateinit var viewDelegte:ViewDelegate
    private var mCurrentPage = 1
    private var mCurrentChapter = 2
    private val handler = Handler()
    private lateinit var mGestureDetector: GestureDetector
    private lateinit var mSimpleOnGestureListener: SimpleOnGestureListener

    private var isEnable = true


    private val mChapterList = ArrayList<Drawable>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crt_activity_home)
        viewDelegte = ViewDelegate(this )
        initData()
        toPage(mCurrentChapter, mCurrentPage)
        mSimpleOnGestureListener = SimpleOnGestureListener()
        mSimpleOnGestureListener.setmSwipeViewDelegate(this)
        mGestureDetector = GestureDetector(mSimpleOnGestureListener)
    }

    private fun initData() {
        mCurrentPage = SpUtils.getInt(KEY_PAGE, 1, this@MainActivity)
        mCurrentChapter = SpUtils.getInt(KEY_CHAPTER, 1, this@MainActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        SpUtils.putInt(KEY_PAGE, mCurrentPage, this@MainActivity)
        SpUtils.putInt(KEY_CHAPTER, mCurrentChapter, this@MainActivity)
    }

    private fun toPage(chapter: Int, page: Int) {
        mCurrentChapter = chapter
        mCurrentPage = page
        mChapterList.clear()
        initCurrentChapter(chapter, 1)
    }

    private fun getImgUrl(chapter: Int, page: Int): String {
        return "$BASE_URL$CODE/$NAME/$chapter$PAGES/$page.jpg-mht.middle.webp"
    }

    fun initCurrentChapter(chapter: Int, page: Int) {
        //        String img = "https://mhpic.manhualang.com/comic/"+"Y/妖神记/" + chapter + "话/" + page + ".jpg-mht.middle.webp";
        val img = getImgUrl(chapter, page)
        val options = RequestOptions()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        Glide.with(this)
                .load(img)
                .apply(options).transition(DrawableTransitionOptions().crossFade(200))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.i(TAG, "onLoadFailed: e = " + e!!)
                        Log.i(TAG, "当前章节加载结束,page size =" + mChapterList.size)
                        handler.post { loadCurrentPageFromList(mCurrentChapter, mCurrentPage) }
                        return true
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Log.i(TAG, "onResourceReady: chapter = " + mCurrentChapter + " page = " + mChapterList.size)
                        mChapterList.add(resource)
                        if (mCurrentPage == page) {
                            handler.post { loadCurrentPageFromList(mCurrentChapter, mCurrentPage) }
                        }

                        handler.post { initCurrentChapter(chapter, page + 1) }
                        return false
                    }
                })
                .into(viewDelegte.mFackImageView!!)
    }


    fun btnGoClick(){
        try {
            val page = Integer.valueOf(viewDelegte.mEvPage!!.text.toString().trim { it <= ' ' })
            val chapter = Integer.valueOf(viewDelegte.mEvChapter!!.text.toString().trim { it <= ' ' })
            if (isEnable && page > 0 && chapter > 0) {
                isEnable = false
                mCurrentChapter = chapter
                mCurrentPage = page
                mChapterList.clear()
                initCurrentChapter(chapter, 1)
            }
        } catch (e: Exception) {
            Log.e(TAG, "ClickListener error ", e)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

    /**
     * 从内存队列中获取
     *
     * @param chapter
     * @param page
     */
    @SuppressLint("CheckResult")
    private fun loadCurrentPageFromList(chapter: Int, page: Int) {
        var page = page
        Log.i(TAG, "loadCurrentPageFromList:list size = " + mChapterList.size + " chapter = " + chapter + " page = " + page)
        isEnable = true
        if (page == 0) {
            Log.i(TAG, "page == 0,向前翻一话")
            mCurrentPage = mChapterList.size
            page = mCurrentPage
        }

        if (mChapterList.size > page - 1 && page - 1 >= 0) {
            Log.i(TAG, "set page=" + (page - 1) + " drawable = " + mChapterList[page - 1].hashCode())
            viewDelegte.mImageView!!.setImageDrawable(mChapterList[page - 1])
            val format = resources.getString(R.string.crt_string_current_page)
            viewDelegte.mTvPageTip!!.text = String.format(format, chapter, page)
        } else {
            Toast.makeText(this@MainActivity, "加载错误，请检查输入是否正确", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "loadCurrentPageFromList error")
        }
    }


    private fun turnPrePage() {
        Log.i(TAG, "turnPrePage")
        if (isEnable) {
            isEnable = false
            mCurrentPage--
            if (mCurrentChapter >= 1) {
                if (mCurrentPage >= 1) {
                    loadCurrentPageFromList(mCurrentChapter, mCurrentPage)
                } else {
                    //去前一话
                    mCurrentChapter--
                    if (mCurrentChapter >= 1) {
                        mChapterList.clear()
                        initCurrentChapter(mCurrentChapter, 1)
                    } else {
                        mCurrentPage++
                        Toast.makeText(this@MainActivity, "当前为第一页,没有更前了。", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                mCurrentPage++
                Toast.makeText(this@MainActivity, "当前为第一页,没有更前了。", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun turnNextPage() {
        Log.i(TAG, "turnNextPage")
        if (isEnable) {
            isEnable = false
            mCurrentPage++
            if (mCurrentPage > 0 && mCurrentPage - 1 < mChapterList.size) {
                loadCurrentPageFromList(mCurrentChapter, mCurrentPage)
            } else if (mCurrentPage > mChapterList.size) {
                Log.i(TAG, "turnNextPage 翻后一话")
                //翻后一话
                mCurrentChapter++
                mCurrentPage = 1
                mChapterList.clear()
                initCurrentChapter(mCurrentChapter, mCurrentPage)
            }
        }
    }

    override fun flingToLeft(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "flingToLeft")
        turnNextPage()
        return true
    }

    override fun flingToRight(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "flingToRight")
        turnPrePage()
        return false
    }

    override fun flingToTop(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "flingToTop")
        return false
    }

    override fun flingToBottom(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Log.i(TAG, "flingToBottom")
        return false
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_PAGE = "page"
        private const val KEY_CHAPTER = "chapter"

        private const val BASE_URL = "https://mhpic.manhualang.com/comic/"
        private val CODE = "J" //漫画分类
        private val NAME = "绝世唐门条漫版" //漫画名
        private val PAGES = "话GQ" //漫画名
    }
}
