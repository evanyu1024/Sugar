package com.yblxt.sugar.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivityMainBinding
import com.yblxt.sugar.lib.indicator.Indicator
import com.yblxt.sugar.lib.indicator.IndicatorHelper
import com.yblxt.sugar.lib.indicator.TabAdapter
import com.yblxt.sugar.lib.indicator.tab.Tab
import com.yblxt.sugar.lib.indicator.tab.TextTab
import com.yblxt.sugar.ui.fragment.MainTabOneFragment
import com.yblxt.sugar.ui.fragment.MainTabTwoFragment

/**
 * @author evanyu
 * @date 2019-07-17
 */
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val fragments: List<Fragment> by lazy { listOf(MainTabOneFragment(), MainTabTwoFragment()) }
    private val viewPager: ViewPager2 by lazy { binding.mainViewpager }
    private val indicator: Indicator by lazy { binding.mainPageIndicator }

    override fun init() {
        binding.mainViewpager.adapter = FragmentAdapter()

        indicator.setAdapter(object : TabAdapter() {
            override fun getCount() = 2

            override fun getTab(index: Int): Tab {
                val tab = TextTab(this@MainActivity)
                tab.setText("tab-${index + 1}")
                tab.index = index
                return tab
            }
        })

        IndicatorHelper.bind(indicator, viewPager)
    }

    inner class FragmentAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

}
