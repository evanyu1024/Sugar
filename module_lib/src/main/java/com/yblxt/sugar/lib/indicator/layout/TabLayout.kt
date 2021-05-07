package com.yblxt.sugar.lib.indicator.layout

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import androidx.core.util.forEach
import com.yblxt.sugar.lib.indicator.Indicator
import com.yblxt.sugar.lib.indicator.TabAdapter
import com.yblxt.sugar.lib.indicator.tab.Tab

/**
 * @author evanyu
 * @date 2021-05-04
 */
class TabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr), Indicator {

    private var adapter: TabAdapter? = null
    private val tabs = SparseArray<Tab>()
    private var curIndex: Int = 0
    private val onTabSelectedListenerList by lazy { ArrayList<Indicator.OnTabSelectedListener>() }

    override fun setAdapter(adapter: TabAdapter) {
        this.adapter = adapter
        removeAllViews()
        tabs.clear()
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        for (index in 0 until adapter.getCount()) {
            val tab = adapter.getTab(index)
            tab.index = index
            tab.getView().setOnClickListener { onTabSelected(tab.index) }
            tabs.put(index, tab)
            addView(tab.getView(), index, lp)
        }
        onTabSelected(curIndex)
    }

    override fun setSelected(index: Int) {
        onTabSelected(index)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 0) {
            val tabWidth = resolveSize(Int.MAX_VALUE, widthMeasureSpec) / (adapter?.getCount() ?: 1)
            val tabHeight = resolveSize(Int.MAX_VALUE, heightMeasureSpec)
            val tabWidthMeasureSpec = MeasureSpec.makeMeasureSpec(tabWidth, MeasureSpec.EXACTLY)
            val tabHeightMeasureSpec = MeasureSpec.makeMeasureSpec(tabHeight, MeasureSpec.EXACTLY)
            for (index in 0 until childCount) {
                getChildAt(index)?.measure(tabWidthMeasureSpec, tabHeightMeasureSpec)
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            val left = index * child.measuredWidth
            val right = left + child.measuredWidth
            child.layout(left, 0, right, child.measuredHeight)
        }
    }

    private fun onTabSelected(selectedIndex: Int) {
        val oldIndex = this.curIndex
        if (selectedIndex < tabs.size()) {
            this.curIndex = selectedIndex
        } else {
            this.curIndex = 0
        }

        tabs.forEach { index, tab ->
            when (index) {
                this.curIndex -> tab.onSelected()
                oldIndex -> tab.onUnselected()
            }
        }

        if (oldIndex != this.curIndex) {
            onTabSelectedListenerList.forEach {
                it.onTabSelected(this.curIndex)
                it.onTabUnselected(oldIndex)
            }
        }
    }

    override fun addOnTabSelectedListener(listener: Indicator.OnTabSelectedListener) {
        onTabSelectedListenerList.add(listener)
    }

}