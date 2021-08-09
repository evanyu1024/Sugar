package com.yblxt.sugar.common.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.trello.lifecycle2.android.lifecycle.RxLifecycleAndroidLifecycle
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.yblxt.sugar.common.base.interf.IViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author evanyu
 * @date 2019-07-23
 */
abstract class RxViewModel : ViewModel(), IViewModel, LifecycleProvider<Lifecycle.Event> {

    private val lifecycleSubject = BehaviorSubject.create<Lifecycle.Event>()

    override fun lifecycle(): Observable<Lifecycle.Event> {
        return lifecycleSubject.hide()
    }

    override fun <T : Any?> bindUntilEvent(event: Lifecycle.Event): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroidLifecycle.bindLifecycle(lifecycleSubject)
    }

    override fun onLifecycleEvent(owner: LifecycleOwner, event: Lifecycle.Event) {
        lifecycleSubject.onNext(event)
    }

}

