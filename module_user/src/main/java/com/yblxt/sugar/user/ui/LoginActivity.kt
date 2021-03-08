package com.yblxt.sugar.user.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.yblxt.sugar.user.databinding.ActivityLoginBinding
import com.yblxt.sugar.user.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2021/2/26
 */
@AndroidEntryPoint
@Route(path = "/user/login")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.tag("evanyu").d("LoginActivity#gson.hashCode(): ${gson.hashCode()}")

        viewModel.loading.observe(this, Observer {
            binding.flLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.loginResult.observe(this, Observer {
            if (it) {
                ARouter.getInstance().build("/user/loginFinish").navigation()
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