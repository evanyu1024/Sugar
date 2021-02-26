package com.yblxt.sugar.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yblxt.sugar.common.base.BaseViewModel
import com.yblxt.sugar.user.repository.UserRepository
import kotlin.concurrent.thread

/**
 * @author : evanyu
 * @date 2021/02/26
 */
class LoginViewModel(val userRepo: UserRepository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<Boolean>()

    fun login(name: String, pwd: String) {
        // mock login request
        thread {
            loading.postValue(true)
            loginResult.postValue(userRepo.login(name, pwd))
            loading.postValue(false)

        }
    }

}
