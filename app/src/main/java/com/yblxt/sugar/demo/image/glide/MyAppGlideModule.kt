package com.yblxt.sugar.demo.image.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.InputStream

/**
 * @author evanyu
 * @date 2021-05-19
 */
//@GlideModule(glideName = "ImageLoader") // 自定义glideName
@GlideModule
class MyAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 修改默认配置
//        // 磁盘缓存配置（默认缓存大小250M，默认保存在内部存储中）
//        // 设置磁盘缓存保存在外部存储，且指定缓存大小
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, diskCacheSize);
//        // 设置磁盘缓存保存在自己指定的目录下，且指定缓存大小
//        builder.setDiskCache(new DiskLruCacheFactory(new DiskLruCacheFactory.CacheDirectoryGetter() {
//            @Override
//            public File getCacheDirectory() {
//                return diskCacheFolder;
//            }
//        }, diskCacheSize);
//
//        // 内存缓存配置（不建议配置，Glide会自动根据手机配置进行分配）
//        // 设置内存缓存大小
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
//        // 设置Bitmap池大小
//        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSize));
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // 替换组件，如网络请求组件
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(myOkHttpClient()))
    }

    private fun myOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("glideHttp").d(message) }
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}