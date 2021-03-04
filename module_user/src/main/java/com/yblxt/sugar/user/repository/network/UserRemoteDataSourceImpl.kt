package com.yblxt.sugar.user.repository.network

import android.util.Log
import com.yblxt.sugar.user.repository.UserRemoteDataSource
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021/3/3
 */
class UserRemoteDataSourceImpl @Inject constructor() : UserRemoteDataSource {

    override fun login(name: String, pwd: String): Boolean {
        Log.d("evanyu", "login by UserRemoteDataSourceImpl")
        Thread.sleep(1000)
        return "admin" == name && "admin123" == pwd
    }

}