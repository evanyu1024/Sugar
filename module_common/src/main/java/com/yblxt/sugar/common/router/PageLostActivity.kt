package com.yblxt.sugar.common.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.yblxt.sugar.common.databinding.ActivityPageLostBinding

/**
 * @author evanyu
 * @date 2021-03-12
 */
@Route(path = Router.Path.PAGE_LOST)
class PageLostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPageLostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}