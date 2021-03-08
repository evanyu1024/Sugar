package com.yblxt.sugar.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yblxt.sugar.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * @author evanyu
 * @date 2021/02/26
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

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
