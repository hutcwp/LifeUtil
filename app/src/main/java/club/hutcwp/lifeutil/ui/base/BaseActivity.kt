package club.hutcwp.lifeutil.ui.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import com.google.android.material.snackbar.Snackbar

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.app.AppGlobal

/**
 * Created by hutcwp on 2017/4/15.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

abstract class BaseActivity : AppCompatActivity() {


    @get:LayoutRes
    protected abstract val layoutId: Int

    protected abstract fun initViews(savedInstanceState: Bundle?)

    protected abstract fun loadData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTheme()
        setContentView(layoutId)
        initViews(savedInstanceState)
        loadData()
    }

    // 给左上角图标的左边加上一个返回的按钮
    protected fun setDisplayHomeAsUpEnabled(enable: Boolean) {
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(enable)
    }

    /**
     * 菜单创建
     * @param item 菜单Item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 设置主题
     */
    private fun initTheme() {
        val pf = getSharedPreferences(AppGlobal.FILE_NAME, Context.MODE_PRIVATE)
        val themeIndex = pf.getInt("theme", 0)
        when (themeIndex) {
            0 -> setTheme(R.style.LapisBlueTheme)
            1 -> setTheme(R.style.PaleDogwoodTheme)
            2 -> setTheme(R.style.GreeneryTheme)
            3 -> setTheme(R.style.PrimroseYellowTheme)
            4 -> setTheme(R.style.FlameTheme)
            5 -> setTheme(R.style.IslandParadiseTheme)
            6 -> setTheme(R.style.KaleTheme)
            7 -> setTheme(R.style.PinkYarrowTheme)
            8 -> setTheme(R.style.NiagaraTheme)
        }
    }

    /**
     * showSnack
     *
     * @param s 要显示的内容
     */
    fun showSnack(view: View, s: String) {
        val snackbar = Snackbar.make(view, s, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
