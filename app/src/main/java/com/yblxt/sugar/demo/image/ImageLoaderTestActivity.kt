package com.yblxt.sugar.demo.image

import com.yblxt.sugar.R
import com.yblxt.sugar.common.base.BaseViewBindingActivity
import com.yblxt.sugar.databinding.ActivityImageLoaderTestBinding
import com.yblxt.sugar.demo.image.glide.GlideApp

/**
 * @author evanyu
 * @date 2021-05-19
 */
class ImageLoaderTestActivity : BaseViewBindingActivity<ActivityImageLoaderTestBinding>() {

    private val ivShow by lazy { binding.ivShow }

    override fun init() {
        binding.btnLoadLargeImage.setOnClickListener {
            ivShow.setImageBitmap(
                LargeImageLoader.load(this, R.drawable.img_large, ivShow.width, ivShow.height)
            )
        }
        binding.btnLoadImageByGlide.setOnClickListener {
            // requestOptions 可以被复用
//            val requestOptions = RequestOptions().centerCrop().override(100)
//            Glide.with(this)
//                .load("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
//                .apply(requestOptions)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(ivShow)
            GlideApp.with(this)
//                .myGifType()
                .load("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png")
//                .placeholder() // 占位符
//                .error() // 错误符/失败时开始新的请求(降级处理)
//                .fallback() // 后备回调符，在请求的 url/model 为 null 时展示
//                .override() // 指定图片大小
//                .transition() // 过渡选项
//                .thumbnail(Glide.with(this).load(thumbnailUrl).override(thumbnailSize)) // 缩略图请求
                .myOption()
                .myOption2("testParam")
                .into(ivShow)
        }
    }

}
