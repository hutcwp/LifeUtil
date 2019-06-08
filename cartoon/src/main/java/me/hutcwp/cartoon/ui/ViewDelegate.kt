package me.hutcwp.cartoon.ui

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import me.hutcwp.cartoon.R

/**
 *
 * Created by hutcwp on 2019-06-01 01:44
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
class ViewDelegate(activity: MainActivity) {

    var mActivity: MainActivity = activity
    var mImageView: ImageView? = null
    var mFackImageView: ImageView? = null
    var mTvPageTip: TextView? = null
    var mBtnGo: Button? = null
    var mEvPage: EditText? = null
    var mEvChapter: EditText? = null

    init {
        initView()
    }

    private fun initView() {
        mImageView = mActivity.findViewById(R.id.webp)
        mFackImageView = mActivity.findViewById(R.id.fake_webp)
        mTvPageTip = mActivity.findViewById(R.id.tv_page_tip)
        mBtnGo = mActivity.findViewById(R.id.btnGo)
        mEvChapter = mActivity.findViewById(R.id.etChapter)
        mEvPage = mActivity.findViewById(R.id.etPage)
        mBtnGo!!.setOnClickListener {
            mActivity.btnGoClick()
        }
    }


}