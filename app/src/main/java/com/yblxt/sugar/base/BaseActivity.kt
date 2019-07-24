package com.yblxt.sugar.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var mDataBinding: DB
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isMvvmEnabled()) {
            initDataBinding()
            initViewModel()
            lifecycle.addObserver(mViewModel)
        } else {
            setContentView(getLayoutResId())
        }
        initView()
    }

    protected open fun isMvvmEnabled() = true

    protected open fun initDataBinding() {
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun initViewModel() {
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        mViewModel = ViewModelProviders.of(this).get(vmClass)
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun initView()

}