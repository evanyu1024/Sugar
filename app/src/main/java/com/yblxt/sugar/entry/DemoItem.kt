package com.yblxt.sugar.entry

/**
 * @author evanyu
 * @date 2019-07-24
 */
data class DemoItem(
    val text: String,
    val activityClass: Class<*>? = null
)