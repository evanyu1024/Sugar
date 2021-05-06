package com.yblxt.sugar.lib.indicator

import com.yblxt.sugar.lib.indicator.tab.Tab

/**
 * @author evanyu
 * @date 2021-05-04
 */
abstract class TabAdapter {

    abstract fun getCount(): Int

    abstract fun getTab(index: Int): Tab

}