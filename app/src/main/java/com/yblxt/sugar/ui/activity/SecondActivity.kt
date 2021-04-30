package com.yblxt.sugar.ui.activity

import com.yblxt.sugar.R
import com.yblxt.sugar.common.base.BaseActivity

/**
 * @author evanyu
 * @date 2019-07-24
 */
class SecondActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_second

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
