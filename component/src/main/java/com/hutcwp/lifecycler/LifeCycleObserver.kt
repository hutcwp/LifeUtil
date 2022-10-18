package com.hutcwp.lifecycler

/**
 * author : kevin
 * date : 2021/12/19 7:45 PM
 * description :
 */
interface LifeCycleObserver {

    fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event)

}