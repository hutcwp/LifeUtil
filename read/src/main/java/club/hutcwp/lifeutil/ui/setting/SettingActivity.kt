package club.hutcwp.lifeutil.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle


import androidx.annotation.ColorInt

import com.afollestad.materialdialogs.color.ColorChooserDialog

import org.greenrobot.eventbus.EventBus

import androidx.appcompat.widget.Toolbar
import club.hutcwp.lifeutil.R
import club.hutcwp.lifeutil.app.AppGlobal
import club.hutcwp.lifeutil.event.ThemeChangedEvent
import club.hutcwp.lifeutil.ui.base.BaseActivity
import club.hutcwp.lifeutil.util.ThemeUtil

class SettingActivity : BaseActivity(), ColorChooserDialog.ColorCallback {

    override val layoutId: Int
        get() = R.layout.read_activity_setting


    override fun initViews(savedInstanceState: Bundle?) {

        setDisplayHomeAsUpEnabled(true)

        fragmentManager.beginTransaction().replace(R.id.contentLayout, SettingFragment()).commit()
    }

    override fun loadData() {

    }

    override fun onColorSelection(dialog: ColorChooserDialog, @ColorInt selectedColor: Int) {

        if (selectedColor == ThemeUtil.getThemeColor(this, R.attr.colorPrimary))
            return
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(selectedColor)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = selectedColor
        }
        if (selectedColor == resources.getColor(R.color.lapis_blue)) {
            setTheme(R.style.LapisBlueTheme)
            saveTheme(0)
        } else if (selectedColor == resources.getColor(R.color.pale_dogwood)) {
            setTheme(R.style.PaleDogwoodTheme)
            saveTheme(1)
        } else if (selectedColor == resources.getColor(R.color.greenery)) {
            setTheme(R.style.GreeneryTheme)
            saveTheme(2)
        } else if (selectedColor == resources.getColor(R.color.primrose_yellow)) {
            setTheme(R.style.PrimroseYellowTheme)
            saveTheme(3)
        } else if (selectedColor == resources.getColor(R.color.flame)) {
            setTheme(R.style.FlameTheme)
            saveTheme(4)
        } else if (selectedColor == resources.getColor(R.color.island_paradise)) {
            setTheme(R.style.IslandParadiseTheme)
            saveTheme(5)
        } else if (selectedColor == resources.getColor(R.color.kale)) {
            setTheme(R.style.KaleTheme)
            saveTheme(6)
        } else if (selectedColor == resources.getColor(R.color.pink_yarrow)) {
            setTheme(R.style.PinkYarrowTheme)
            saveTheme(7)
        } else if (selectedColor == resources.getColor(R.color.niagara)) {
            setTheme(R.style.NiagaraTheme)
            saveTheme(8)
        }
        //重新装载Fragment
        fragmentManager.beginTransaction().replace(R.id.contentLayout, SettingFragment()).commit()
        EventBus.getDefault().post(ThemeChangedEvent(selectedColor))

    }

    private fun saveTheme(theme: Int) {
        val pf = getSharedPreferences(AppGlobal.FILE_NAME, Context.MODE_PRIVATE)
        val editor = pf.edit()
        setTheme(R.style.NiagaraTheme)
        editor.putInt("theme", theme)
        editor.apply()
    }

    companion object {

        fun createActivity(context: Context) {

            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }

}
