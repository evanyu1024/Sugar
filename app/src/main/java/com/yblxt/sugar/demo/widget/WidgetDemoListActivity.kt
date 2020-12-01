package com.yblxt.sugar.demo.widget

import com.yblxt.sugar.demo.widget.button.ToggleButtonTestActivity
import com.yblxt.sugar.demo.widget.progressbar.ProgressBarDemoActivity
import com.yblxt.sugar.entry.DemoItem
import com.yblxt.sugar.ui.activity.BaseDemoListActivity

/**
 * @author evanyu
 * @date 2019-10-15
 */
class WidgetDemoListActivity : BaseDemoListActivity() {

    override fun init() {
        super.init()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getItemList(): List<DemoItem> {
        return listOf(
            DemoItem("ToggleButton", ToggleButtonTestActivity::class.java),
            DemoItem("ProgressBar", ProgressBarDemoActivity::class.java)
        )
    }
}