package com.bytedance.tiktok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.example.tiktok.R;
import com.bytedance.tiktok.base.BaseRvAdapter;
import com.bytedance.tiktok.base.BaseRvViewHolder;
import com.bytedance.tiktok.bean.VideoBean;
import com.bytedance.tiktok.view.ControllerView;
import com.bytedance.tiktok.view.LikeView;
import java.util.List;


/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class VideoAdapter extends BaseRvAdapter<VideoBean, VideoAdapter.VideoViewHolder> {

    public VideoAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(final VideoViewHolder holder, final VideoBean videoBean, int position) {
        holder.controllerView.setVideoData(videoBean);

        holder.ivCover.setImageResource(videoBean.getCoverRes());

        holder.likeView.setOnLikeListener(new LikeView.OnLikeListener() {
            @Override
            public void onLikeListener() {
                if (!videoBean.isLiked()) {  //未点赞，会有点赞效果，否则无
                    holder.controllerView.like();
                }

            }
        });
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    public class VideoViewHolder extends BaseRvViewHolder {
        LikeView likeView;
        ControllerView controllerView;
        ImageView ivCover;

        public VideoViewHolder(View itemView) {
            super(itemView);

            likeView = itemView.findViewById(R.id.likeview);
            controllerView = itemView.findViewById(R.id.controller);
            ivCover = itemView.findViewById(R.id.iv_cover);

        }
    }
}
