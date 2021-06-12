package com.hutcwp.read.ui.home.sub.picture

import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hutcwp.read.R
import com.hutcwp.read.entitys.Photo
import com.hutcwp.read.ui.MainActivity
import com.hutcwp.read.ui.base.BaseFragment
import com.hutcwp.read.ui.home.adpter.NewPhotoAdapter
import com.hutcwp.read.ui.home.other.PicDetailActivity
import com.alibaba.android.arouter.launcher.ARouter
import hut.cwp.annotations.mvp.DelegateBind
import kotlinx.android.synthetic.main.read_fragment_gank_girl.*
import me.hutcwp.other.RoutePath


@DelegateBind(presenter = PicturePresenter::class)
class PictureFragment : BaseFragment<PicturePresenter, IPicture>(), IPicture {

    var adapter: NewPhotoAdapter? = null
    var hasMore = true


    fun updateHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        if (!hasMore) {
            val footerView = View.inflate(context, R.layout.read_view_item_empty, null)
            adapter?.setFooterView(footerView)
        } else {
            adapter?.removeAllFooterView()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_gank_girl
    }

    override fun lazyFetchData() {
        presenter.initData()
    }

    override fun initViews() {
        adapter = NewPhotoAdapter()
        // 先注册需要点击的子控件id（注意，请不要写在convert方法里）
        adapter?.addChildClickViewIds(R.id.img)
        // 设置子控件点击监听
        adapter?.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.data[position] as Photo
            if (view.id == R.id.img) {

                val imageUrl = item.img!!
                val imageTitle = item.name!!
                ARouter.getInstance()
                        .build(RoutePath.PIC_DETAIL_ACTIVITY)
                        .withString(PicDetailActivity.EXTRA_IMAGE_URL, imageUrl)
                        .withString(PicDetailActivity.EXTRA_IMAGE_TITLE, imageTitle)
                        .navigation()
            }
        }

        grid_recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        grid_recycler.addItemDecoration(SpacesItemDecoration(14))
        grid_recycler.adapter = adapter
        setting()
    }


    private fun setting() {
        swipRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipRefreshLayout.setOnRefreshListener {
            presenter.initData()
        }

        grid_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!grid_recycler.canScrollVertically(1)) {
                    if (hasMore) {
                        presenter.loadMore()
                    }
                }
            }
        })
    }

    override fun showSnack(msg: String) {
        (activity as MainActivity).showSnack(msg)
    }

    override val data: List<Photo>
        get() = adapter!!.data

    override fun setRefreshing(status: Boolean) {
        swipRefreshLayout.isRefreshing = status
    }

    override fun setNewData(data: List<Photo>) {
        adapter?.setNewInstance(data.toMutableList())
    }

    override fun addNewData(data: List<Photo>) {
        adapter?.addData(data)
    }

    override fun addNewData(pos: Int, data: List<Photo>) {

    }

    /**
     * Recycler的分割线
     */
    private inner class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.left = space
            outRect.right = space
            outRect.bottom = space
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space
            }
        }
    }
}