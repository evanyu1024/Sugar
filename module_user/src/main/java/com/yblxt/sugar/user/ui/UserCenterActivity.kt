package com.yblxt.sugar.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yblxt.sugar.user.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author evanyu
 * @date 2021-03-08
 */
@AndroidEntryPoint
class UserCenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_center)
    }

}