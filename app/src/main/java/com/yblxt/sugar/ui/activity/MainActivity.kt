package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.demo.databinding.DataBindingDemoActivity
import com.yblxt.sugar.demo.widget.WidgetDemoListActivity
import com.yblxt.sugar.entry.DemoItem
import com.yblxt.sugar.jetpack.JetpackMainActivity
import com.yblxt.sugar.user.ui.LoginActivity

/**
 * @author evanyu
 * @date 2019-07-17
 */
class MainActivity : BaseDemoListActivity() {

    override fun getItemList(): List<DemoItem> {
        return listOf(
            DemoItem("SecondActivity", SecondActivity::class.java),
            DemoItem("DataBinding", DataBindingDemoActivity::class.java),
            DemoItem("Widget", WidgetDemoListActivity::class.java),
            DemoItem("Jetpack", JetpackMainActivity::class.java),
            DemoItem("login demo", LoginActivity::class.java)
        )
    }
}
