package com.hutcwp.read.ui.home.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.read.R
import com.bumptech.glide.Glide
import hut.cwp.util.MLog
import kotlinx.android.synthetic.main.read_fragment_girl_pic.*

class PicDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.read_fragment_girl_pic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        val imageUrl = arguments?.getString(PARAM_IMAGE_URL) ?: ""
        MLog.debug(TAG, "image url = $imageUrl")

        iv_image?.let {
            Glide.with(it)
                    .load(imageUrl)
                    .into(it)
        }
    }


    companion object {
        private const val TAG = "PicDetailFragment"
        private const val PARAM_IMAGE_URL = "PARAM_IMAGE_URL"

        fun newInstance(imageUrl: String): PicDetailFragment {
            val args = Bundle().apply {
                this.putString(PARAM_IMAGE_URL, imageUrl)
            }

            val fragment = PicDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
