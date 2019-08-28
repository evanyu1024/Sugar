package com.yblxt.sugar

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.yblxt.sugar.databinding.DefaultDataBindingComponent
import com.yblxt.sugar.di.component.AppComponent
import com.yblxt.sugar.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2019-07-17
 */
class SugarApplication : Application(), HasAndroidInjector {

    @set:Inject
    @Volatile
    internal var androidInjector: DispatchingAndroidInjector<Any>? = null

    companion object {
        lateinit var instance: SugarApplication
            private set
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        injectIfNecessary()
        DataBindingUtil.setDefaultComponent(DefaultDataBindingComponent())
    }

    private fun injectIfNecessary() {
        if (androidInjector == null) {
            synchronized(this) {
                if (androidInjector == null) {
                    appComponent = DaggerAppComponent.builder().application(this).build()
                    appComponent.inject(this)
                }
                if (androidInjector == null) {
                    throw IllegalStateException(
                        "The AndroidInjector DaggerAppComponent did not inject the " + "SugarApplication"
                    )
                }
            }
        }
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        // injectIfNecessary should already be called unless we are about to inject a ContentProvider,
        // which can happen before Application.onCreate()
        injectIfNecessary()
        return androidInjector
    }

}