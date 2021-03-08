package com.yblxt.sugar.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.yblxt.sugar.user.databinding.ActivityLoginFinishBinding

/**
 * @author evanyu
 * @date 2021/02/26
 */
@Route(path = "/user/loginFinish")
class LoginFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}