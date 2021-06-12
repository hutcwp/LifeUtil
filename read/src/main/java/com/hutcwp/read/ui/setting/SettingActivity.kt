package com.hutcwp.read.ui.setting


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.ColorInt
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.hutcwp.common.mvp.BaseActivity
import com.hutcwp.read.R
import com.hutcwp.read.event.ThemeChangedEvent
import com.hutcwp.read.util.ThemeUtil
import me.hutcwp.constants.AppGlobal
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseActivity(), ColorChooserDialog.ColorCallback {

    override fun bindLayout() = R.layout.read_activity_setting

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initView() {
        fragmentManager.beginTransaction().replace(R.id.contentLayout, SettingFragment()).commit()
    }

    override fun onColorSelection(dialog: ColorChooserDialog, @ColorInt selectedColor: Int) {
        if (selectedColor == ThemeUtil.getThemeColor(this, R.attr.colorPrimary))
            return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = selectedColor
        }

        when (selectedColor) {
            resources.getColor(R.color.lapis_blue) -> {
                setTheme(R.style.LapisBlueTheme)
                saveTheme(0)
            }
            resources.getColor(R.color.pale_dogwood) -> {
                setTheme(R.style.PaleDogwoodTheme)
                saveTheme(1)
            }
            resources.getColor(R.color.greenery) -> {
                setTheme(R.style.GreeneryTheme)
                saveTheme(2)
            }
            resources.getColor(R.color.primrose_yellow) -> {
                setTheme(R.style.PrimroseYellowTheme)
                saveTheme(3)
            }
            resources.getColor(R.color.flame) -> {
                setTheme(R.style.FlameTheme)
                saveTheme(4)
            }
            resources.getColor(R.color.island_paradise) -> {
                setTheme(R.style.IslandParadiseTheme)
                saveTheme(5)
            }
            resources.getColor(R.color.kale) -> {
                setTheme(R.style.KaleTheme)
                saveTheme(6)
            }
            resources.getColor(R.color.pink_yarrow) -> {
                setTheme(R.style.PinkYarrowTheme)
                saveTheme(7)
            }
            resources.getColor(R.color.niagara) -> {
                setTheme(R.style.NiagaraTheme)
                saveTheme(8)
            }
        }
        //重新装载Fragment
        fragmentManager.beginTransaction().replace(R.id.contentLayout, SettingFragment()).commit()
        EventBus.getDefault().post(ThemeChangedEvent(selectedColor))

    }

    private fun saveTheme(theme: Int) {
        val pf = getSharedPreferences(AppGlobal.SHARE_FILE_NAME, Context.MODE_PRIVATE)
        val editor = pf.edit()
        editor.putInt(AppGlobal.FL_CUR_THEME_SP, theme)
        editor.apply()
    }


    companion object {

        fun createActivity(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }

}
