package com.yblxt.sugar.jetpack.livedata

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

/**
 * @author evanyu
 * @date 2021-05-18
 */
object SimpleLiveEventBus {

    private val eventMap = ConcurrentHashMap<String, Event<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T> with(eventName: String): Event<T> {
        var event = eventMap[eventName]
        if (event == null) {
            event = Event<T>()
            eventMap[eventName] = event
        }
        return event as Event<T>
    }

    class Event<T> : LiveData<T>() {

        var version = -1
        var stickyData: T? = null

        fun post(data: T) {
            Timber.tag("evanyu").d("Event -> post: $data, isMainThread() = ${isMainThread()}")
            version++
            if (isMainThread()) {
                setValue(data)
            } else {
                postValue(data)
            }
        }

        /**
         * 发送粘性事件
         */
        fun postSticky(data: T) {
            // 保存最后发送的粘性事件
            stickyData = data
            post(data)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observe(owner, false, observer)
        }

        fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
            observe(owner, true, observer)
        }

        private fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            super.observe(owner, StickyObserver(this, sticky, observer))
        }

        private fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()
    }

    private class StickyObserver<T>(
        val event: Event<T>,
        val sticky: Boolean,
        val observer: Observer<in T>
    ) : Observer<T> {

        // 将version拉齐，是为了粘性事件的分发处理
        private var lastVersion = event.version

        override fun onChanged(t: T) {
            Timber.tag("evanyu").d("StickyObserver -> onChanged: $t")
            if (lastVersion >= event.version) {
                // 判断是否需要处理粘性事件
                if (sticky && event.stickyData != null) {
                    observer.onChanged(event.stickyData)
                }
            } else {
                lastVersion = event.version
                observer.onChanged(t)
            }
        }
    }

}