package com.yblxt.sugar.demo.image

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * 高效加载大型位图
 * 官方示例：https://developer.android.google.cn/topic/performance/graphics/load-bitmap
 *
 * @author evanyu
 * @date 2021-05-19
 */
object LargeImageLoader {

    fun load(context: Context, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        return decodeSampledBitmapFromResource(context.resources, resId, reqWidth, reqHeight)
    }

    private fun decodeSampledBitmapFromResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, this)
            // Calculate inSampleSize
            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
            // Decode bitmap with inSampleSize set
            inJustDecodeBounds = false
            BitmapFactory.decodeResource(res, resId, this)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // 根据 inSampleSize 文档，计算 2 的幂的原因是解码器使用的最终值将向下舍入为最接近的 2 的幂
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth } // 解构声明
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            // 180 100
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}