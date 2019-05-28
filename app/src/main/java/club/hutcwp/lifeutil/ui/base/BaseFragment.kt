package club.hutcwp.lifeutil.ui.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import hut.cwp.mvp.MvpFragment
import hut.cwp.mvp.MvpPresenter
import hut.cwp.mvp.MvpView

/**
 * Created by hutcwp on 2017/4/15.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

abstract class BaseFragment<P : MvpPresenter<V>, V : MvpView> : MvpFragment<P, V>() {
    private var isViewPrepared: Boolean = false // 标识fragment视图已经初始化完毕
    private var hasFetchData: Boolean = false // 标识已经触发过懒加载数据
    lateinit var binding: ViewDataBinding

//    @get:LayoutRes
//    protected abstract val layoutId: Int

    abstract fun getLayoutId():Int

    //初始化View
    protected abstract fun initViews()

    //懒加载
    protected abstract fun lazyFetchData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        initViews()
        return binding.root

    }

    /**
     * 判断Fragment懒加载是否准备完成
     */
    private fun lazyFetchDataIfPrepared() {
        if (userVisibleHint && !hasFetchData && isViewPrepared) {
            hasFetchData = true
            lazyFetchData()
        }
    }

    /**
     * 用户可见
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.d(TAG, "setUserVisibleHint: ")
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared()
        }
    }

    /**
     * View创建完成
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        lazyFetchDataIfPrepared()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        hasFetchData = false
        isViewPrepared = false
    }

    open fun showSnack(msg: String) {
        if (binding.root != null) {
            val snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    companion object {

        private val TAG = "BaseFragment"
    }
}
