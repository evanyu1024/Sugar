package com.yblxt.sugar.common.base

import androidx.databinding.ViewDataBinding

/**
 * @author evanyu
 * @date 2019-07-18
 */
abstract class BaseSimpleActivity : BaseActivity<ViewDataBinding, BaseViewModel>() {

    override fun isMvvmEnabled() = false

}