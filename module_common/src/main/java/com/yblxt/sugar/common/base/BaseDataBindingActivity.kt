package com.yblxt.sugar.common.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.common.util.ToastUtils
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2021-04-30
 */
abstract class BaseDataBindingActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun initView() {
        initDataBinding()
        initViewModel()
        observeLiveData()
        lifecycle.addObserver(viewModel)
    }

    protected open fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.lifecycleOwner = this
    }

    protected open fun initViewModel() {
        @Suppress("UNCHECKED_CAST")
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        viewModel = ViewModelProvider(this).get(vmClass)
    }

    private fun observeLiveData() {
        // observe ToastLiveData
        viewModel.toast.observe(this, Observer { ToastUtils.showToast(this, it) })
    }

}