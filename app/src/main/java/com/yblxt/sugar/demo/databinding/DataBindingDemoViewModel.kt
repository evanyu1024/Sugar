package com.yblxt.sugar.demo.databinding

import com.yblxt.sugar.base.BaseViewModel

/**
 * @author evanyu
 * @date 2019-08-13
 */
class DataBindingDemoViewModel : BaseViewModel() {

    var dataList: List<ItemBean>? = null

    init {
        dataList = ArrayList<ItemBean>().apply {
            for (i in 0..50) {
                add(ItemBean("item $i"))
            }
        }
    }
}