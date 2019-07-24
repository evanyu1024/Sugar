package com.yblxt.sugar.base.interf

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author evanyu
 * @date 2019-07-23
 */
interface IViewModel : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleEvent(owner: LifecycleOwner, event: Lifecycle.Event)

}