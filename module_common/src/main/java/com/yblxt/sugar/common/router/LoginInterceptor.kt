package com.yblxt.sugar.common.router

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * @author evanyu
 * @date 2021-03-08
 */
@Interceptor(priority = 1)
class LoginInterceptor : IInterceptor {

    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context;
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val flags = postcard.extra
        if (flags and Router.Flag.NEED_LOGIN != 0) {
            val isLogin = false
            if (isLogin) {
                callback.onContinue(postcard)
            } else {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            callback.onContinue(postcard)
        }
    }
}