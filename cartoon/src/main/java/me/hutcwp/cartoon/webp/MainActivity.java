package me.hutcwp.cartoon.webp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.hutcwp.cartoon.R;
import me.hutcwp.cartoon.webp.gesture.SimpleOnGestureListener;
import me.hutcwp.cartoon.webp.webp.WebpBytebufferDecoder;
import me.hutcwp.cartoon.webp.webp.WebpResourceDecoder;

public class MainActivity extends AppCompatActivity implements SimpleOnGestureListener.SwipeViewDelegate {

    private ImageView mImageView;
    private ImageView mFackImageView;
    private TextView mTvPageTip;
    private Button mBtnGo;
    private EditText mEvPage;
    private EditText mEvChapter;
    private int mCurrentPage = 1;
    private int mCurrentChapter = 2;
    private final Handler handler = new Handler();
    GestureDetector mGestureDetector;
    SimpleOnGestureListener mSimpleOnGestureListener;

    private boolean isEnable = true;
    private static final String TAG = "MainActivity";
    private static final String KEY_PAGE = "page";
    private static final String KEY_CHAPTER = "chapter";

    private static final String BASE_URL = "https://mhpic.manhualang.com/comic/";
    private static final String CODE = "J"; //漫画分类
    private static final String NAME = "绝世唐门条漫版"; //漫画名
    private static final String PAGES = "话GQ"; //漫画名


    private List<Drawable> mChapterList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crt_activity_home);
        initData();
        initView();
        webpInit();
        toPage(mCurrentChapter, mCurrentPage);
        mSimpleOnGestureListener = new SimpleOnGestureListener();
        mSimpleOnGestureListener.setmSwipeViewDelegate(this);
        mGestureDetector = new GestureDetector(mSimpleOnGestureListener);
    }

    private void initData() {
        mCurrentPage = SpUtils.getInt(KEY_PAGE, 1, MainActivity.this);
        mCurrentChapter = SpUtils.getInt(KEY_CHAPTER, 1, MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpUtils.putInt(KEY_PAGE, mCurrentPage, MainActivity.this);
        SpUtils.putInt(KEY_CHAPTER, mCurrentChapter, MainActivity.this);
    }

    private void toPage(int chapter, int page) {
        mCurrentChapter = chapter;
        mCurrentPage = page;
        mChapterList.clear();
        initCurrentChapter(chapter, 1);
    }

    private String getImgUrl(final int chapter, final int page) {
        return BASE_URL + CODE + "/" + NAME + "/" + chapter + PAGES+"/" + page + ".jpg-mht.middle.webp";
    }

    public void initCurrentChapter(final int chapter, final int page) {
//        String img = "https://mhpic.manhualang.com/comic/"+"Y/妖神记/" + chapter + "话/" + page + ".jpg-mht.middle.webp";
        String img = getImgUrl(chapter, page);
        RequestOptions options =
                new RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this)
                .load(img)
                .apply(options).transition(new DrawableTransitionOptions().crossFade(200))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i(TAG, "onLoadFailed: e = " + e);
                        Log.i(TAG, "当前章节加载结束,page size =" + mChapterList.size());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadCurrentPageFromList(mCurrentChapter, mCurrentPage);
                            }
                        });
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i(TAG, "onResourceReady: chapter = " + mCurrentChapter + " page = " + mChapterList.size());
                        mChapterList.add(resource);
                        if (mCurrentPage == page) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    loadCurrentPageFromList(mCurrentChapter, mCurrentPage);
                                }
                            });
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                initCurrentChapter(chapter, page + 1);
                            }
                        });
                        return false;
                    }
                })
                .into(mFackImageView);
    }

    private void initView() {
        mImageView = findViewById(R.id.webp);
        mFackImageView = findViewById(R.id.fake_webp);
        mTvPageTip = findViewById(R.id.tv_page_tip);
        mBtnGo = findViewById(R.id.btnGo);
        mEvChapter = findViewById(R.id.etChapter);
        mEvPage = findViewById(R.id.etPage);
        mBtnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int page = Integer.valueOf(mEvPage.getText().toString().trim());
                    int chapter = Integer.valueOf(mEvChapter.getText().toString().trim());
                    if (isEnable && page > 0 && chapter > 0) {
                        isEnable = false;
                        mCurrentChapter = chapter;
                        mCurrentPage = page;
                        mChapterList.clear();
                        initCurrentChapter(chapter, 1);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ClickListener error ", e);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 从内存队列中获取
     *
     * @param chapter
     * @param page
     */
    @SuppressLint("CheckResult")
    private void loadCurrentPageFromList(final int chapter, int page) {
        Log.i(TAG, "loadCurrentPageFromList:list size = " + mChapterList.size() + " chapter = " + chapter + " page = " + page);
        isEnable = true;
        if (page == 0) {
            Log.i(TAG, "page == 0,向前翻一话");
            page = mCurrentPage = mChapterList.size();
        }

        if (mChapterList.size() > page - 1 && page - 1 >= 0) {
            Log.i(TAG, "set page=" + (page - 1) + " drawable = " + mChapterList.get(page - 1).hashCode());
            mImageView.setImageDrawable(mChapterList.get(page - 1));
            String format = getResources().getString(R.string.crt_string_current_page);
            mTvPageTip.setText(String.format(format, chapter, page));
        } else {
            Toast.makeText(MainActivity.this, "加载错误，请检查输入是否正确", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "loadCurrentPageFromList error");
        }
    }

    private void webpInit() {
        ResourceDecoder decoder = new WebpResourceDecoder(this);
        ResourceDecoder byteDecoder = new WebpBytebufferDecoder(this);
        // use prepend() avoid intercept by default decoder
        Glide.get(this).getRegistry()
                .prepend(InputStream.class, Drawable.class, decoder)
                .prepend(ByteBuffer.class, Drawable.class, byteDecoder);
    }

    private void turnPrePage() {
        Log.i(TAG, "turnPrePage");
        if (isEnable) {
            isEnable = false;
            mCurrentPage--;
            if (mCurrentChapter >= 1) {
                if (mCurrentPage >= 1) {
                    loadCurrentPageFromList(mCurrentChapter, mCurrentPage);
                } else {
                    //去前一话
                    mCurrentChapter--;
                    if (mCurrentChapter >= 1) {
                        mChapterList.clear();
                        initCurrentChapter(mCurrentChapter, 1);
                    } else {
                        mCurrentPage++;
                        Toast.makeText(MainActivity.this, "当前为第一页,没有更前了。", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                mCurrentPage++;
                Toast.makeText(MainActivity.this, "当前为第一页,没有更前了。", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void turnNextPage() {
        Log.i(TAG, "turnNextPage");
        if (isEnable) {
            isEnable = false;
            mCurrentPage++;
            if (mCurrentPage > 0 && mCurrentPage - 1 < mChapterList.size()) {
                loadCurrentPageFromList(mCurrentChapter, mCurrentPage);
            } else if (mCurrentPage > mChapterList.size()) {
                Log.i(TAG, "turnNextPage 翻后一话");
                //翻后一话
                mCurrentChapter++;
                mCurrentPage = 1;
                mChapterList.clear();
                initCurrentChapter(mCurrentChapter, mCurrentPage);
            }
        }
    }

    @Override
    public boolean flingToLeft(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "flingToLeft");
        turnNextPage();
        return true;
    }

    @Override
    public boolean flingToRight(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "flingToRight");
        turnPrePage();
        return false;
    }

    @Override
    public boolean flingToTop(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "flingToTop");
        return false;
    }

    @Override
    public boolean flingToBottom(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG, "flingToBottom");
        return false;
    }
}
