package club.hutcwp.lifeutil.adpter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.model.Girl;
import club.hutcwp.lifeutil.ui.home.other.PicDetailActivity;

/**
 * Created by hutcwp on 2017/4/13.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class GankGirlAdapter extends RecyclerView.Adapter<GankGirlAdapter.GirlViewHolder> {

    private List<Girl> girlList = new ArrayList<>();

    private Context mContext;


    public GankGirlAdapter(Context context, List<Girl> girlList) {

        mContext = context;
        this.girlList = girlList;

    }

    /**
     * 添加数据
     *
     * @param datas 新增的数据
     */
    public void addDatas(List<Girl> datas) {

        girlList.addAll(datas);

        notifyItemChanged(getItemCount());
    }

    /**
     * 设置新内容
     *
     * @param data 新内容
     */
    public void setNewData(List<Girl> data) {
        this.girlList = data;
        notifyDataSetChanged();
    }


    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent,
                false);//这个布局就是一个imageview用来显示图片
        GirlViewHolder holder = new GirlViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(GirlViewHolder holder, final int position) {

        ViewGroup.LayoutParams params = holder.iv.getLayoutParams();
        params.width = 520;
        params.height = (new Random().nextInt(100) + 600);
        holder.iv.setLayoutParams(params);

        holder.name.setText(girlList.get(position).getName());
        holder.date.setText(girlList.get(position).getDate());

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PicDetailActivity.newIntent(mContext, girlList.get(position).getUrl(), "");
                mContext.startActivity(intent);
            }
        });


        //使用params,width 和params.heght 去加载图片
        Glide.with(mContext)
                .load(girlList.get(position)
                        .getUrl())
                .override(params.width, params.height) //设置加载尺寸
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((holder).iv);//加载网络图片
    }


    @Override
    public int getItemCount() {
        return girlList == null ? 0 : girlList.size();
    }


    //自定义ViewHolder，用于加载图片
    class GirlViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView name;
        TextView date;

        GirlViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.img);
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


}
