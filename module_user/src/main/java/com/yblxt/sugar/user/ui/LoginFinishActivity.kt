package com.yblxt.sugar.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yblxt.sugar.user.databinding.ActivityLoginFinishBinding

/**
 * @author evanyu
 * @date 2021/02/26
 */
class LoginFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}