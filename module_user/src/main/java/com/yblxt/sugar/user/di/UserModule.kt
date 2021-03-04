package com.yblxt.sugar.user.di

import com.google.gson.Gson
import com.yblxt.sugar.user.repository.UserRemoteDataSource
import com.yblxt.sugar.user.repository.network.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author evanyu
 * @date 2021/3/3
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(dataSource: UserRemoteDataSourceImpl): UserRemoteDataSource
//    abstract fun bindUserRemoteDataSource(dataSource: UserRemoteDataSourceMock): UserRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideGson(): Gson = Gson()
    }

}