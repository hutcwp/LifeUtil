package com.hutcwp.live.livebiz.ui.component.emoji;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.hutcwp.live.livebiz.ui.component.emoji.adapter.EmoticonsGridAdapter;
import com.hutcwp.live.livebiz.ui.component.emoji.adapter.EmoticonsPageAdapter;
import com.hutcwp.live.livebiz.ui.component.emoji.filter.EmoticonFilter;
import com.hutcwp.livebiz.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-31
 * Time: 下午7:35
 * To change this template use File | Settings | File Templates.
 */
public class EmoticonsView {
    private Context mContext;
    private View rootView;

    private ViewPager yyEmoticonsPager;
    protected ScrollEmoticonsPageAdapter mScrollEmoticonsPageAdapter;

    //游标显示布局
    protected LinearLayout cursorLayout;
    protected List<ImageView> cursorImageView = new ArrayList<ImageView>();

    public interface IEmoticonsMessageListener {
        public void sendMessageFromEmoticon(String msg);
    }

    public interface IMomoEmotionListener {
        public void onIMomoEmotionClick(String msg);
    }

    public interface IEmoticonLimitedListener {
        public boolean onEmoticonLimited(String msg);
    }

    private IEmoticonsMessageListener mListener;
    private IEmoticonLimitedListener mLimitedListener;

    public void setLimitedListener(IEmoticonLimitedListener listener) {
        this.mLimitedListener = listener;
    }

    public void setIMomoEmotionListener(IMomoEmotionListener mIMomoEmotionListener) {
        this.mIMomoEmotionListener = mIMomoEmotionListener;
    }

    private IMomoEmotionListener mIMomoEmotionListener;

    public EmoticonsView(Activity activity, IEmoticonsMessageListener listener, EditText editText) {
        rootView = LayoutInflater.from(activity).inflate(R.layout.emoticons_layout, null);
        mContext = activity;
        mListener = listener;
        init(editText);
    }

    public EmoticonsView(Context context, View root, IEmoticonsMessageListener listener, EditText editText) {
        rootView = root;
        mContext = context;
        mListener = listener;
        init(editText);
    }

    public EmoticonsView(
            Context
                    context, View
                    root, IEmoticonsMessageListener
                    listener, EmoticonsGridAdapter.IEmoticonsInsertListener<EmoticonFilter.SmileItem>
                    emoticonsInsertListener) {
        rootView = root;
        mContext = context;
        mListener = listener;
        initWithEmoticonsInsertListener(emoticonsInsertListener);
    }

    public View getRootView() {
        return rootView;
    }

    public void setVisibility(int visibility) {
        rootView.setVisibility(visibility);
    }

    public int getVisibility() {
        return rootView.getVisibility();
    }

    private void init(EditText editText) {
        cursorLayout = rootView.findViewById(R.id.cursor_layout);
        yyEmoticonsPager = rootView.findViewById(R.id.emoticons_pager);

        EmoticonsPageAdapter adapter = new EmoticonsPageAdapter<>(mContext,
                EmoticonFilter.getSmileList(mContext), getEmoticonsInsertListener(editText), 21, 7);

        List<EmoticonsPageAdapter> listAdapter = new ArrayList<>();
        listAdapter.add(adapter);
        mScrollEmoticonsPageAdapter = new ScrollEmoticonsPageAdapter(mContext, listAdapter);
        yyEmoticonsPager.setAdapter(mScrollEmoticonsPageAdapter);
        yyEmoticonsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPage = i;
                updateCursorLayout();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //禁用滚动边缘效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            yyEmoticonsPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        updateCursorLayout();

    }

    private void initWithEmoticonsInsertListener(
            EmoticonsGridAdapter.IEmoticonsInsertListener<EmoticonFilter.SmileItem> emoticonsInsertListener) {
        cursorLayout = rootView.findViewById(R.id.cursor_layout);
        yyEmoticonsPager = rootView.findViewById(R.id.emoticons_pager);

        EmoticonsPageAdapter adapter = new EmoticonsPageAdapter<EmoticonFilter.SmileItem>(mContext,
                EmoticonFilter.getSmileList(mContext), emoticonsInsertListener, 21, 7);

        List<EmoticonsPageAdapter> listAdapter = new ArrayList<EmoticonsPageAdapter>();
        listAdapter.add(adapter);
        mScrollEmoticonsPageAdapter = new ScrollEmoticonsPageAdapter(mContext, listAdapter);
        yyEmoticonsPager.setAdapter(mScrollEmoticonsPageAdapter);
        yyEmoticonsPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPage = i;
                updateCursorLayout();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //禁用滚动边缘效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            yyEmoticonsPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        updateCursorLayout();

    }

    private int mCurrentPage = 0;

    private void updateCursorLayout() {
        int count =
                mScrollEmoticonsPageAdapter.mAdapterList
                        .get(mScrollEmoticonsPageAdapter
                        .getAdapterIndex(mCurrentPage))
                        .getCount();
        int index = mScrollEmoticonsPageAdapter.getAdapterItemIndex(mCurrentPage);
        cursorLayout.removeAllViews();
        cursorImageView.clear();
        ImageView imageView;
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(mContext);
            if (i == index) {
                imageView.setImageResource(R.drawable.dot_xuanzhong);
            } else {
                imageView.setImageResource(R.drawable.dot_weixuanzhong);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.gravity = Gravity.TOP;
            cursorLayout.addView(imageView, layoutParams);
            cursorImageView.add(imageView);
        }
        //cursorLayout.setPadding(0,0,0,20);
        cursorLayout.setBackgroundColor(0xFFFFFFFF);
    }

    protected EmoticonsGridAdapter.IEmoticonsInsertListener<EmoticonFilter.SmileItem> getEmoticonsInsertListener(
            final EditText edit) {
        return new EmoticonsGridAdapter.IEmoticonsInsertListener<EmoticonFilter.SmileItem>() {
            @Override
            public void onInsertSmiley(EmoticonFilter.SmileItem s) {

                if (mIMomoEmotionListener != null) {
                    mIMomoEmotionListener.onIMomoEmotionClick(s.getText());
                }
                if (edit == null) {
                    return;
                }
                if (s.getText().equals("/{del")) {
                    final KeyEvent deleteEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL);
                    edit.onKeyDown(KeyEvent.KEYCODE_DEL, deleteEvent);
                    return;
                }
                String beforeAddSmileItemText = edit.getText().toString();
                Boolean isEndOfTheText = true;
                int beforeSeletionPosition = 0;
                String text = edit.getText().toString();
                if (edit.getSelectionStart() == text.length()) {
                    text += s.getText(); //s.mText;
                } else {
                    isEndOfTheText = false;
                    beforeSeletionPosition = edit.getSelectionStart();
                    text = text.substring(
                            0, edit.getSelectionStart()) + s.getText() + text.substring(
                            edit.getSelectionStart(), text.length());
                }
                if (mLimitedListener != null) {
                    boolean overLimited = mLimitedListener.onEmoticonLimited(text);
                    if (overLimited) {
                        return;
                    }
                }
                edit.setText(text);
                if (!edit.getText().toString().equals(text)) {
                    edit.setText(beforeAddSmileItemText);
                }
                if (isEndOfTheText) {
                    edit.setSelection(edit.getText().toString().length());
                } else {
                    if (beforeAddSmileItemText.equals(edit.getText().toString())) {
                        edit.setSelection(beforeSeletionPosition);
                    } else {
                        edit.setSelection(beforeSeletionPosition + s.getText().length());
                    }
                }
            }
        };
    }

    public class ScrollEmoticonsPageAdapter extends PagerAdapter {
        private Context mContext;
        private List<EmoticonsPageAdapter> mAdapterList;

        public ScrollEmoticonsPageAdapter(Context context, List<EmoticonsPageAdapter> adapterList) {
            mContext = context;
            mAdapterList = adapterList;
            initIndex(mAdapterList);
        }

        public int getAdapterIndex(int position) {
            return mAdapterIndex.get(position);
        }

        public int getAdapterItemIndex(int position) {
            return mAdapterItemIndex.get(position);
        }

        @Override
        public int getCount() {
            return mAdapterIndex.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return mAdapterList.get(getAdapterIndex(position)).instantiateItem(
                    container, getAdapterItemIndex(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }
    }

    private List<Integer> mAdapterIndex = new ArrayList<Integer>();
    private List<Integer> mAdapterItemIndex = new ArrayList<Integer>();

    private void initIndex(List<EmoticonsPageAdapter> adapterList) {
        for (int i = 0; i < adapterList.size(); i++) {
            for (int j = 0; j < adapterList.get(i).getCount(); j++) {
                mAdapterIndex.add(i);
                mAdapterItemIndex.add(j);
            }
        }
    }
}
