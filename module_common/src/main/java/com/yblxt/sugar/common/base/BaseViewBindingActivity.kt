package com.yblxt.sugar.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * @author evanyu
 * @date 2021-04-30
 */
abstract class BaseViewBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun initViewBinding() {
        val vbClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val inflate = vbClass.getMethod("inflate", LayoutInflater::class.java)
        binding = inflate.invoke(null, layoutInflater) as VB
    }

}