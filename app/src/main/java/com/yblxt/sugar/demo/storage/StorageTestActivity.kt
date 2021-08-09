package com.yblxt.sugar.demo.storage

import android.os.SystemClock
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivityStorageTestBinding
import com.yblxt.sugar.lib.storage.Storage
import com.yblxt.sugar.lib.storage.StorageManager
import timber.log.Timber

/**
 * @author : evanyu
 * @date : 2021/6/23
 */
class StorageTestActivity : BaseViewBindingActivity<ActivityStorageTestBinding>() {

    companion object {
        const val TAG = "StorageTestActivity"
    }

    override fun init() {

        StorageManager.init(this)
        val storage: Storage = StorageManager.disk()
        val testKey = "myTestKey"

        binding.btnTestSave.setOnClickListener {
            val newVal = SystemClock.uptimeMillis()
            storage.save(testKey, newVal)
        }
        binding.btnTestGet.setOnClickListener {
            val value = storage.getLong(testKey)
            log("getValue = $value")
        }
        binding.btnTestGetLivaData.setOnClickListener {
            // register observer1
            storage.withLiveData()
                .getLong(testKey)
                .observe(this) { value -> log("observe1 -> $testKey=$value") }
            // register observer2
            storage.withLiveData()
                .getLong(testKey)
                .observe(this) { value -> log("observe2 -> $testKey=$value") }
        }
        binding.btnUnregisterObserveLiveData.setOnClickListener {
            storage.withLiveData()
                .getLong(testKey)
                .removeObservers(this)
        }
    }

    private fun log(message: String) {
        Timber.tag(TAG).d(message)
    }

}
