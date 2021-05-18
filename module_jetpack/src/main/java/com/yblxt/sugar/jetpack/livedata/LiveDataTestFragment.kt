package com.yblxt.sugar.jetpack.livedata

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.yblxt.sugar.jetpack.R
import kotlinx.android.synthetic.main.fragment_livedata_test.*
import timber.log.Timber

/**
 * @author : evanyu
 * @date 2021/02/24
 */
class LiveDataTestFragment : Fragment() {

    // test for map
    val liveData1 = MutableLiveData(0)

    // test for switchMap
    val liveDataSwitchMapCondition = MutableLiveData(0)
    val liveData3 = MutableLiveData("livedata3")
    val liveData4 = MutableLiveData("livedata4")

    // test for MediatorLiveData
    val liveData5 = MutableLiveData(0)
    val liveData6 = MutableLiveData(100)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_livedata_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 事件总线测试
        SimpleLiveEventBus.with<String>("EventBusTest").observe(this, { data: String ->
            Timber.tag("evanyu").d("observe -> $data")
        })
        SimpleLiveEventBus.with<String>("EventBusTest").observeSticky(this, { data: String ->
            Timber.tag("evanyu").d("observeSticky -> $data")
        })

        // 转换被观察的数据类型
        Transformations.map(liveData1) {
            it.toString()
        }.observe(this, Observer {
            tv_test_map.text = "map: $it"
        })

        // 切换被观察的 LiveData 对象
        Transformations.switchMap(liveDataSwitchMapCondition) {
            // 可以根据条件判断返回的结果
            if (it < 5) liveData3 else liveData4
        }.observe(this, Observer {
            tv_test_switch_map.text = "switchMap: $it"
        })

        // MediatorLiveData：合并 LiveData，同时观察多个 LiveData
        val mediatorLiveData = MediatorLiveData<String>()
        mediatorLiveData.addSource(liveData5) {
            mediatorLiveData.value = "liveData5 # onChanged-> $it"
        }
        mediatorLiveData.addSource(liveData6) {
            mediatorLiveData.value = "liveData6 # onChanged-> $it"
        }
        mediatorLiveData.observe(this, Observer {
            Log.d("evanyu", "mediatorLiveData # onChanged -> $it")
        })

        btn_test.setOnClickListener {
            liveData1.value = liveData1.value?.plus(1)
            liveDataSwitchMapCondition.value = liveDataSwitchMapCondition.value?.plus(1)
            liveData5.value = liveData5.value?.plus(1)
            liveData6.value = liveData6.value?.plus(1)
        }
    }

}