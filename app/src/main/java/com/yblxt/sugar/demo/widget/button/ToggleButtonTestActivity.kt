package com.yblxt.sugar.demo.widget.button

import com.yblxt.sugar.R
import com.yblxt.sugar.common.base.BaseSimpleActivity
import kotlinx.android.synthetic.main.activity_toggle_button_test.*

/**
 * @author evanyu
 * @date 2020-11-17
 */
class ToggleButtonTestActivity : BaseSimpleActivity() {

    override fun getLayoutResId() = R.layout.activity_toggle_button_test

    override fun init() {
        btn_start.setOnClickListener { toggle_rectangle.startAnimation() }
        btn_cancel.setOnClickListener { toggle_rectangle.cancelAnimation() }
        btn_pause.setOnClickListener { toggle_rectangle.pauseAnimation() }
        btn_resume.setOnClickListener { toggle_rectangle.resumeAnimation() }
    }

}