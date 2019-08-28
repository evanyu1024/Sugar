package com.yblxt.sugar.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author evanyu
 * @date 2019-08-27
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}