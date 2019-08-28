package com.yblxt.sugar.di.component

import android.app.Application
import com.yblxt.sugar.SugarApplication
import com.yblxt.sugar.di.module.ActivityBindingModule
import com.yblxt.sugar.di.module.AppModule
import com.yblxt.sugar.di.module.FragmentBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author evanyu
 * @date 2019/8/27
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<SugarApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}