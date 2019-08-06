package com.yblxt.sugar.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yblxt.sugar.util.ToastUtils
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var mDataBinding: DB
    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (isMvvmEnabled()) {
            initDataBinding(inflater, container)
            initViewModel()
            observeLiveData()
            lifecycle.addObserver(mViewModel)
            mDataBinding.root
        } else {
            inflater.inflate(getLayoutResId(), container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun initViewModel() {
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        mViewModel = ViewModelProviders.of(this).get(vmClass)
    }

    private fun observeLiveData() {
        // observe ToastLiveData
        mViewModel.mToast.observe(this, Observer { ToastUtils.showToast(activity, it) })
    }

    protected open fun isMvvmEnabled() = true

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun initView()

}