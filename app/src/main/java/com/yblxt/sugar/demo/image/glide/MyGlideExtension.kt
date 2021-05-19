package com.yblxt.sugar.demo.image.glide

import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.annotation.GlideType
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.decodeTypeOf

/**
 * @author evanyu
 * @date 2021-05-19
 */
@GlideExtension
class MyGlideExtension private constructor() {

    companion object {

        private val DECODE_TYPE_GIF: RequestOptions = decodeTypeOf(GifDrawable::class.java).lock()

        // 被 @GlideType 注解的静态方法用于扩展 RequestManager
        @JvmStatic
        @GlideType(GifDrawable::class)
        fun myGifType(requestBuilder: RequestBuilder<GifDrawable>): RequestBuilder<GifDrawable> {
            return requestBuilder
                .transition(DrawableTransitionOptions())
                .apply(DECODE_TYPE_GIF)
        }

        // 用 @GlideOption 注解的静态方法用于扩展 RequestOptions
        @JvmStatic
        @GlideOption
        fun myOption(options: BaseRequestOptions<*>): BaseRequestOptions<*> {
            // options...
            return options
        }

        // 可以为方法任意添加参数，但要保证第一个参数为 RequestOptions
        @JvmStatic
        @GlideOption
        fun myOption2(options: BaseRequestOptions<*>, testParam: String): BaseRequestOptions<*> {
            // options...
            return options
        }

    }

}