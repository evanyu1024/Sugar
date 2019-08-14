package com.yblxt.sugar.demo.databinding

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yblxt.sugar.BR
import com.yblxt.sugar.R
import com.yblxt.sugar.base.BaseActivity
import com.yblxt.sugar.base.databinding.BindingRecyclerViewAdapter
import com.yblxt.sugar.databinding.DataBindingDemoBinding

/**
 * @author evanyu
 * @date 2019-08-13
 */
class DataBindingDemoActivity : BaseActivity<DataBindingDemoBinding, DataBindingDemoViewModel>() {

    override fun getLayoutResId() = R.layout.activity_databinding_demo

    override fun initView() {
        mBinding.viewModel = mViewModel
        val adapter = BindingRecyclerViewAdapter(R.layout.item_rv_databinding_demo, mViewModel.dataList, BR.itemBean)
        mBinding.rvDatabindingDemo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBinding.rvDatabindingDemo.adapter = adapter
    }

}