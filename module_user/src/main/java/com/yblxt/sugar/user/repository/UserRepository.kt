package com.yblxt.sugar.user.repository

import android.util.Log
import com.google.gson.Gson
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021/2/26
 */
class UserRepository @Inject constructor() {

    @Inject
    lateinit var remoteDataSource: UserRemoteDataSource

    @Inject
    lateinit var gson:Gson

    fun login(name: String, pwd: String): Boolean {
        Log.d("evanyu", "remoteDataSource.hashCode() = " + remoteDataSource.hashCode());
        Log.d("evanyu", "UserRepository#gson.hashCode() = " + gson.hashCode());
        return remoteDataSource.login(name, pwd)
    }

}