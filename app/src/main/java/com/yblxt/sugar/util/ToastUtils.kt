package com.yblxt.sugar.util

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.yblxt.sugar.livadata.ToastLiveData

/**
 * @author evanyu
 * @date 2019-08-05
 */
object ToastUtils {

    /**
     * show toast
     *
     * @param context context
     * @param params  @[ToastLiveData.Params]
     */
    fun showToast(context: Context?, params: ToastLiveData.Params) {
        var context: Context? = context ?: return
        if (context !is Application) {
            context = context!!.applicationContext
        }
        var text: CharSequence? = null
        if (!TextUtils.isEmpty(params.text)) {
            text = params.text
        } else {
            try {
                text = context!!.getString(params.textResId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        if (text != null) {
            Toast.makeText(context, text, params.duration).show()
        }
    }

}