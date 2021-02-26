package com.yblxt.sugar.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yblxt.sugar.common.R
import com.yblxt.sugar.common.util.ToastUtils
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: DB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isMvvmEnabled()) {
            initDataBinding()
            initViewModel()
            observeLiveData()
            lifecycle.addObserver(viewModel)
        } else {
            setContentView(getLayoutResId())
        }
        initToolbar()
        init()
    }

    protected open fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
        binding.lifecycleOwner = this
    }

    protected open fun initViewModel() {
        @Suppress("UNCHECKED_CAST")
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        viewModel = ViewModelProviders.of(this).get(vmClass)
    }

    private fun observeLiveData() {
        // observe ToastLiveData
        viewModel.toast.observe(this, Observer { ToastUtils.showToast(this, it) })
    }

    protected open fun isMvvmEnabled() = true

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected fun initToolbar() {
        (findViewById<Toolbar>(R.id.toolbar))?.let { toolbar ->
            setSupportActionBar(toolbar)
        }
    }

    protected abstract fun init()

}