package club.hutcwp.lifeutil.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import club.hutcwp.lifeutil.R;
import club.hutcwp.lifeutil.app.AppGlobal;
import club.hutcwp.lifeutil.event.ThemeChangedEvent;
import club.hutcwp.lifeutil.ui.base.BaseActivity;
import club.hutcwp.lifeutil.ui.girl.PhotoFragment;
import club.hutcwp.lifeutil.ui.reading.ReadFragment;
import club.hutcwp.lifeutil.ui.setting.AboutActivity;
import club.hutcwp.lifeutil.ui.setting.SettingActivity;
import club.hutcwp.lifeutil.util.DoubleClickExit;


public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;

    private String currentFragmentTag;

    private FragmentManager fragmentManager;

    private static final String FRAGMENT_TAG_PHOTO = "photo";
    private static final String FRAGMENT_TAG_READING = "reading";

    private DrawerLayout drawerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initViews(Bundle savedInstanceState) {
        drawerLayout = findViewById(R.id.drawerLayout);
        fragmentManager = getSupportFragmentManager();
        initNavigationViewHeader();
        initFragment(savedInstanceState);

    }


    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            switchContent(FRAGMENT_TAG_READING);
        } else {
            currentFragmentTag = savedInstanceState.getString(AppGlobal.CURRENT_INDEX);
            switchContent(currentFragmentTag);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(AppGlobal.CURRENT_INDEX, currentFragmentTag);
    }

    @Override
    protected void loadData() {


    }

    private void initNavigationViewHeader() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.inflateHeaderView(R.layout.drawer_header);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }


    /**
     * 用来开关DrawerLayout
     *
     * @param toolbar
     */
    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.refresh, R.string.refresh) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            //更新状态
            mDrawerToggle.syncState();
            mDrawerLayout.addDrawerListener(mDrawerToggle);
        }
    }

    private class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_read:
                    menuItem.setChecked(true);
                    switchContent(FRAGMENT_TAG_READING);
                    break;
                case R.id.navigation_photo:
                    menuItem.setChecked(true);
                    switchContent(FRAGMENT_TAG_PHOTO);
                    break;
                case R.id.navigation_setting:
                    menuItem.setChecked(true);
                    SettingActivity.createActivity(MainActivity.this);
                    break;
                case R.id.navigation_item_about:
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    break;
            }
            mDrawerLayout.closeDrawer(Gravity.START);
            return false;
        }
    }


    /**
     * 选择当前的Fragment
     *
     * @param name Fragment的名字
     */
    public void switchContent(String name) {

        Log.d("error", "switchContent");
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment foundFragment = fragmentManager.findFragmentByTag(name);
        if (foundFragment == null) {
            switch (name) {
                case FRAGMENT_TAG_PHOTO:
                    foundFragment = new PhotoFragment();
                    break;
                case FRAGMENT_TAG_READING:
                    foundFragment = new ReadFragment();
                    break;
            }
        }

        if (foundFragment == null) {
        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.layout_content, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }


    @Subscribe
    public void onMessageEvent(ThemeChangedEvent event) {
        Log.d("event", "msg");
        this.recreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(drawerLayout, "再按一次退出 App!", Snackbar.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }


    public void showSnack(String msg) {
        if (drawerLayout == null) {
            return;
        }

        showSnack(drawerLayout,msg);
    }

}
