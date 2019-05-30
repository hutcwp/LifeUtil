package me.hutcwp.cartoon.webp.imagelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.hutcwp.cartoon.R;
import okhttp3.OkHttpClient;

/**
 * Created on 2018/6/27.
 *
 * @author ice
 */
public class ImageListActivity extends FragmentActivity {

    public static void startThis(Context context) {
        Intent intent = new Intent(context, ImageListActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private OkHttpClient okhttp = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        initView(savedInstanceState);
    }

    protected void initView(@Nullable Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMyAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mMyAdapter);
    }

    private static class MyAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private GankMeizhi mGankMeizhi;

        public MyAdapter(Context context) {
            mContext = context;
        }

        public void refreshData(GankMeizhi gankMeizhi) {
            mGankMeizhi = gankMeizhi;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            Glide.with(mContext)
                    .load("http://a.img.diaoyu-3.com/GGv8401-webp")
                    .into(myViewHolder.mImageView);
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


    private static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.meizhi_iv);
            mTextView = itemView.findViewById(R.id.desc_tv);
        }
    }

}
