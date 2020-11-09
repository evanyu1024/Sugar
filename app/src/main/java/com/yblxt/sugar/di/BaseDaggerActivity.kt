package com.yblxt.sugar.di

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yblxt.sugar.SugarApplication
import com.yblxt.sugar.common.base.BaseActivity
import com.yblxt.sugar.common.base.BaseViewModel
import com.yblxt.sugar.di.component.AppComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2019-08-27
 */
abstract class BaseDaggerActivity<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseActivity<DB, VM>(), HasAndroidInjector {

    @set:Inject
    var androidInjector: DispatchingAndroidInjector<Any>? = null
    @set:Inject
    var viewModelFactory: ViewModelProvider.Factory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isDaggerEnabled()) {
            setupDaggerComponent(SugarApplication.appComponent)
        }
        super.onCreate(savedInstanceState)
    }

    private fun isDaggerEnabled() = true

    protected open fun setupDaggerComponent(appComponent: AppComponent) {
        AndroidInjection.inject(this)
    }

    override fun initViewModel() {
        @Suppress("UNCHECKED_CAST")
        val vmClass: Class<VM> = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return androidInjector
    }

}