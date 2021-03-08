package com.yblxt.sugar.common

import android.content.Context
import androidx.startup.Initializer
import com.yblxt.sugar.lib.LibraryInitializer
import timber.log.Timber

/**
 * @author evanyu
 * @date 2021-03-08
 */
class CommonInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Timber.d("CommonInitializer#create")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(LibraryInitializer::class.java)
    }
}