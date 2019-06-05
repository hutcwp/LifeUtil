package club.hutcwp.lifeutil.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.app.AppGlobal
import club.hutcwp.lifeutil.event.ThemeChangedEvent
import club.hutcwp.lifeutil.ui.base.BaseActivity
import club.hutcwp.lifeutil.ui.home.top.PhotoFragment
import club.hutcwp.lifeutil.ui.home.top.ReadFragment
import club.hutcwp.lifeutil.ui.setting.AboutActivity
import club.hutcwp.lifeutil.ui.setting.SettingActivity
import club.hutcwp.lifeutil.util.DoubleClickExit
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

@Route(path = "/read/main")
class MainActivity : BaseActivity() {

    private var mDrawerLayout: DrawerLayout? = null

    private var currentFragmentTag: String? = null

    private var fragmentManager: FragmentManager? = null

    private var drawerLayout: DrawerLayout? = null

    override val layoutId: Int
        get() = R.layout.read_activity_main


    override fun initViews(savedInstanceState: Bundle?) {
        drawerLayout = findViewById(R.id.drawerLayout)
        fragmentManager = supportFragmentManager
        initNavigationViewHeader()
        initFragment(savedInstanceState)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }


    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            switchContent(FRAGMENT_TAG_READING)
        } else {
            currentFragmentTag = savedInstanceState.getString(AppGlobal.CURRENT_INDEX)
            switchContent(currentFragmentTag)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(AppGlobal.CURRENT_INDEX, currentFragmentTag)
    }

    override fun loadData() {


    }

    private fun initNavigationViewHeader() {
        mDrawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val navigationView = findViewById<View>(R.id.navigation) as NavigationView
        navigationView.inflateHeaderView(R.layout.read_drawer_header)
        navigationView.setNavigationItemSelectedListener(NavigationItemSelected())
    }


    /**
     * 用来开关DrawerLayout
     *
     * @param toolbar
     */
    fun initDrawer(toolbar: Toolbar?) {
        if (toolbar != null) {
            val mDrawerToggle = object : ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.refresh, R.string.refresh) {
                override fun onDrawerOpened(drawerView: View) {
                    super.onDrawerOpened(drawerView)
                }

                override fun onDrawerClosed(drawerView: View) {
                    super.onDrawerClosed(drawerView)
                }
            }
            //更新状态
            mDrawerToggle.syncState()
            mDrawerLayout!!.addDrawerListener(mDrawerToggle)
        }
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
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
            return false
        }
    }


    /**
     * 选择当前的Fragment
     *
     * @param name Fragment的名字
     */
    fun switchContent(name: String?) {

        Log.d("error", "switchContent")
        if (currentFragmentTag != null && currentFragmentTag == name)
            return

        val ft = fragmentManager!!.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        val currentFragment = fragmentManager!!.findFragmentByTag(currentFragmentTag)
        if (currentFragment != null) {
            ft.hide(currentFragment)
        }

        var foundFragment = fragmentManager!!.findFragmentByTag(name)
        if (foundFragment == null) {
            when (name) {
                FRAGMENT_TAG_PHOTO -> foundFragment = PhotoFragment()
                FRAGMENT_TAG_READING -> foundFragment = ReadFragment()
            }
        }

        if (foundFragment == null) {
        } else if (foundFragment.isAdded) {
            ft.show(foundFragment)
        } else {
            ft.add(R.id.layout_content, foundFragment, name)
        }
        ft.commit()
        currentFragmentTag = name
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
        if (mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            if (!DoubleClickExit.check()) {
                Snackbar.make(drawerLayout!!, "再按一次退出 App!", Snackbar.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        }
    }


    fun showSnack(msg: String) {
        if (drawerLayout == null) {
            return
        }

        showSnack(drawerLayout!!, msg)
    }

    companion object {

        private val FRAGMENT_TAG_PHOTO = "photo"
        private val FRAGMENT_TAG_READING = "reading"
    }

}
