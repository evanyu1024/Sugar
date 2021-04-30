package com.yblxt.sugar.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
abstract class BaseDataBindingFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseFragment() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initDataBinding(inflater, container)
        initViewModel()
        observeLiveData()
        lifecycle.addObserver(viewModel)
        return binding.root
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        binding.lifecycleOwner = this
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun initViewModel() {
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        viewModel = ViewModelProvider(this).get(vmClass)
    }

    private fun observeLiveData() {
        // observe ToastLiveData
        viewModel.toast.observe(this, Observer { ToastUtils.showToast(activity, it) })
    }

}