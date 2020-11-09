package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.demo.widget.progressbar.ProgressBarDemoActivity
import com.yblxt.sugar.entry.DemoItem

/**
 * @author evanyu
 * @date 2019-10-15
 */
class WidgetDemoListActivity : BaseDemoListActivity() {

    override fun getItemList(): List<DemoItem> {
        return listOf(
            DemoItem("ProgressBar", ProgressBarDemoActivity::class.java)
        )
    }
}