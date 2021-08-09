package com.yblxt.sugar.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author evanyu
 * @date 2021-04-30
 */
abstract class BaseViewBindingFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null

    // This property is only valid between onCreateView and onDestroyView.
    protected val binding get() = _binding!!

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vbClass = findViewBindingClass(javaClass.genericSuperclass!!)
        val inflate = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = inflate.invoke(null, inflater, container, false) as VB
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 官方文档：https://developer.android.com/topic/libraries/view-binding?hl=zh-cn#kotlin
        // 注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
        _binding = null
    }

    private fun findViewBindingClass(type: Type): Class<VB> {
        return if (type is ParameterizedType && type.actualTypeArguments.isNotEmpty()) {
            type.actualTypeArguments[0] as Class<VB>
        } else {
            findViewBindingClass((type as Class<*>).genericSuperclass!!)
        }
    }

}