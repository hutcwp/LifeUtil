package club.hutcwp.lifeutil.ui.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import club.hutcwp.lifeutil.R;

public class PicDetailActivity extends AppCompatActivity {

    public static String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_picture);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PicDetailFragment fragment = new PicDetailFragment();
        transaction.add(R.id.content2,fragment);
        transaction.commit();


    }


    /**
     * 启动该Activity的Intent
     * @param context
     * @param url
     * @param desc
     * @return
     */
    public static Intent newIntent(Context context, String url, String desc) {

        Intent intent = new Intent(context, PicDetailActivity.class);
        EXTRA_IMAGE_URL =url;
        intent.putExtra(PicDetailActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PicDetailActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }


}
