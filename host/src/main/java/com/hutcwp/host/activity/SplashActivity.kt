package com.hutcwp.host.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.hutcwp.host.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

/**
 *  闪屏页
 */
class SplashActivity : AppCompatActivity() {

    private var mCountDownDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        tvCountDown?.setOnClickListener {
            toHomePage()
        }
        startCountDownToHomePage()
    }

    private fun startCountDownToHomePage() {
        mCountDownDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)//延迟0，间隔1s，单位秒
                .take(COUNT_DOWN_SECOND + 1)//限制发射次数（因为倒计时要显示 3 2 1 0 四个数字）
                .map {
                    COUNT_DOWN_SECOND - it
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    tvCountDown?.text = "点击跳过(${it}s)"
                    if (it == 0L) {
                        toHomePage()
                    }
                })
    }

    private fun toHomePage() {
//        ARouter.getInstance().build("/homepage/home").navigation()
        ARouter.getInstance().build("/homepage/assist").navigation()
        finish()
    }

    override fun onDestroy() {
        mCountDownDisposable?.dispose()
        super.onDestroy()
    }

    companion object {
        const val COUNT_DOWN_SECOND = 3L
        const val TAG = "MainActivity"
    }
}


