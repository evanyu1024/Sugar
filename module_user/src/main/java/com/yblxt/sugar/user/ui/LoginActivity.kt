package com.yblxt.sugar.user.ui

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yblxt.sugar.common.base.BaseSimpleActivity
import com.yblxt.sugar.user.R
import com.yblxt.sugar.user.repository.UserRepository
import com.yblxt.sugar.user.viewmodel.LoginViewModel
import com.yblxt.sugar.user.viewmodel.factory.LoginViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author : evanyu
 * @date 2021/02/26
 */
class LoginActivity : BaseSimpleActivity() {

    private val vm: LoginViewModel by lazy {
        ViewModelProviders.of(this, LoginViewModelFactory(UserRepository())).get(LoginViewModel::class.java)
    }

    override fun getLayoutResId() = R.layout.activity_login

    override fun init() {
        vm.loading.observe(this, Observer {
            fl_loading.visibility = if (it) View.VISIBLE else View.GONE
        })
        vm.loginResult.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, LoginFinishActivity::class.java))
            } else {
                Toast.makeText(this, "登录失败：帐号或密码错误", Toast.LENGTH_SHORT).show()
            }
        })
        btn_login.setOnClickListener {
            vm.login(et_user_name.text.toString(), et_user_pwd.text.toString())
        }
        fl_loading.setOnTouchListener { _, _ -> true }
    }

}