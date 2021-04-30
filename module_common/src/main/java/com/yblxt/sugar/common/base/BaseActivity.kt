package com.yblxt.sugar.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yblxt.sugar.common.R

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initToolbar()
        init()
    }

    protected open fun initView() {
        setContentView(getLayoutResId())
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected abstract fun init()

    protected fun initToolbar() {
        (findViewById<Toolbar>(R.id.toolbar))?.let { toolbar ->
            setSupportActionBar(toolbar)
        }
    }

}