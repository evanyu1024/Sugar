package com.yblxt.sugar.lib

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

/**
 * @author evanyu
 * @date 2021-03-08
 */
class LibraryInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Timber.plant(Timber.DebugTree())
        Timber.d("LibraryInitializer#create")
    }

    override fun dependencies() = emptyList<Class<out Initializer<*>>>()

}