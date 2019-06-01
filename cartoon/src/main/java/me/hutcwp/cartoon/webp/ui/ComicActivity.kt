package me.hutcwp.cartoon.webp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.crt_activity_comic.*
import me.hutcwp.cartoon.R
import me.hutcwp.cartoon.webp.db.ComicDBRepo

class ComicActivity : AppCompatActivity() {

    private var mChapter = 0
    private var mPage = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crt_activity_comic)

        loadComic()
    }

    private fun loadComic() {
        Log.i(TAG, "loadComic")
        val img = getPageUrl()
        Glide.with(this)
                .load(img)
                .transition(DrawableTransitionOptions().crossFade(200))
                .into(ivComic)
    }


    private fun getPageUrl(): String? {
        return ComicDBRepo.getComic(this, "话", mChapter, mPage)?.url
    }

    companion object {
        const val TAG = "ComicActivity"
    }
}
