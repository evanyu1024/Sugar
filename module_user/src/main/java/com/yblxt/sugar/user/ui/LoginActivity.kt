package com.yblxt.sugar.user.ui

import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.yblxt.sugar.common.base.BaseDataBindingActivity
import com.yblxt.sugar.common.router.Router
import com.yblxt.sugar.user.R
import com.yblxt.sugar.user.databinding.ActivityLoginBinding
import com.yblxt.sugar.user.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author evanyu
 * @date 2021/2/26
 */
@AndroidEntryPoint
@Route(path = Router.Path.USER_LOGIN)
class LoginActivity : BaseDataBindingActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getLayoutResId() = R.layout.activity_login

    override fun installVariables() {
        binding.viewModel = viewModel
    }

    override fun init() {
        viewModel.loginResult.observe(this, {
            if (it.isLogin) {
                Router.openLoginFinishPage(it.name, it.pwd)
                finish()
            } else {
                Toast.makeText(this, "登录失败：帐号或密码错误", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etUserName.text.toString(), binding.etUserPwd.text.toString())
        }
    }

}