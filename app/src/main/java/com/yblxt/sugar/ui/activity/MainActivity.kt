package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.demo.service.ServiceDemoActivity
import com.yblxt.sugar.demo.widget.WidgetDemoListActivity
import com.yblxt.sugar.entry.DemoItem
import com.yblxt.sugar.jetpack.JetpackMainActivity
import com.yblxt.sugar.user.ui.UserCenterActivity

/**
 * @author evanyu
 * @date 2019-07-17
 */
class MainActivity : BaseDemoListActivity() {

    override fun getItemList(): List<DemoItem> {
        return listOf(
            DemoItem("SecondActivity", SecondActivity::class.java),
            DemoItem("Widget", WidgetDemoListActivity::class.java),
            DemoItem("Jetpack", JetpackMainActivity::class.java),
            DemoItem("user center", UserCenterActivity::class.java),
            DemoItem("Service", ServiceDemoActivity::class.java)
        )
    }
}
