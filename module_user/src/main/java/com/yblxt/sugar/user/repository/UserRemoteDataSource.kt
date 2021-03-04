package com.yblxt.sugar.user.repository

/**
 * @author evanyu
 * @date 2021/3/3
 */
interface UserRemoteDataSource {

    fun login(name: String, pwd: String): Boolean

}