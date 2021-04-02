package com.yblxt.sugar.demo.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yblxt.sugar.R
import timber.log.Timber
import kotlin.concurrent.thread

/**
 * @author evanyu
 * @date 2021-04-02
 */
class MyForegroundService : Service() {

    private var isDestroyed = false;
    private var thread: Thread? = null;

    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate")
        setForeground()
        startTask()
    }

    private fun setForeground() {
        // target version 为 Android 9 (API level 28) 及以上的应用，使用前台任务需要在清单文件中申请相关权限
        // <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("myChannelId", "myChannelName", NotificationManager.IMPORTANCE_DEFAULT)
            NotificationManagerCompat.from(this).createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(this, "myChannelId")
                .setContentTitle("MyService")
                .setContentText("MyService is running in the foreground")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setPriority(NotificationCompat.PRIORITY_LOW) // low or higher
                .build();
            Timber.d("startForeground")
            startForeground(1, notification)
        }
    }

    private fun startTask() {
        thread = thread {
            Timber.d("thread start")
            for (i in 0..20) {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    break;
                }
                if (isDestroyed) {
                    break;
                }
                Timber.d("count -> $i")
            }
            Timber.d("thread end")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Do nothing.
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
        isDestroyed = true;
        thread?.interrupt()
        Timber.d("stopForeground")
        stopForeground(true)
    }

}