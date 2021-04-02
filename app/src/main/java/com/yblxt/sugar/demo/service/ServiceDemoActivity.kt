package com.yblxt.sugar.demo.service

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yblxt.sugar.databinding.ActivityServiceDemoBinding
import timber.log.Timber

/**
 * @author evanyu
 * @date 2021-04-02
 */
class ServiceDemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartService.setOnClickListener {
            Timber.d("startService")
            startService(Intent(this, MyForegroundService::class.java))
        }
        binding.btnStartForegroundService.setOnClickListener {
            // API 26 开始支持前台服务
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 通过 startForegroundService 方法启动 Service 时，Service 必须在5秒内调用 startForeground 方法，否则会出现 ANR
                // PS：前台服务优先级更高
                Timber.d("startForegroundService")
                startForegroundService(Intent(this, MyForegroundService::class.java))
            }
        }
        binding.btnStopService.setOnClickListener {
            stopService(Intent(this, MyForegroundService::class.java))
        }
    }
}
