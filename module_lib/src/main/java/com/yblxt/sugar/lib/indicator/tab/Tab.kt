package com.yblxt.sugar.lib.indicator.tab

import android.view.View

/**
 * @author evanyu
 * @date 2021-05-04
 */
abstract class Tab {

    var index: Int = -1
    private var state: Int = STATE_NORMAL

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_SELECTED = 1
    }

    abstract fun getView(): View

    abstract fun onSelectChanged(isSelected: Boolean)

    fun isNormal() = state == STATE_NORMAL

    fun isSelected() = (state and STATE_SELECTED) == STATE_SELECTED

}