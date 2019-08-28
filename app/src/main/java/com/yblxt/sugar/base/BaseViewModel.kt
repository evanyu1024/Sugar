package com.yblxt.sugar.base

import com.yblxt.sugar.base.interf.IViewModel
import com.yblxt.sugar.livadata.ToastLiveData

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseViewModel : RxViewModel(), IViewModel {

    val toast = ToastLiveData()

}