package com.yblxt.sugar.ui.activity.widget

import android.view.View
import com.yblxt.sugar.R
import com.yblxt.sugar.base.BaseSimpleActivity
import kotlinx.android.synthetic.main.activity_progressbar.*

/**
 * @author evanyu
 * @date 2019-10-15
 */
class ProgressBarDemoActivity : BaseSimpleActivity() {
    override fun getLayoutResId() = R.layout.activity_progressbar

    override fun initView() {
        progressbar_horizontal.setOnProgressChangeListener { view, progress ->
            updateProgressShow()
        }

        // switch enable/disable
        cbox_progress_enable.setOnCheckedChangeListener { view, isChecked ->
            progressbar_horizontal.isEnabled = isChecked
        }

        // switch indeterminate enabled
        cbox_progress_indeterminate.setOnCheckedChangeListener { view, isChecked ->
            progressbar_horizontal.isIndeterminate = isChecked
        }

        updateProgressShow()
    }

    private fun updateProgressShow() {
        tv_progress.text = progressbar_horizontal.progress.toString()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_progress_plus -> progressbar_horizontal.progress = progressbar_horizontal.progress + 10
            R.id.btn_progress_minus -> progressbar_horizontal.progress = progressbar_horizontal.progress - 10
            R.id.btn_secondary_progress_plus -> progressbar_horizontal.secondaryProgress = progressbar_horizontal.secondaryProgress + 10
            R.id.btn_secondary_progress_minus -> progressbar_horizontal.secondaryProgress = progressbar_horizontal.secondaryProgress - 10
        }
    }
}