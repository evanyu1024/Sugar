package com.yblxt.sugar.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * @author evanyu
 * @date 2019-07-17
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

}