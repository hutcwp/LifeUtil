package com.hutcwp.read.ui.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import com.hutcwp.read.R;
import com.hutcwp.read.app.AppGlobal;
import com.hutcwp.read.util.FileSizeUtil;
import com.hutcwp.read.util.FileUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.hutcwp.BasicConfig;


public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference cleanCache;
    private Preference theme;
    private Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        cleanCache = findPreference("clean_cache");
        theme = findPreference("theme_color");

        String[] colorNames = getActivity().getResources().getStringArray(R.array.color_name);
        SharedPreferences sp = getActivity().getSharedPreferences(AppGlobal.INSTANCE.getFILE_NAME(),
                Context.MODE_PRIVATE);
        int currentThemeIndex = sp.getInt("theme", 0);

        if (currentThemeIndex >= colorNames.length) {
            theme.setSummary("自定义色");
        } else {

            theme.setSummary(colorNames[currentThemeIndex]);
        }

        cleanCache.setSummary(
                FileSizeUtil.INSTANCE.
                        getAutoFileOrFilesSize(
                                FileUtil.INSTANCE.getInternalCacheDir(BasicConfig.getApplicationContext()),
                                FileUtil.INSTANCE.getExternalCacheDir(BasicConfig.getApplicationContext())));
        theme.setOnPreferenceClickListener(this);
        cleanCache.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference == cleanCache) {
            disposable = Observable.just(FileUtil.INSTANCE.delete(FileUtil.INSTANCE.getInternalCacheDir(BasicConfig.getApplicationContext())))
                    .map(new Function<Boolean, Boolean>() {
                        @Override
                        public Boolean apply(Boolean result) throws Exception {
                            return result && FileUtil.INSTANCE.delete(FileUtil.INSTANCE.getExternalCacheDir(BasicConfig.getApplicationContext()));

                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean o) throws Exception {
                            cleanCache.setSummary(
                                    FileSizeUtil.INSTANCE.
                                            getAutoFileOrFilesSize(
                                                    FileUtil.INSTANCE.getInternalCacheDir(BasicConfig.getApplicationContext()),
                                                    FileUtil.INSTANCE.getExternalCacheDir(BasicConfig.getApplicationContext())));
                            Snackbar.make(getView(), "缓存已清除 (*^__^*)", Snackbar.LENGTH_SHORT).show();

                        }
                    });


        } else if (preference == theme) {
            new ColorChooserDialog.Builder((SettingActivity) getActivity(), R.string.theme)
                    .customColors(R.array.colors, null)
                    .doneButton(R.string.done)
                    .cancelButton(R.string.cancel)
                    .allowUserColorInput(false)
                    .allowUserColorInputAlpha(false)
                    .show();
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
