package com.hutcwp.read.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.hutcwp.common.mvp.BaseActivity
import com.hutcwp.read.R
import com.hutcwp.read.event.ThemeChangedEvent
import com.hutcwp.read.http.ApiFactory
import com.hutcwp.read.ui.home.tabs.*
import com.hutcwp.read.ui.setting.AboutActivity
import com.hutcwp.read.ui.setting.SettingActivity
import com.hutcwp.read.ui.view.BottomNavigationView
import com.hutcwp.read.util.DoubleClickExit
import kotlinx.android.synthetic.main.read_activity_main.*
import kotlinx.android.synthetic.main.read_drawer_header.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.constants.RoutePath
import me.hutcwp.log.MLog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


@Route(path = RoutePath.READ_MAIN, name = "阅读首页")
class MainActivity : BaseActivity() {

    private var currentFragmentTag: String? = null


    override fun bindLayout() = R.layout.read_activity_main


    override fun initData(savedInstanceState: Bundle?) {
        initFragment(savedInstanceState)
    }

    override fun initView() {
        initNavigationViewHeader()

        initBottomNavigationView()
    }


    private fun initBottomNavigationView() {
        val list = mutableListOf<BottomNavigationView.ItemBean>()
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_read, TAB_NAME_READ))
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_girl, TAB_NAME_GIRL))
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_test, TAB_NAME_TEST))
        bottomNavigationView.itemBeanList = list
        bottomNavigationView?.setSelect(list[0].name)
        bottomNavigationView.onItemClickListener = object : BottomNavigationView.OnItemClickListener {
            override fun onClick(v: BottomNavigationView.ItemView) {
                MLog.info(TAG, "bottomNavigationView click: name=${v.itemBean.name}")
                bottomNavigationView?.setSelect(v.itemBean.name)
                when (v.itemBean.name) {
                    TAB_NAME_READ -> {
                        switchContent(FRAGMENT_TAG_READING)
                    }
                    TAB_NAME_GIRL -> {
                        switchContent(FRAGMENT_TAG_PHOTO)
                    }
                    TAB_NAME_TEST -> {
                        switchContent(FRAGMENT_TAG_ARTICLE)
                    }
                }
            }
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            switchContent(FRAGMENT_TAG_READING)
        } else {
            currentFragmentTag = savedInstanceState.getString(CURRENT_FRAGMENT_TAG)
            switchContent(currentFragmentTag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_TAG, currentFragmentTag)
    }

    private fun initNavigationViewHeader() {
        val headerView = navigation?.inflateHeaderView(R.layout.read_drawer_header)
        navigation?.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_read -> {
                    menuItem.isChecked = true
                    switchContent(FRAGMENT_TAG_READING)
                }
                R.id.navigation_photo -> {
                    menuItem.isChecked = true
                    switchContent(FRAGMENT_TAG_PHOTO)
                }
                R.id.navigation_setting -> {
                    menuItem.isChecked = true
                    SettingActivity.createActivity(this@MainActivity)
                }
                R.id.navigation_item_about -> {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                }
            }
            drawerLayout?.closeDrawer(GravityCompat.START)
            false
        }

        val ivRefresh = headerView?.findViewById<ImageView>(R.id.iv_refresh)
        ivRefresh?.setOnClickListener {
            refreshHeaderImg()
        }
        refreshHeaderImg()
    }

    @SuppressLint("SetTextI18n")
    private fun refreshHeaderImg() {
        val parentView = navigation.getHeaderView(0)
        val imageView = parentView.findViewById<ImageView>(R.id.iv_header)
        val tvDateYearMonth = parentView.findViewById<TextView>(R.id.tv_date_year_month)
        val tvDateDay = parentView.findViewById<TextView>(R.id.tv_date_day)
        val tvAuthor = parentView.findViewById<TextView>(R.id.tv_author)
        val tvTitle = parentView.findViewById<TextView>(R.id.tv_title)
        imageView.let { iv ->
            GlobalScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) {
                    ApiFactory.getGirlsController().getNewGankRandom()
                }

                MLog.info(TAG, "getNewGankRandom: response=$response")
                if (response.data?.isEmpty() != false) {
                    MLog.error(TAG, "bean data is empty!")
                    return@launch
                }

                response.data[0]?.let {
                    val dateList = formatDataStr(it.publishedAt)
                    tvDateDay.text = dateList[2]
                    tvDateYearMonth.text = dateList[0] + "-" + dateList[1]
                    tvAuthor.text = it.desc
                    tvTitle.text = it.title

                    Glide.with(iv.context)
                            .load(it.images[0])
//                            .placeholder(R.drawable.drawer_header_bg)
                            .error(R.drawable.drawer_header_bg)
                            .into(iv)
                }
            }
        }
    }

    private fun formatDataStr(dateStr: String): List<String> {
        val date = dateStr.split(" ")[0].split("-")
        return date
    }

    fun initDrawer(toolbar: Toolbar?) {
        if (toolbar != null) {
            val mDrawerToggle = object : ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.refresh, R.string.refresh) {
            }
            mDrawerToggle.syncState()
            drawerLayout?.addDrawerListener(mDrawerToggle)
        }
    }

    fun switchContent(goalFragmentTag: String?) {
        MLog.info(TAG, "switchContent: cur=$currentFragmentTag, new=$goalFragmentTag")
        if (goalFragmentTag == null || currentFragmentTag == goalFragmentTag) {
            return
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        val currentFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
        if (currentFragment != null) {
            ft.hide(currentFragment)
        }

        var foundFragment = supportFragmentManager.findFragmentByTag(goalFragmentTag)
        if (foundFragment == null) {
            foundFragment = when (goalFragmentTag) {
                FRAGMENT_TAG_PHOTO -> WrapPhotoFragment()
                FRAGMENT_TAG_READING -> WrapGankFragment()
                FRAGMENT_TAG_ARTICLE -> WrapReadFragment()
                else -> null
            }
        }

        when {
            foundFragment == null -> {
                MLog.error(TAG, "foundFragment is null, return.")
            }
            foundFragment.isAdded -> {
                ft.show(foundFragment)
            }
            else -> {
                ft.add(R.id.layout_content, foundFragment, goalFragmentTag)
            }
        }
        ft.commitAllowingStateLoss()
        currentFragmentTag = goalFragmentTag
    }

    @Subscribe
    fun onMessageEvent(event: ThemeChangedEvent) {
        this.recreate()
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(drawerLayout!!, "再按一次退出", Snackbar.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        }
    }


    companion object {
        private const val TAG = "MainActivity"

        private const val CURRENT_FRAGMENT_TAG = "currentIndex"
        private const val FRAGMENT_TAG_PHOTO = "photo"
        private const val FRAGMENT_TAG_READING = "reading"
        private const val FRAGMENT_TAG_ARTICLE = "article"

        private const val TAB_NAME_READ = "read"
        private const val TAB_NAME_GIRL = "girl"
        private const val TAB_NAME_TEST = "test"
    }
}
