package club.hutcwp.lifeutil.ui.home.other

import android.os.Bundle
import androidx.fragment.app.Fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.bumptech.glide.Glide

import club.hutcwp.lifeutil.R

class PicDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_girl_pic, container, false)
        Log.d("url", PicDetailActivity.EXTRA_IMAGE_URL)
        Glide.with(activity).load(PicDetailActivity.EXTRA_IMAGE_URL).into(view.findViewById<View>(R.id.image) as ImageView)
        return view
    }


}
