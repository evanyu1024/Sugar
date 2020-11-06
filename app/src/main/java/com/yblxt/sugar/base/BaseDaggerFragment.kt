package com.yblxt.sugar.base

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yblxt.sugar.SugarApplication
import com.yblxt.sugar.di.component.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2019/8/28
 */
abstract class BaseDaggerFragment<DB : ViewDataBinding, VM : BaseViewModel>
    : BaseFragment<DB, VM>(), HasAndroidInjector {

    @set:Inject
    var androidInjector: DispatchingAndroidInjector<Any>? = null
    @set:Inject
    var viewModelFactory: ViewModelProvider.Factory? = null

    override fun onAttach(context: Context) {
        if (isDaggerEnabled()) {
            setupDaggerComponent(SugarApplication.appComponent)
        }
        super.onAttach(context)
    }

    protected open fun isDaggerEnabled() = true

    protected open fun setupDaggerComponent(appComponent: AppComponent) {
        AndroidSupportInjection.inject(this)
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