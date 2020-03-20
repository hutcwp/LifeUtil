package com.hutcwp.live.livebiz.ui.component.emoji.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.hutcwp.live.livebiz.ui.component.emoji.IEmoticonsDataInterface;
import com.hutcwp.livebiz.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-14
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */
public class EmoticonsGridAdapter<T extends IEmoticonsDataInterface> extends BaseAdapter {
    private Context mContext;
    private List<T> iconList;

    public interface IEmoticonsInsertListener<T> {
        public void onInsertSmiley(T t);
    }

    private IEmoticonsInsertListener<T> mListener;

    public EmoticonsGridAdapter(Context context, List<T> list, IEmoticonsInsertListener<T> listener) {
        mContext = context;
        iconList = list;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return iconList.size();
    }

    @Override
    public T getItem(int position) {
        return iconList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.emoticons_item, null, false);
        }
        T t = getItem(position);
        if (t != null) {
            ImageView emoticon = (ImageView) view.findViewById(R.id.emoticon_view);
            /*
             *ImageView's method setAlpha() is used from API 11.
             * So that use ImageView setAlpha() will ca
             * use no such method exception in Android 2.3.3 or before.
             * Use Drawable setAlpha() to replace it.
             */
            //emoticon.setImageBitmap(t.getBitmap());
            //emoticon.setAlpha(t.getAlphaValue());
            Drawable drawable = new BitmapDrawable(mContext.getResources(), t.getBitmap());
            drawable.setAlpha(t.getAlphaValue());
            emoticon.setImageDrawable(drawable);
            emoticon.setTag(t);

            emoticon.setOnClickListener(new View.OnClickListener() {
                @Override
                @SuppressWarnings("Unchecked")
                public void onClick(View v) {
                    mListener.onInsertSmiley((T) v.getTag());
                }
            });
        }
        return view;
    }
}
