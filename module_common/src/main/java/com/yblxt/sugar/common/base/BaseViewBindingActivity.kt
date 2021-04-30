package com.yblxt.sugar.common.base

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2021-04-30
 */
abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseActivity() {

    protected lateinit var binding: VB

    @Suppress("UNCHECKED_CAST")
    override fun initView() {
        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val inflate = vbClass.getMethod("inflate", LayoutInflater::class.java)
        binding = inflate.invoke(null, layoutInflater) as VB
        setContentView(binding.root)
    }

    override fun getLayoutResId() = View.NO_ID

}