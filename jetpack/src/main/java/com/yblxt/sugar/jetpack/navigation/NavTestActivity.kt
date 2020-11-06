package com.yblxt.sugar.jetpack.navigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yblxt.sugar.jetpack.R

/**
 * @author : evanyu
 * @date 2020/11/02
 */
class NavTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_test)
        val value = intent?.extras?.getString("testArg")
        Log.d("evanyu", "testArg = $value")
    }
}