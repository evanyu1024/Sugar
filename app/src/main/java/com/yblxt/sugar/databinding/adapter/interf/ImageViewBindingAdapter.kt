package com.yblxt.sugar.databinding.adapter.interf

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * @author : evanyu
 * @date 2019/8/27
 */
interface ImageViewBindingAdapter {

    @BindingAdapter("imgUrl")
    fun setImage(imageView: ImageView, imgUrl: String)

}