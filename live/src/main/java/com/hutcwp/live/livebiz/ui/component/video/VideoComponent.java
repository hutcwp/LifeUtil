package com.hutcwp.live.livebiz.ui.component.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import com.hutcwp.live.livebiz.ui.component.Component;
import com.hutcwp.live.livebiz.ui.view.CustomVideoView;
import com.hutcwp.livebiz.R;


import androidx.annotation.Nullable;
import hut.cwp.mvp.BindPresenter;

@BindPresenter(presenter = VideoComponentPresenter.class)
public class VideoComponent extends Component<VideoComponentPresenter,IVideoComponent> implements IVideoComponent {

    public static final String TAG = "VideoComponent";

    private String videoPath = "http://alcdn.hls.xiaoka.tv/2017427/14b/7b3/Jzq08Sl8BbyELNTo/index.m3u8";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.component_video,container,false);
        initVideoView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initVideoView(View rootView) {
        CustomVideoView videoView = rootView.findViewById(R.id.videoView);

        videoView.setMediaController(new MediaController(getContext()));
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getContext(), "播放完成了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
