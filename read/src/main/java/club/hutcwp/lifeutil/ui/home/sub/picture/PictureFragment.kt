package club.hutcwp.lifeutil.ui.home.sub.picture

import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.adpter.PhotoAdapter
import club.hutcwp.lifeutil.entitys.Photo
import club.hutcwp.lifeutil.ui.MainActivity
import club.hutcwp.lifeutil.ui.base.BaseFragment
import hut.cwp.mvp.BindPresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@BindPresenter(presenter = PicturePresenter::class)
class PictureFragment : BaseFragment<PicturePresenter, IPicture>(), IPicture {

    private var adapter: PhotoAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var compositeDisposable = CompositeDisposable()

    override fun getLayoutId(): Int {
        return R.layout.read_fragment_gank_girl
    }

    override fun lazyFetchData() {
        getDatasByType()
    }

    private fun getDatasByType() {
        setRefreshing(true)
//        if (arguments!!.getInt("type") == 0) {
//            presenter.getGank()
//        } else {
//            presenter.getServer()
//        }

        getData(presenter.gerStrge())
    }

    override fun initViews() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipRefreshLayout)
        recyclerView = rootView.findViewById(R.id.grid_recycler)
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.addItemDecoration(SpacesItemDecoration(14))
        adapter = PhotoAdapter(context!!, mutableListOf())
        recyclerView?.adapter = adapter
        setListener()
    }

    private fun setListener() {
        swipeRefreshLayout?.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        swipeRefreshLayout?.setOnRefreshListener {
            presenter.serRefresh(true)
            getDatasByType()
        }

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (recyclerView.canScrollVertically(1)) {
                    // 此操作先与getDatasByType（）
                    presenter.serRefresh(false)
                    getDatasByType()
                }
            }
        })
    }

    override fun showSnack(msg: String) {
        (activity as MainActivity).showSnack(msg)
    }

    override fun setRefreshing(status: Boolean) {
        swipeRefreshLayout?.isRefreshing = status
    }

    override fun setNewData(data: List<Photo>) {
        adapter?.setNewData(data.toMutableList())
    }

    override fun addNewData(data: List<Photo>) {
        adapter?.addDatas(data)
    }

    private fun getData(observable: Observable<List<Photo>>) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Photo>> {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onNext(photos: List<Photo>) {
                        setNewData(photos)
                    }

                    override fun onError(e: Throwable) {
                        showSnack("加载失败")
                        setRefreshing(false)
                    }

                    override fun onComplete() {
                        showSnack("加载完成")
                        setRefreshing(false)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
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