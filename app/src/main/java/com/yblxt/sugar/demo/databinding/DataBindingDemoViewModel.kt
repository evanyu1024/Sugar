package com.yblxt.sugar.demo.databinding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.yblxt.sugar.base.BaseViewModel
import javax.inject.Inject

/**
 * @author evanyu
 * @date 2019-08-13
 */
class DataBindingDemoViewModel @Inject constructor(private val gson: Gson) : BaseViewModel() {

    val imgUrl: MutableLiveData<String> by lazy {
        val liveData = MutableLiveData<String>()
        liveData.value = "https://www.baidu.com/img/bd_logo1.png?where=super"
        liveData
    }
    var dataList: List<ItemBean>? = null

    init {
        // todo just for test dagger, will delete
        Log.d("mtag", "gson = ${gson.hashCode()}")

        dataList = ArrayList<ItemBean>().apply {
            for (i in 0..50) {
                add(ItemBean("item $i"))
            }
        }
    }

}