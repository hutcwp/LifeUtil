package com.hutcwp.live.livebiz.ui.component.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.example.annotations.mvp.DelegateBind
import com.hutcwp.live.livebiz.base.util.MLog
import com.hutcwp.framwork.component.Component
import com.hutcwp.live.livebiz.ui.component.bean.Playable
import com.hutcwp.live.livebiz.ui.view.CustomVideoView
import com.hutcwp.livebiz.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@DelegateBind(presenter = VideoComponentPresenter::class)
class VideoComponent : com.hutcwp.framwork.component.Component<VideoComponentPresenter?, IVideoComponent?>(), IVideoComponent {
    private val videoPath = "http://alcdn.hls.xiaoka.tv/2017427/14b/7b3/Jzq08Sl8BbyELNTo/index.m3u8"
    private val mv = "http://fs.mv.web.kugou.com/202003072329/8989f6eff9ffc74a04ba6a4a0ae317af/G174/M04/01/19/jpQEAF3byymAX1GpANQ4qI7mBnw356.mp4"
    private val mp3 = "https://webfs.yun.kugou.com/202003072257/f2f39357022fca54713dc267b98a1997/G180/M07/04/08/9A0DAF3FKAqATsbCACy595OooJM759.mp3"
    private var videoView: CustomVideoView? = null

    private var curPlayable: Playable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.component_video, container, false)
        initVideoView(root)
        return root
    }

    private fun initVideoView(rootView: View) {
        videoView = rootView.findViewById(R.id.videoView)
        playMedia(Playable("测试", videoPath, videoPath))
    }

    init {
        EventBus.getDefault().register(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun playMedia(playable: Playable) {
        MLog.info(TAG, "playMedia: playable=$playable")
        videoView?.let {
            if (playable == curPlayable && it.isPlaying) {
                it.stopPlayback()
            }

            curPlayable = playable
            it.setVideoURI(Uri.parse(playable.mv))
            it.start()
            it.setOnCompletionListener { Toast.makeText(context, "播放完成了", Toast.LENGTH_SHORT).show() }
            it.setMediaController(MediaController(context))
        }
    }

    companion object {
        const val TAG = "VideoComponent"
    }
}