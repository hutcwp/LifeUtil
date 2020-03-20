package com.hutcwp.live.livebiz.ui.component.emoji.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hutcwp.live.livebiz.ui.component.emoji.IEmoticonsDataInterface;
import com.hutcwp.live.livebiz.ui.component.emoji.filter.EmoticonFilter;
import com.hutcwp.livebiz.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-13
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
public class EmoticonsPageAdapter<T extends IEmoticonsDataInterface> extends PagerAdapter {
    //private static final int EMOTICONS_PER_PAGE = 21;
    private Context mContext;
    private List<T> iconList = null;
    private final int iconsPerPage;
    private final int gridColumns;

    private EmoticonsGridAdapter.IEmoticonsInsertListener<T> mListener;

    public EmoticonsPageAdapter(
            Context context,
            List<T> list,
            EmoticonsGridAdapter.IEmoticonsInsertListener<T> listener,
            int iconsPerPage,
            int columns
    ) {
        mContext = context;
        iconList = list;
        mListener = listener;
        this.iconsPerPage = iconsPerPage;
        gridColumns = columns;
    }

    @Override
    public int getCount() {
        return (int) Math.ceil((double) iconList.size() / (double) iconsPerPage);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.emoticons_grid, null);

        List<T> gridList = new ArrayList<>();
        //表情页增加删除键
        if (iconList.size() > 0 && iconList.get(0) instanceof EmoticonFilter.SmileItem) {
            int initialPosition = position * (iconsPerPage - 1);
            for (int i = initialPosition; i < initialPosition + iconsPerPage - 1 && i < iconList.size(); i++) {
                gridList.add(iconList.get(i));
            }
            EmoticonFilter.SmileItem delIcon = new EmoticonFilter.SmileItem();
            delIcon.mText = "/{del";
            delIcon.mIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_emot_del);
            gridList.add((T) delIcon);
        } else {
            int initialPosition = position * iconsPerPage;
            for (int i = initialPosition; i < initialPosition + iconsPerPage && i < iconList.size(); i++) {
                gridList.add(iconList.get(i));
            }
        }

        GridView grid = layout.findViewById(R.id.emoticons_grid);
        grid.setNumColumns(gridColumns);
        EmoticonsGridAdapter adapter = new EmoticonsGridAdapter(mContext, gridList, mListener);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("hjinw", "onItemClick position = " + position);
            }
        });
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int positon, Object view) {
        container.removeView((View) view);
    }
}
