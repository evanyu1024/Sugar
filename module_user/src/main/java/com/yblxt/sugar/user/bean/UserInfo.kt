package com.yblxt.sugar.user.bean

/**
 * @author evanyu
 * @date 2021-03-09
 */
data class UserInfo(
    var isLogin: Boolean = false,
    var name: String? = null,
    var pwd: String? = null
)