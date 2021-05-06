package com.yblxt.sugar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.yblxt.sugar.common.base.BaseRecyclerViewAdapter
import com.yblxt.sugar.common.base.BaseViewBindingFragment
import com.yblxt.sugar.common.databinding.LayoutBaseRecyclerviewBinding
import com.yblxt.sugar.entry.DemoItem

/**
 * @author evanyu
 * @date 2021-05-06
 */
abstract class BaseDemoListFragment: BaseViewBindingFragment<LayoutBaseRecyclerviewBinding>() {

    private var adapter: BaseRecyclerViewAdapter<DemoItem>? = null
        set(value) {
            binding.recyclerviewBase.adapter = value
            field = value
        }

    private val recyclerView by lazy { binding.recyclerviewBase }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = createAdapter().apply { setOnItemClickListener(this@BaseDemoListFragment::onItemClick) }
    }

    private fun createAdapter() = InnerAdapter(getItemList())

    protected abstract fun getItemList(): List<DemoItem>

    private fun onItemClick(view: View, position: Int) {
        adapter?.data?.get(position)?.activityClass?.let {
            startActivity(Intent(activity, it))
        }
    }

    inner class InnerAdapter(data: List<DemoItem>) :
        BaseRecyclerViewAdapter<DemoItem>(android.R.layout.simple_list_item_1, data) {

        override fun convert(holder: InnerHolder, itemData: DemoItem?, position: Int) {
            holder.getViewById<TextView>(android.R.id.text1).text = itemData?.text
        }
    }

}