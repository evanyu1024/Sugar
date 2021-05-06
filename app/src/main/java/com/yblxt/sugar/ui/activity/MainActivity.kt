package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivityMainBinding
import com.yblxt.sugar.lib.indicator.TabAdapter
import com.yblxt.sugar.lib.indicator.tab.Tab
import com.yblxt.sugar.lib.indicator.tab.TextTab
import timber.log.Timber

/**
 * @author evanyu
 * @date 2019-07-17
 */
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override fun init() {
        binding.mainPageIndicator.setAdapter(object : TabAdapter() {
            override fun getCount() = 3

            override fun getTab(index: Int): Tab {
                val tab = TextTab(this@MainActivity)
                tab.setText("text-$index")
                tab.index = index
                return tab
            }
        })

        binding.mainPageIndicator.setOnTabSelectedListener { curIndex: Int, preIndex: Int ->
            Timber.tag("evanyu").d("curIndex=$curIndex, preIndex=$preIndex")
        }
    }

}
