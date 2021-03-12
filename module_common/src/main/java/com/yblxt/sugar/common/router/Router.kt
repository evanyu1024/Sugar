package com.yblxt.sugar.common.router

/**
 * @author evanyu
 * @date 2021-03-12
 */
object Router {

    object Flag {
        const val NEED_LOGIN = 0x1;
    }

    object Path {
        const val USER_CENTER = "/user/center"
        const val USER_LOGIN = "/user/login"
        const val USER_LOGOUT = "/user/logout"
        const val USER_MEMBERSHIP_CENTER = "/user/membership"
    }

}