package com.yblxt.sugar.lib.indicator.layout

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import androidx.core.util.forEach
import com.yblxt.sugar.lib.indicator.TabAdapter
import com.yblxt.sugar.lib.indicator.tab.Tab

/**
 * @author evanyu
 * @date 2021-05-04
 */
class TabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    private var adapter: TabAdapter? = null
    private val tabs = SparseArray<Tab>()
    private var curIndex: Int = 0
    private var onTabSelectedListener: OnTabSelectedListener? = null

    fun setAdapter(adapter: TabAdapter) {
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount > 0) {
            val tabWidth = resolveSize(Int.MAX_VALUE, widthMeasureSpec) / 3
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
            tab.onSelectChanged(index == this.curIndex)
        }

        if (oldIndex != this.curIndex) {
            onTabSelectedListener?.onSelectChanged(this.curIndex, oldIndex)
        }
    }

    interface OnTabSelectedListener {
        fun onSelectChanged(curIndex: Int, preIndex: Int)
    }

    fun setOnTabSelectedListener(listener: OnTabSelectedListener) {
        onTabSelectedListener = listener
    }

    fun setOnTabSelectedListener(closure: (Int, Int) -> Unit) {
        onTabSelectedListener = object : OnTabSelectedListener {
            override fun onSelectChanged(curIndex: Int, preIndex: Int) {
                closure.invoke(curIndex, preIndex)
            }
        }
    }

}