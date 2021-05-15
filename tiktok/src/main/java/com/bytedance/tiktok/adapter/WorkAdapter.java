package com.bytedance.tiktok.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.tiktok.R;
import com.bytedance.tiktok.activity.PlayListActivity;
import com.bytedance.tiktok.base.BaseRvAdapter;
import com.bytedance.tiktok.base.BaseRvViewHolder;
import com.bytedance.tiktok.bean.VideoBean;
import com.bytedance.tiktok.utils.NumUtils;
import java.util.List;

/**
 * create by libo
 * create on 2020-05-21
 * description
 */
public class WorkAdapter extends BaseRvAdapter<VideoBean, WorkAdapter.WorkViewHolder> {

    public WorkAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(WorkViewHolder holder, VideoBean videoBean, final int position) {
        holder.ivCover.setImageResource(videoBean.getCoverRes());
        holder.tvLikeCount.setText(NumUtils.numberFilter(videoBean.getLikeCount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayListActivity.initPos = position;
                WorkAdapter.this.getContext().startActivity(new Intent(WorkAdapter.this.getContext(), PlayListActivity.class));
            }
        });
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rooView = LayoutInflater.from(getContext()).inflate(R.layout.item_work, parent, false);
        return new WorkViewHolder(rooView);
    }

    public class WorkViewHolder extends BaseRvViewHolder {
        ImageView ivCover;
        TextView tvLikeCount;

        public WorkViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvLikeCount = itemView.findViewById(R.id.tv_likecount);

        }
    }

}
