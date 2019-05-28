package club.hutcwp.lifeutil.ui.home.sub.picture

import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.PhotoAdapter
import club.hutcwp.lifeutil.databinding.FragmentGankGirlBinding
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.mvp.BindPresenter

@BindPresenter(presenter = PicturePresenter::class)
class PictureFragment : BaseFragment<PicturePresenter, IPicture>(), IPicture {


    private var adapter: PhotoAdapter? = null


    private var fragmentGankGirlBinding: FragmentGankGirlBinding? = null


    override fun getLayoutId(): Int {
        return R.layout.fragment_gank_girl
    }

    override fun lazyFetchData() {
        getDatasByType()
    }

    private fun getDatasByType() {
        setRefreshing(true)
        if (arguments!!.getInt("type") == 0) {
            presenter.getGank()
        } else {
            presenter.getServer()
        }
    }

    override fun initViews() {
        fragmentGankGirlBinding = binding as FragmentGankGirlBinding
        //可能会出现空指针异常
        adapter = PhotoAdapter(context!!, null)
        fragmentGankGirlBinding!!.gridRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        fragmentGankGirlBinding!!.gridRecycler.addItemDecoration(SpacesItemDecoration(14))
        fragmentGankGirlBinding!!.gridRecycler.setAdapter(adapter)
        setting()
    }

    /**
     * 注意，因放置在initView方法的最后，以避免出现空指针
     */
    fun setting() {
        fragmentGankGirlBinding!!.swipRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        fragmentGankGirlBinding!!.swipRefreshLayout.setOnRefreshListener {
            presenter.serRefresh(true)
            getDatasByType()
        }

        fragmentGankGirlBinding!!.gridRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!fragmentGankGirlBinding!!.gridRecycler.canScrollVertically(1)) {
                    // 此操作先与getDatasByType（）
                    presenter.serRefresh(false)
                    getDatasByType()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }


    override fun showSnack(msg: String) {
        (activity as MainActivity).showSnack(msg)
    }

    override fun setRefreshing(status: Boolean) {
        fragmentGankGirlBinding!!.swipRefreshLayout.setRefreshing(status)
    }

    override fun setNewData(data: List<Photo>) {
        adapter!!.setNewData(data.toMutableList())
    }

    override fun addNewData(data: List<Photo>) {
        adapter!!.addDatas(data)
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