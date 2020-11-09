package com.yblxt.sugar.common.base

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yblxt.sugar.common.R
import kotlinx.android.synthetic.main.layout_base_recyclerview.*

/**
 * @author evanyu
 * @date 2019-07-18$
 */
abstract class BaseRecyclerViewActivity<DB : ViewDataBinding, VM : BaseViewModel, E> : BaseActivity<DB, VM>() {

    protected var adapter: BaseRecyclerViewAdapter<E>? = null
        set(value) {
            recyclerview_base.adapter = value
            field = value
        }

    override fun getLayoutResId() = R.layout.layout_base_recyclerview

    override fun init() {
        adapter = createAdapter().apply {
            setOnItemClickListener(this@BaseRecyclerViewActivity::onItemClick)
        }
        initRecyclerView(recyclerview_base)
    }

    protected abstract fun initRecyclerView(recyclerView: RecyclerView)

    protected abstract fun createAdapter(): BaseRecyclerViewAdapter<E>

    protected open fun onItemClick(view: View, position: Int) {
        // empty
    }

}