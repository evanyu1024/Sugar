package com.yblxt.sugar.user.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yblxt.sugar.user.repository.UserRepository

/**
 * @author : evanyu
 * @date 2021/02/26
 */
class LoginViewModelFactory(private val userRepo: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(userRepo)
    }
}