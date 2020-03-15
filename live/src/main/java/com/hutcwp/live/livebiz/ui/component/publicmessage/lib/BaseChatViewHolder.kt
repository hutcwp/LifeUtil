package com.hutcwp.live.livebiz.ui.component.publicmessage.lib

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * Created by hutcwp on 2020-03-10 11:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 **/
abstract class BaseChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(data: Any, position: Int)

    private var mViews: SparseArray<View?>? = null


      fun findViewById(@IdRes viewId: Int): View? {
        var view = mViews!![viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            mViews!!.put(viewId, view)
        }
        return view
    }

    protected open fun getView(@IdRes viewId: Int): View? {
        return findViewById(viewId)
    }

    init {
        mViews = SparseArray()
    }
}