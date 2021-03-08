package com.yblxt.sugar.user.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author evanyu
 * @date 2021/2/26
 */
@Singleton
class UserRepository @Inject constructor() {

    @Inject
    lateinit var remoteDataSource: UserRemoteDataSource

    @Inject
    lateinit var gson: Gson

    private val isLogin = MutableLiveData(false)

    fun isLogin(): LiveData<Boolean> = isLogin

    fun login(name: String, pwd: String): Boolean {
        Timber.tag("evanyu").d("remoteDataSource.hashCode() = ${remoteDataSource.hashCode()}");
        Timber.tag("evanyu").d("UserRepository#gson.hashCode() = ${gson.hashCode()}");
        val loginResult = remoteDataSource.login(name, pwd)
        isLogin.postValue(loginResult)
        return loginResult
    }

    fun logout() {
        isLogin.value = false
    }

}