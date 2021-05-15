package com.bytedance.tiktok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.tiktok.R;
import com.bytedance.tiktok.base.BaseRvAdapter;
import com.bytedance.tiktok.base.BaseRvViewHolder;
import com.bytedance.tiktok.bean.VideoBean;

import java.util.List;


/**
 * create by libo
 * create on 2020-05-25
 * description
 */
public class PrivateLetterAdapter extends BaseRvAdapter<VideoBean.UserBean, PrivateLetterAdapter.PrivateLetterViewHolder> {

    public PrivateLetterAdapter(Context context, List<VideoBean.UserBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(PrivateLetterViewHolder holder, VideoBean.UserBean userBean, int position) {
        holder.ivHead.setImageResource(userBean.getHead());
        holder.tvName.setText(userBean.getNickName());
    }

    @NonNull
    @Override
    public PrivateLetterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_private_letter,parent, false);
        return new PrivateLetterViewHolder(view);
    }

    public class PrivateLetterViewHolder extends BaseRvViewHolder {
        ImageView ivHead;
        TextView tvName;

        public PrivateLetterViewHolder(View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.iv_head);
            tvName = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
