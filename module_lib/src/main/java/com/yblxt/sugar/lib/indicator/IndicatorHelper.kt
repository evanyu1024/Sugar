package com.yblxt.sugar.lib.indicator

import androidx.viewpager2.widget.ViewPager2

/**
 * @author evanyu
 * @date 2021-05-07
 */
object IndicatorHelper {

    fun bind(indicator: Indicator, viewPager: ViewPager2) {

        indicator.addOnTabSelectedListener(object : Indicator.OnTabSelectedListener {
            override fun onTabSelected(index: Int) {
                viewPager.currentItem = index
            }

            override fun onTabUnselected(index: Int) {}
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicator.setSelected(position)
            }
        })
    }

}