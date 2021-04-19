package com.yblxt.sugar.demo.widget.scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yblxt.sugar.databinding.ActivityScrollTestBinding

/**
 * @author evanyu
 * @date 2021-04-08
 */
class ScrollTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
         * scrollTo 和 scrollBy 方法中的参数表示的是屏幕（用户）相对于画布滑动的距离
         * PS：View 在不画布上的为止并没有发生变化，也就是说 View 在画布上的坐标并没有改变。
         */

        binding.btnScrollTo.setOnClickListener {
            // scrollTo：相对于原始位置滑动
            binding.scrollTestView.scrollTo(-60, -60)
        }

        binding.btnScrollBy.setOnClickListener {
            // scrollBy：相对于当前位置滑动
            binding.scrollTestView.scrollBy(-60, -60)
        }

        binding.btnScroller.setOnClickListener {
            binding.scrollTestView.testScroller()
        }
    }

}