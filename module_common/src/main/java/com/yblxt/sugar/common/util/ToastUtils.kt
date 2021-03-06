package com.yblxt.sugar.common.util

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.yblxt.sugar.common.livadata.ToastLiveData

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
        if (context == null) {
            return;
        }
        var text: CharSequence? = null
        if (!TextUtils.isEmpty(params.text)) {
            text = params.text
        } else {
            try {
                text = context.getString(params.textResId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        if (text != null) {
            Toast.makeText(context, text, params.duration).show()
        }
    }

}