package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.base.BaseDemoListActivity
import com.yblxt.sugar.demo.databinding.DataBindingDemoActivity
import com.yblxt.sugar.entry.DemoItem

/**
 * @author evanyu
 * @date 2019-07-17
 */
class MainActivity : BaseDemoListActivity() {

    override fun getItemList(): List<DemoItem> {
        return listOf(
                DemoItem("SecondActivity", SecondActivity::class.java),
                DemoItem("DataBindingDemo", DataBindingDemoActivity::class.java),
                DemoItem("item2")
        )
    }
}