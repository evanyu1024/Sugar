package com.yblxt.sugar

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * @author evanyu
 * @date 2019-07-17
 */
@HiltAndroidApp
class SugarApplication : Application() {

    companion object {
        lateinit var instance: SugarApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(DebugTree())
    }

}