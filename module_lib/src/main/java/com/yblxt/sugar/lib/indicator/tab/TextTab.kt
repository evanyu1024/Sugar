package com.yblxt.sugar.lib.indicator.tab

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView

/**
 * @author evanyu
 * @date 2021-05-06
 */
class TextTab(context: Context) : Tab() {

    companion object {
        private const val COLOR_NORMAL = Color.BLACK
        private const val COLOR_SELECTED = Color.RED
    }

    private val textView: TextView = TextView(context)

    init {
        textView.gravity = Gravity.CENTER
        textView.textSize = 26F
        textView.setTextColor(COLOR_NORMAL)
    }

    override fun getView() = textView

    override fun onSelected() {
        textView.setTextColor(COLOR_SELECTED)
    }

    override fun onUnselected() {
        textView.setTextColor(COLOR_NORMAL)
    }

    fun setText(text: String) {
        textView.text = text
    }

}