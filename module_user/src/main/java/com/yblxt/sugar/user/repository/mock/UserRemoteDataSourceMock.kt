package com.yblxt.sugar.user.repository.mock

import android.util.Log
import com.yblxt.sugar.user.repository.UserRemoteDataSource
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021/3/3
 */
class UserRemoteDataSourceMock @Inject constructor() : UserRemoteDataSource {

    override fun login(name: String, pwd: String): Boolean {
        Log.d("evanyu", "login by UserRemoteDataSourceMock")
        Thread.sleep(1000)
        return "admin" == name && "admin123" == pwd
    }

}