package com.yblxt.sugar.base

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yblxt.sugar.entry.DemoItem

/**
 * @author evanyu
 * @date 2019-07-18$
 */
abstract class BaseDemoListActivity : BaseRecyclerViewActivity<ViewDataBinding, BaseViewModel, DemoItem>() {

    override fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    override fun isMvvmEnabled() = false

    protected abstract fun getItemList(): List<DemoItem>

    override fun createAdapter(): InnerAdapter {
        return InnerAdapter(this, getItemList())
    }

    override fun onItemClick(view: View, position: Int) {
        adapter?.data?.get(position)?.activityClass?.let {
            startActivity(Intent(this, it))
        }
    }

    protected inner class InnerAdapter(context: Context, data: List<DemoItem>) :
        BaseRecyclerViewAdapter<DemoItem>(context, android.R.layout.simple_list_item_1, data) {

        override fun convert(holder: InnerHolder, itemData: DemoItem?, position: Int) {
            holder.getViewById<TextView>(android.R.id.text1).text = itemData?.text
        }
    }
}