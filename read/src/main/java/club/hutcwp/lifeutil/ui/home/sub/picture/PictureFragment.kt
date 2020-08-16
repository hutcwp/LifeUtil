package club.hutcwp.lifeutil.ui.home.sub.picture

import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.ui.home.adpter.PhotoAdapter
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.mvp.BindPresenter
import kotlinx.android.synthetic.main.read_fragment_gank_girl.*

@BindPresenter(presenter = PicturePresenter::class)
class PictureFragment : BaseFragment<PicturePresenter, IPicture>(), IPicture {

    private var adapter: PhotoAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_gank_girl
    }

    override fun lazyFetchData() {
        getDatasByType()
    }

    private fun getDatasByType() {
        setRefreshing(true)
        presenter.getGank()
    }

    override fun initViews() {
        adapter = PhotoAdapter(context!!, null)
        grid_recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        grid_recycler.addItemDecoration(SpacesItemDecoration(14))
        grid_recycler.adapter = adapter
        setting()
    }

    private fun setting() {
        swipRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipRefreshLayout.setOnRefreshListener {
            presenter.resetPage(true)
            getDatasByType()
        }

        grid_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!grid_recycler.canScrollVertically(1)) {
                    presenter.resetPage(false)
                    getDatasByType()
                }
            }
        })
    }

    override fun showSnack(msg: String) {
        (activity as MainActivity).showSnack(msg)
    }

    override val data: List<Photo>
        get() = TODO("Not yet implemented")

    override fun setRefreshing(status: Boolean) {
        swipRefreshLayout.isRefreshing = status
    }

    override fun setNewData(data: List<Photo>) {
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(data: List<Photo>) {
        adapter?.addDatas(data)
    }

    override fun addNewData(pos: Int, data: List<Photo>) {
        TODO("Not yet implemented")
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