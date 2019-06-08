package me.hutcwp.cartoon.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import me.hutcwp.cartoon.R

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.crt_activity_load.*
import me.hutcwp.cartoon.db.ComicDBRepo
import me.hutcwp.cartoon.db.ComicEntity

@Route(path = "/cartoon/load")
class LoadActivity : AppCompatActivity() {

    var mChapterList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crt_activity_load)
        initView()
    }

    private fun initView() {
        btnStart.setOnClickListener {
            //            startTask()
            startGrab()
        }
    }

    private fun startTask() {
//        var comic = ComicEntity()
//        comic.id = 0
//        comic.chapter = 1
//        comic.page = 1
//        comic.type = "话"
//        var img = "https://mhpic.manhualang.com/comic/" + "Y/妖神记/" + comic.chapter + "话/" + comic.page + ".jpg-mht.middle.webp";
//        comic.url = img
//        comic.name = "妖神记"

//        comicDao.insert(comic)

//        ComicDBRepo.deleteAll(this)

//        ComicDBRepo.insert(this, comic)
//        ComicDBRepo.insert(this, comic)
//
//        var c = ComicDBRepo.getComic(this, "话", 1, 1);
//        Log.i(TAG, "c =$c")
//
        ComicDBRepo.getAll(this).forEach {
            Log.i(TAG, " it =$it")
        }

//        var u = userDao.getByUid(100)
//        Log.i("hut", "name = ${u.name}")
    }

    private fun getImgUrl(chapter: Int, page: Int): String {

//        https://mhpic.isamanhua.com/comic/Y/妖神记/第225话1F/5.jpg-smh.low.webp
//        https://mhpic.manhualang.com/comic/Y/妖神记/224话1GQ/3.jpg-smh.low.webp
//        https://mhpic.isamanhua.com/comic/Y/妖神记/第244话2F/1.jpg-smh.low.webp
//        https://mhpic.manhualang.com/comic/Y/妖神记/166话下/1.jpg-mht.middle.webp
        return "https://mhpic.manhualang.com/comic/" + "Y/妖神记/" + chapter + "话/" + page + ".jpg-mht.middle.webp";

//        return "${BASE_URL}${CODE}/${NAME}/$chapter${MainActivity.PAGES}/$page.jpg-mht.middle.webp"
    }

    private val handler = Handler()
    private var mCurrentChapter = 1;
    private var mCurrentPage = 1;


    fun startGrab() {
        val img = getImgUrl(mCurrentChapter, mCurrentPage)
        Log.i(TAG, "chapter=$mCurrentChapter  ,page=$mCurrentPage")
        tvTip.text = "当前抓取：chapter=$mCurrentChapter  ,page=$mCurrentPage"
        val options = RequestOptions()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
        Glide.with(this)
                .load(img)
                .apply(options).transition(DrawableTransitionOptions().crossFade(200))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.i(TAG, "onLoadFailed: e = " + e!!)
                        Log.i(TAG, "当前章节加载结束,page size =" + mChapterList.size)
                        if (mChapterList.size == 0 || mCurrentChapter > 300) {
                            Log.i(TAG, "结束抓取")
                        } else {
                            mChapterList.clear()
                            mCurrentChapter++
                            mCurrentPage = 1
                            handler.post { startGrab() }
                        }
                        return true
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        Log.i(TAG, "onResourceReady: chapter = $mCurrentChapter page = $mCurrentPage")
                        mChapterList.add("1")
                        var comic = ComicEntity()
                        comic.name = "妖神记"
                        comic.type = "话"
                        comic.chapter = mCurrentChapter
                        comic.page = mCurrentPage
                        comic.url = img
                        mCurrentPage++
                        ComicDBRepo.insert(this@LoadActivity, comic)
                        handler.post { startGrab() }
                        return false
                    }
                })
                .into(fake_webp)
    }

    companion object {
        const val TAG = "LoadActivity"

        private const val KEY_PAGE = "page"
        private const val KEY_CHAPTER = "chapter"

        private const val BASE_URL = "https://mhpic.manhualang.com/comic/"
        private val CODE = "J" //漫画分类
        private val NAME = "绝世唐门条漫版" //漫画名
        private val PAGES = "话GQ" //漫画名
    }
}
