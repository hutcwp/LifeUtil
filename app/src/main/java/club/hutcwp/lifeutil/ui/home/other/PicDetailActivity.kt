package club.hutcwp.lifeutil.ui.home.other

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity

import club.hutcwp.lifeutil.R

class PicDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_picture)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = PicDetailFragment()
        transaction.add(R.id.content2, fragment)
        transaction.commit()


    }

    companion object {

        var EXTRA_IMAGE_URL = "image_url"
        val EXTRA_IMAGE_TITLE = "image_title"


        /**
         * 启动该Activity的Intent
         * @param context
         * @param url
         * @param desc
         * @return
         */
        fun newIntent(context: Context, url: String, desc: String): Intent {

            val intent = Intent(context, PicDetailActivity::class.java)
            EXTRA_IMAGE_URL = url
            intent.putExtra(PicDetailActivity.EXTRA_IMAGE_URL, url)
            intent.putExtra(PicDetailActivity.EXTRA_IMAGE_TITLE, desc)
            return intent
        }
    }


}
