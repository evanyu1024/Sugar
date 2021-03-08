package com.yblxt.sugar

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.yblxt.sugar.lib.BuildConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * @author evanyu
 * @date 2019-07-17
 */
@HiltAndroidApp
class SugarApplication : Application() {

    companion object {
        lateinit var instance: SugarApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // ARouter
        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

}