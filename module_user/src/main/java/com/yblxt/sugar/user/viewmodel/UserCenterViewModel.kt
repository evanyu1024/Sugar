package com.yblxt.sugar.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yblxt.sugar.user.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021-03-08
 */
@HiltViewModel
class UserCenterViewModel @Inject constructor(private val userRepo: UserRepository) : ViewModel() {

    val isLogin: LiveData<Boolean> = userRepo.isLogin()

    fun logout() {
        userRepo.logout()
    }

}