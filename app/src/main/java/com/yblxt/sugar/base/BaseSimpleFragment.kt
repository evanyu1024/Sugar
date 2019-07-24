package com.yblxt.sugar.base

import androidx.databinding.ViewDataBinding

/**
 * @author evanyu
 * @date 2019-07-24
 */
abstract class BaseSimpleFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {

    override fun isMvvmEnabled() = false

}