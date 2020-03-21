package com.hutcwp.game.sanguo.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import me.hutcwp.log.MLog
import me.hutcwp.util.ActivityCheckUtils

/**
 * Created by xufang on 2019/4/26.
 */
enum class PageManager {
    INSTANCE;

    companion object {
        val TAG = "PageManager"
    }

    enum class PAGE {
        NONE,
        WELCOME,
        GAME,
        END
    }

    data class FragmentInfo(val fragment: Fragment)

    private var mContainerId: Int = 0
    private var mCurrentPage: PAGE = PAGE.NONE
    private var mCachedPage: PAGE = PAGE.NONE
    private var mHasPause: Boolean = false
    private val mPageMap = HashMap<PAGE, FragmentInfo>(PAGE.values().size)

    fun init(containerId: Int) {
        MLog.info(TAG, "init")
        mContainerId = containerId
    }

    fun unInit() {
        MLog.info(TAG, "unInit")
        mCurrentPage = PAGE.NONE
        mCachedPage = PAGE.NONE
        mContainerId = 0
        mHasPause = false
        mPageMap.clear()
    }

    fun onResume(activity: FragmentActivity?) {
        MLog.info(TAG, "onResume")
        mHasPause = false
        if (mCachedPage != PAGE.NONE) {
            MLog.info(TAG, "onResume: to mCachedPage:$mCachedPage")
            toPage(mCachedPage, activity)
            mCachedPage = PAGE.NONE
        }
    }

    fun onPause() {
        MLog.info(TAG, "onPause")
        mHasPause = true
    }

    fun registerPage(page: PAGE, fragmentInfo: FragmentInfo) {
        mPageMap[page] = fragmentInfo
    }

    fun toPage(page: PAGE, activity: FragmentActivity?) {
        MLog.info(TAG, "toPage: $mCurrentPage to $page")
        if (page == PAGE.NONE || page == mCurrentPage) {
            return
        }
        if (!ActivityCheckUtils.checkActivityValid(activity)) {
            MLog.error(TAG, "toPage: activity invalid")
            return
        }
        if (mHasPause) {
            mCachedPage = page
            MLog.info(TAG, "toPage: cache page:$mCachedPage")
            return
        }

        for ((pageKey, fragmentInfo) in mPageMap) {
            if (pageKey == page) {
                mCurrentPage = page
                replaceFragment(fragmentInfo, activity!!.supportFragmentManager)
                break
            }
        }
    }

    private fun replaceFragment(fragmentInfo: FragmentInfo, fragmentManager: FragmentManager) {
        MLog.info(TAG, "replaceFragment $fragmentInfo")
        fragmentManager.beginTransaction().replace(mContainerId, fragmentInfo.fragment)
                .commitAllowingStateLoss()
    }
}