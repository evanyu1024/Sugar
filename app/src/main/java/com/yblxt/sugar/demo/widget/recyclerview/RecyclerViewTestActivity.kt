package com.yblxt.sugar.demo.widget.recyclerview

import com.yblxt.sugar.entry.DemoItem
import com.yblxt.sugar.ui.activity.BaseDemoListActivity

/**
 * @author evanyu
 * @date 2021-05-13
 */
class RecyclerViewTestActivity : BaseDemoListActivity() {

    override fun getItemList() = listOf(
        DemoItem("RecyclerView basic usage", RecyclerViewBasicUsageActivity::class.java),
        DemoItem("SpanSizeLookup demo", SpanSizeLookupTestActivity::class.java)
    )

}