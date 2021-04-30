package com.yblxt.sugar.jetpack.navigation

import android.util.Log
import com.yblxt.sugar.common.base.BaseActivity
import com.yblxt.sugar.jetpack.R

/**
 * @author : evanyu
 * @date 2020/11/02
 */
class NavTestActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_nav_test

    override fun init() {
        intent?.extras?.let {
//            val testArgVal = intent?.extras?.getString("testArgVal")
            val testArgVal = NavTestActivityArgs.fromBundle(it).testArg
            Log.d("evanyu", "testArgVal = $testArgVal")
        }
    }
}