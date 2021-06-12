package com.hutcwp.read.ui.view

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import me.hutcwp.log.MLog


/**
 *
 * @ProjectName:    LifeUtil$
 * @Package:        com.hutcwp.lifeutil.ui$
 * @ClassName:      BottoNavigationView$
 * @Description:
 * @Author:         caiwenpeng
 * @CreateDate:     2020/8/15$ 3:07 PM$
 */
class BottomNavigationView : LinearLayout, View.OnClickListener {

    var onItemClickListener: OnItemClickListener? = null
    val itemViewList: MutableList<ItemView> = mutableListOf()
    var itemBeanList = mutableListOf<ItemBean>()
        set(value) {
            field = value
            updateView(itemBeanList)
        }

    fun setSelect(name: String) {
        itemViewList?.forEach {
            if (it.itemBean.name == name) {
                setSelect(it, true)
            } else {
                setSelect(it, false)
            }
        }
    }


    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        orientation = HORIZONTAL
    }

    private fun updateView(itemBeanList: MutableList<ItemBean>) {
        MLog.info(TAG, "updateView: itemBeanList=$itemBeanList")
        removeAllViews()
        itemBeanList.forEach {
            val imageView = ItemView(context, it)
//            val height = ResolutionUtils.convertDpToPixel(60f, context).toInt()
            val param = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

            imageView.setImageResource(it.drawableId)
            imageView.layoutParams = param
            imageView.setOnClickListener(this)
            addView(imageView)

        }
    }

    override fun addView(child: View?) {
        super.addView(child)
        if (child is ItemView) {
            itemViewList.add(child)
        }
    }

    override fun removeView(view: View?) {
        super.removeView(view)
        if (view is ItemView && itemViewList.contains(view)) {
            itemViewList.remove(view)
        }
    }

    private fun setSelect(view: ItemView, selected: Boolean) {
        val grayColorFilter = if (!selected) {
            val cm = ColorMatrix()
            cm.setSaturation(0f)
            ColorMatrixColorFilter(cm)
        } else {
            null
        }
        view.colorFilter = grayColorFilter
    }

    data class ItemBean(var drawableId: Int, val name: String)

    interface OnItemClickListener {
        fun onClick(v: ItemView)
    }

    override fun onClick(v: View) {
        if (v is ItemView) {
            onItemClickListener?.onClick(v)
        }
    }

    companion object {
        const val TAG = "BottomNavigationView"
    }

    class ItemView(context: Context?, val itemBean: ItemBean) : ImageView(context) {

    }
}