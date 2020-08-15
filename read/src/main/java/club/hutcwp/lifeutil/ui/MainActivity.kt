package club.hutcwp.lifeutil.ui


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.event.ThemeChangedEvent
import club.hutcwp.lifeutil.http.ApiFactory
import club.hutcwp.lifeutil.ui.base.BaseActivity
import club.hutcwp.lifeutil.ui.home.top.PhotoFragment
import club.hutcwp.lifeutil.ui.home.top.ReadFragment
import club.hutcwp.lifeutil.ui.setting.AboutActivity
import club.hutcwp.lifeutil.ui.setting.SettingActivity
import club.hutcwp.lifeutil.util.DoubleClickExit
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.read_activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hutcwp.log.MLog
import me.hutcwp.util.SingleToastUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


@Route(path = "/read/main")
class MainActivity : BaseActivity() {

    private var currentFragmentTag: String? = null

    override val layoutId: Int
        get() = R.layout.read_activity_main


    override fun initViews(savedInstanceState: Bundle?) {
        initNavigationViewHeader()
        initFragment(savedInstanceState)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        val list = mutableListOf<BottomNavigationView.ItemBean>()
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_read, "read"))
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_girl, "girl"))
        list.add(BottomNavigationView.ItemBean(R.drawable.ic_test, "test"))
        bottomNavigationView.itemBeanList = list
        bottomNavigationView?.setSelect(list[0].name)
        bottomNavigationView.onItemClickListener = object : BottomNavigationView.OnItemClickListener {
            override fun onClick(v: BottomNavigationView.ItemView) {
                MLog.info(TAG, "v.itemBean.name=${v.itemBean.name}")
                bottomNavigationView?.setSelect(v.itemBean.name)
                when (v.itemBean.name) {
                    "read" -> {
                        switchContent(FRAGMENT_TAG_READING)
                    }
                    "girl" -> {
                        switchContent(FRAGMENT_TAG_PHOTO)
                    }
                    "test" -> {
                        SingleToastUtil.showToast("test")
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

    override fun loadData() {
    }

    private fun initNavigationViewHeader() {
        navigation?.inflateHeaderView(R.layout.read_drawer_header)
        navigation?.setNavigationItemSelectedListener(NavigationItemSelected())
        addHeaderImg()
    }

    private fun addHeaderImg() {
        val view = navigation.getHeaderView(0)
        val ivRefresh = view.findViewById<ImageView>(R.id.iv_refresh)
        ivRefresh?.setOnClickListener {
            refreshHeaderImg(view)
        }
        refreshHeaderImg(view)
    }

    @SuppressLint("SetTextI18n")
    private fun refreshHeaderImg(parentView: View) {
        val imageView = parentView.findViewById<ImageView>(R.id.iv_header)
        val tvDateYearMonth = parentView.findViewById<TextView>(R.id.tv_date_year_month)
        val tvDateDay = parentView.findViewById<TextView>(R.id.tv_date_day)
        val tvAuthor = parentView.findViewById<TextView>(R.id.tv_author)
        val tvTitle = parentView.findViewById<TextView>(R.id.tv_title)
        imageView.let { iv ->
            GlobalScope.launch(Dispatchers.Main) {
                val bean = withContext(Dispatchers.IO) {
                    ApiFactory.getGirlsController().getNewGankRandom()
                }
                MLog.info(TAG, "bean=$bean")
                if (bean.data.isEmpty()) {
                    MLog.error(TAG, "bean data is empty.")
                    return@launch
                }
                bean.data[0]?.let {
                    val dateList = doHandleDate(it.publishedAt)
                    tvDateDay.text = dateList[2]
                    tvDateYearMonth.text = dateList[0] + "-" + dateList[1]
                    tvAuthor.text = it.desc
                    tvTitle.text = it.title
                    val options: RequestOptions = RequestOptions().placeholder(R.drawable.drawer_header_bg)
                    Glide.with(iv.context)
                            .load(it.images[0])
                            .apply(options)
                            .into(iv)
                }
            }
        }
    }

    private fun doHandleDate(dateStr: String): List<String> {
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
            when (goalFragmentTag) {
                FRAGMENT_TAG_PHOTO -> foundFragment = PhotoFragment()
                FRAGMENT_TAG_READING -> foundFragment = ReadFragment()
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
        Log.d("event", "msg")
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
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
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

    fun showSnack(msg: String) {
        drawerLayout?.let {
            showSnack(drawerLayout, msg)
        }
    }

    companion object {
        const val TAG = "MainActivity"
        private const val CURRENT_FRAGMENT_TAG = "currentIndex"
        private const val FRAGMENT_TAG_PHOTO = "photo"
        private const val FRAGMENT_TAG_READING = "reading"
    }

    private inner class NavigationItemSelected : NavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
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
                R.id.navigation_item_about -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
            }
            drawerLayout?.closeDrawer(GravityCompat.START)
            return false
        }
    }
}
