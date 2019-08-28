package com.yblxt.sugar.databinding.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yblxt.sugar.databinding.adapter.interf.ImageViewBindingAdapter

/**
 * @author : evanyu
 * @date 2019/8/27
 */
class ImageViewBindingAdapterImpl : ImageViewBindingAdapter {

    override fun setImage(imageView: ImageView, imgUrl: String) {
        Glide.with(imageView.context).load(imgUrl).into(imageView)
    }

}