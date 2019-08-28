package com.yblxt.sugar.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.demo.databinding.DataBindingDemoViewModel
import com.yblxt.sugar.di.ViewModelKey
import com.yblxt.sugar.viewmodel.SugarViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author evanyu
 * @date 2019/8/28
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DataBindingDemoViewModel::class)
    abstract fun bindDataBindingDemoViewModel(viewModel: DataBindingDemoViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: SugarViewModelFactory): ViewModelProvider.Factory

}