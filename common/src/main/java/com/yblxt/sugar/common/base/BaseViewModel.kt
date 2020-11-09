package com.yblxt.sugar.common.base

import com.yblxt.sugar.common.base.interf.IViewModel
import com.yblxt.sugar.common.livadata.ToastLiveData

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseViewModel : RxViewModel(), IViewModel {

    val toast = ToastLiveData()

}