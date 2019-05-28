package club.hutcwp.lifeutil.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

import com.thefinestartist.finestwebview.FinestWebView

import club.hutcwp.lifeutil.R

/**
 * Created by hutcwp on 2017/4/14.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

object WebUtils {

    /**
     * App 内部打开一个网页
     *
     * @param context
     * @param url
     */
    fun openInternal(context: Context, url: String) {
        FinestWebView.Builder(context)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResRefresh(R.string.menu_action_refresh)
                .stringResShareVia(R.string.menu_action_share)
                .stringResCopyLink(R.string.menu_action_copy)
                .stringResOpenWith(R.string.menu_action_openwith)
                .titleColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .toolbarColor(ContextCompat.getColor(context, R.color.colorAccent))
                .statusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .swipeRefreshColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .showUrl(false)
                .webViewDisplayZoomControls(true)
                .webViewSupportZoom(true)
                .webViewBuiltInZoomControls(true)
                .iconDefaultColor(context.resources.getColor(R.color.white))
                .show(url)
    }

    /**
     * 跳转到外部浏览器打开 url
     *
     * @param context
     * @param url
     */
    fun openExternal(context: Context, url: String) {
        val uri = Uri.parse(url)
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = uri
        context.startActivity(intent)
    }

    /**
     * 加载html 片段
     *
     * @param context
     * @param html
     */
    fun load(context: Context, html: String) {
        FinestWebView.Builder(context)
                .showIconMenu(false)
                .titleColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .toolbarColor(ContextCompat.getColor(context, R.color.colorAccent))
                .statusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .swipeRefreshColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .showUrl(false)
                .iconDefaultColor(context.resources.getColor(R.color.white))
                .load(html, "text/html; charset=UTF-8", null)
    }
}
