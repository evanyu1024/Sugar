package com.yblxt.sugar.lib.indicator

/**
 * @author evanyu
 * @date 2021-05-07
 */
interface Indicator {

    fun setAdapter(adapter: TabAdapter)

    fun setSelected(index: Int)

    interface OnTabSelectedListener {
        fun onTabSelected(index: Int)
        fun onTabUnselected(index: Int)
    }

    fun addOnTabSelectedListener(listener: OnTabSelectedListener)

}