package com.yblxt.sugar.lib.indicator.tab

import android.view.View

/**
 * @author evanyu
 * @date 2021-05-04
 */
abstract class Tab {

    var index: Int = -1

    abstract fun getView(): View

    abstract fun onSelected()

    abstract fun onUnselected()

}