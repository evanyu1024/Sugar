package com.yblxt.sugar.base.databinding

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.yblxt.sugar.base.BaseRecyclerViewAdapter

/**
 * @author evanyu
 * @date 2019-08-13
 */
class BindingRecyclerViewAdapter<E>(
    @LayoutRes layoutResId: Int,
    data: List<E>? = null,
    private var brId: Int = NO_BR_ID
) : BaseRecyclerViewAdapter<E>(layoutResId, data) {

    companion object {
        @JvmStatic
        val NO_BR_ID = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        val inflater = getLayoutInflater(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutResId, parent, false)
        val holder = BindingHolder(binding)
        initListener(holder)
        return holder
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val itemBean = getItem(position)
        convert(holder, itemBean, position)
        if (brId != NO_BR_ID && holder is BindingHolder) {
            holder.binding.setVariable(brId, itemBean)
            holder.binding.executePendingBindings()
        }
    }

    override fun convert(holder: InnerHolder, itemData: E?, position: Int) {
        // empty
    }

    private class BindingHolder(val binding: ViewDataBinding) : InnerHolder(binding.root)

}