package com.hutcwp.live.livebiz.ui.component.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.annotations.mvp.DelegateBind
import com.hutcwp.live.livebiz.ui.component.Component
import com.hutcwp.live.livebiz.ui.component.bean.PlayInfo
import com.hutcwp.live.livebiz.ui.component.bean.Playable
import com.hutcwp.live.livebiz.ui.component.video.PageItemViewBinder
import com.hutcwp.livebiz.R
import me.drakeet.multitype.MultiTypeAdapter
import org.greenrobot.eventbus.EventBus

/**
 * Created by hutcwp on 2020-03-08 02:30
 * email: caiwenpeng@yy.com
 * YY: 909076244
 */
@DelegateBind(presenter = RecommendPresenter::class)
class RecommendComponent : Component<RecommendPresenter?, IRecommend?>(), IRecommend {
    private var playListView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_recommend, container, false)
        initView(view)
        return view
    }

    private fun initView(rootView: View) {
        playListView = rootView.findViewById(R.id.musicListView)
    }

    override fun updatePlayList(playInfo: PlayInfo?) {
        playInfo?.let {
            val adapter = MultiTypeAdapter()
            activity?.runOnUiThread {
                playListView?.adapter = adapter
                val pageItemViewBinder = PageItemViewBinder(context!!)
                pageItemViewBinder.setOnItemClick(object : PageItemViewBinder.OnItemClick {
                    override fun onItemClick(playable: Playable) {
                        EventBus.getDefault().post(playable)
                    }
                })
                adapter.register(pageItemViewBinder)
                adapter.items = it.playInfoList.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }
    }


}