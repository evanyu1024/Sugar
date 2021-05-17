package com.yblxt.sugar.demo.widget.recyclerview

import android.graphics.Color
import android.util.SparseIntArray
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivitySpanSizeLookupTestBinding
import com.yblxt.sugar.lib.util.dp2px

/**
 * @author evanyu
 * @date 2021-05-13
 */
class SpanSizeLookupTestActivity : BaseViewBindingActivity<ActivitySpanSizeLookupTestBinding>() {

    companion object {
        const val SPAN_COUNT = 4
    }

    private val recyclerView by lazy { binding.recyclerView }
    private val adapter by lazy { GridItemAdapter() }
    private val dataList: MutableList<ItemBean> by lazy {
        val list = ArrayList<ItemBean>()
        var group = 1
        var color = Color.RED
        for (i in 0..40) {
            if (i == 5) {
                group = 2
                color = Color.GREEN
            }
            if (i == 20) {
                group = 3
                color = Color.BLUE
            }
            if (i == 30) {
                group = 4
                color = Color.GRAY
            }
            list.add(ItemBean(group, "item-$i", color))
        }
        list
    }

    override fun init() {
        val layoutManager = GridLayoutManager(this, SPAN_COUNT)
        layoutManager.spanSizeLookup = GroupSpanSizeLookup()
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    inner class ItemBean(val group: Int, val desc: String, val color: Int)

    inner class GridItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount() = dataList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val tv = TextView(this@SpanSizeLookupTestActivity)
            tv.layoutParams = ViewGroup.LayoutParams(recyclerView.measuredWidth / SPAN_COUNT, dp2px(100))
            tv.textSize = 26f
            tv.gravity = Gravity.CENTER
            return object : RecyclerView.ViewHolder(tv) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder.itemView as TextView).apply {
                val itemData = dataList[position]
                text = itemData.desc
                setBackgroundColor(itemData.color)
            }
        }

    }

    inner class GroupSpanSizeLookup : GridLayoutManager.SpanSizeLookup() {

        // key: 每组最后一项的position
        // value: 本组和之前组的所有spanSize的总和（offset）
        private val groupSpanSizeOffset = SparseIntArray()

        override fun getSpanSize(position: Int): Int {
            var spanSize: Int
            val nextPosition = position + 1
            // 判断当前项和后一项是否在同一组
            if (nextPosition < dataList.size && dataList[position].group == dataList[nextPosition].group) {
                spanSize = 1
            } else {
                val indexOfKey = groupSpanSizeOffset.indexOfKey(position)
                val lastGroupOffset =
                    if (groupSpanSizeOffset.size() <= 0) {
                        0
                    } else if (indexOfKey >= 0) {
                        // position存在于缓存中，说明已经被记录过，这个情况发生在View缓存复用的时候
                        if (indexOfKey == 0) 0 else groupSpanSizeOffset.valueAt(indexOfKey - 1)
                    } else {
                        // 获取缓存中记录的最后一个偏移量
                        groupSpanSizeOffset.valueAt(groupSpanSizeOffset.size() - 1)
                    }
                // 计算当前组的spanSize
                spanSize = SPAN_COUNT - (position + lastGroupOffset) % SPAN_COUNT
                // 缓存偏移量（spanSize的总和）
                if (indexOfKey < 0) {
                    groupSpanSizeOffset.put(position, lastGroupOffset + spanSize - 1)
                }
            }
            return spanSize
        }
    }

}

