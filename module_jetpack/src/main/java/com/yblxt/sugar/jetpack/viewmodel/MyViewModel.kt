package com.yblxt.sugar.jetpack.viewmodel

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.io.File

/**
 * @author : evanyu
 * @date 2021/02/23
 */

private const val KEY_FILE = "filePath"
private fun File.saveFile() = bundleOf(KEY_FILE to absolutePath)
private fun Bundle.restoreFile(): File? = if (containsKey(KEY_FILE)) {
    File(getString(KEY_FILE)!!)
} else {
    null
}

// 从 Fragment 1.2.0 或其传递依赖项 Activity 1.1.0 开始，您可以接受 SavedStateHandle 作为 ViewModel 的构造函数参数。
class MyViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val savedStateLiveData: LiveData<String>
    var file: File? = null

    init {
        // 设置自定义 SavedStateProvider，用于保存一些没有实现序列化接口的数据
        savedStateHandle.setSavedStateProvider("temp_file") {
            // saveSate（）
            Timber.d("MyProvider -> saveState")
            file?.saveFile() ?: Bundle()
        }
        file = savedStateHandle.get<Bundle>("temp_file")?.restoreFile()
        savedStateLiveData = savedStateHandle.getLiveData("data")
    }

    fun setData(str: String) {
        savedStateHandle["data"] = str
    }

    override fun onCleared() {
        /*
         * ViewModel 对象存在的时间范围是获取 ViewModel 时传递给 ViewModelProvider 的 Lifecycle
         * ViewModel 将一直留在内存中，直到限定其存在时间范围的 Lifecycle 永久消失：
         * 对于 Activity，是在 Activity 完成时；而对于 Fragment，是在 Fragment 分离时
         */
        // 清理资源
        Timber.tag("ViewModelTest").d("MyViewModel -> onCleared")
    }

}