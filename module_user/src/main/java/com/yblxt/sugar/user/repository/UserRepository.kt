package com.yblxt.sugar.user.repository

import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021/2/26
 */
class UserRepository @Inject constructor() {

    @Inject
    lateinit var remoteDataSource: UserRemoteDataSource

    @Inject
    lateinit var gson: Gson

    fun login(name: String, pwd: String): Boolean {
        Timber.tag("evanyu").d("remoteDataSource.hashCode() = ${remoteDataSource.hashCode()}");
        Timber.tag("evanyu").d("UserRepository#gson.hashCode() = ${gson.hashCode()}");
        return remoteDataSource.login(name, pwd)
    }

}