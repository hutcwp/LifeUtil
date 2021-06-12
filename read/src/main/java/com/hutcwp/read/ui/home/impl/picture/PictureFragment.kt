package com.hutcwp.read.ui.home.impl.picture

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.hutcwp.read.R
import com.hutcwp.read.entitys.Photo
import com.hutcwp.read.ui.base.BaseFragment
import com.hutcwp.read.ui.home.adpter.NewPhotoAdapter
import com.hutcwp.read.ui.other.PicDetailActivity
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_gank_girl.*
import me.hutcwp.constants.RoutePath
import me.hutcwp.util.ResolutionUtils


@DelegateBind(presenter = PicturePresenter::class)
class PictureFragment : BaseFragment<PicturePresenter, IPicture>(), IPicture {

    private val photoAdapter by lazy { NewPhotoAdapter() }

    private var hasMore = true

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_gank_girl
    }

    override fun lazyFetchData() {
        presenter.initData()
    }

    override fun initViews() {
        photoAdapter.addChildClickViewIds(R.id.iv_img)
        photoAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.data[position] as Photo
            if (view.id == R.id.iv_img) {
                val imageUrl = item.img!!
                val imageTitle = item.name!!
                ARouter.getInstance()
                        .build(RoutePath.PIC_DETAIL_ACTIVITY)
                        .withString(PicDetailActivity.EXTRA_IMAGE_URL, imageUrl)
                        .withString(PicDetailActivity.EXTRA_IMAGE_TITLE, imageTitle)
                        .navigation()
            }
        }

        rv_pic.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_pic.addItemDecoration(SpacesItemDecoration(ResolutionUtils.convertDpToPixel(2f, context).toInt()))
        rv_pic.adapter = photoAdapter
    }

    override fun setListener() {
        swipRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipRefreshLayout.setOnRefreshListener {
            presenter.initData()
        }

        rv_pic.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!rv_pic.canScrollVertically(1)) {
                    if (hasMore) {
                        presenter.loadMore()
                    }
                }
            }
        })
    }

    override val data: List<Photo> get() = photoAdapter.data

    override fun setRefreshing(status: Boolean) {
        swipRefreshLayout.isRefreshing = status
    }

    override fun setNewData(data: List<Photo>) {
        photoAdapter.setNewInstance(data.toMutableList())
    }

    override fun addNewData(data: List<Photo>) {
        photoAdapter.addData(data)
    }

    override fun addNewData(pos: Int, data: List<Photo>) {

    }

    override fun hasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        if (!hasMore) {
            val footerView = View.inflate(context, R.layout.read_view_item_empty, null)
            photoAdapter.setFooterView(footerView)
        } else {
            photoAdapter.removeAllFooterView()
        }
    }


    companion object {

        private const val PARAM_URL = "url"

        fun instance(url: String): PictureFragment {
            val fragment = PictureFragment()
            val data = Bundle().apply {
                this.putString(PARAM_URL, url)
            }
            fragment.arguments = data
            return fragment
        }
    }

    /**
     * Recycler的分割线
     */
    private inner class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            outRect.top = space
        }
    }
}