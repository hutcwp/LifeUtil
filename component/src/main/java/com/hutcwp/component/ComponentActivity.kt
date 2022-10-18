package com.hutcwp.component

import android.app.Activity

/**
 *  author : kevin
 *  date : 2021/12/12 1:33 AM
 *  description :
 */
open class ComponentActivity : Activity() {

    protected val componentManager: ComponentManager by lazy { ComponentManager(this) }


}