package com.yblxt.sugar.user.repository

/**
 * @author : evanyu
 * @date 2021/02/26
 */
class UserRepository {

    fun login(name: String, pwd: String): Boolean {
        Thread.sleep(2000)
        return "admin" == name && "admin123" == pwd
    }

}