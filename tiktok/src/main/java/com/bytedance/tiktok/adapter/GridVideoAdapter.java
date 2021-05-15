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
import com.bytedance.tiktok.view.IconFontTextView;

import java.util.List;


/**
 * create by libo
 * create on 2020-05-20
 * description
 */
public class GridVideoAdapter extends BaseRvAdapter<VideoBean, GridVideoAdapter.GridVideoViewHolder> {

    public GridVideoAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(GridVideoViewHolder holder, VideoBean videoBean, final int position) {
        holder.ivCover.setBackgroundResource(videoBean.getCoverRes());
        holder.tvContent.setText(videoBean.getContent());
        holder.tvDistance.setText(videoBean.getDistance() + " km");
        holder.ivHead.setImageResource(videoBean.getUserBean().getHead());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayListActivity.initPos = position;
                GridVideoAdapter.this.getContext().startActivity(new Intent(GridVideoAdapter.this.getContext(), PlayListActivity.class));
            }
        });
    }

    @NonNull
    @Override
    public GridVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gridvideo, parent, false);
        return new GridVideoViewHolder(view);
    }

    public class GridVideoViewHolder extends BaseRvViewHolder {
        ImageView ivCover;
        TextView tvContent;
        IconFontTextView tvDistance;
        ImageView ivHead;

        public GridVideoViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            ivHead = itemView.findViewById(R.id.iv_head);

        }
    }
}
