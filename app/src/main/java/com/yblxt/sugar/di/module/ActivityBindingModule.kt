package com.yblxt.sugar.di.module

import com.yblxt.sugar.demo.databinding.DataBindingDemoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author evanyu
 * @date 2019/8/27
 */
@Suppress("unused")
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [DataBindingDemoModule::class])
    abstract fun contributeDataBindingDemoActivity(): DataBindingDemoActivity

}