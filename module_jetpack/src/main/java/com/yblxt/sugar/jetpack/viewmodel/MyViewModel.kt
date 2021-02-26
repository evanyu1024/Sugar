package com.yblxt.sugar.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author : evanyu
 * @date 2021/02/23
 */
class MyViewModel : ViewModel() {

    val content: MutableLiveData<String> = MutableLiveData()

    fun setContent(str:String) {
        content.value = str
    }

}