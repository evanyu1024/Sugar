package com.yblxt.sugar.common.livadata

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData

/**
 * @author evanyu
 * @date 2019-08-05
 */
class ToastLiveData : LiveData<ToastLiveData.Params>() {

    private val params = Params()

    @JvmOverloads
    fun setValue(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        params.clear()
        params.text = text
        params.duration = duration
        value = params
    }

    @JvmOverloads
    fun setValue(@IdRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        params.clear()
        params.textResId = resId
        params.duration = duration
        value = params
    }

    class Params {

        var text: CharSequence? = null
        @StringRes
        var textResId: Int = 0
        var duration: Int = Toast.LENGTH_SHORT

        internal fun clear() {
            text = null
            textResId = 0
            duration = Toast.LENGTH_SHORT
        }
    }
}
