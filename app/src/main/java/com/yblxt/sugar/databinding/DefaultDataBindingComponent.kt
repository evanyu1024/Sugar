package com.yblxt.sugar.databinding

import androidx.databinding.DataBindingComponent
import com.yblxt.sugar.databinding.adapter.ImageViewBindingAdapterImpl
import com.yblxt.sugar.databinding.adapter.interf.ImageViewBindingAdapter

/**
 * @author : evanyu
 * @date 2019/8/27
 */
//@Component(dependencies = [AppComponent::class])
class DefaultDataBindingComponent : DataBindingComponent {

    override fun getImageViewBindingAdapter(): ImageViewBindingAdapter = ImageViewBindingAdapterImpl()

}