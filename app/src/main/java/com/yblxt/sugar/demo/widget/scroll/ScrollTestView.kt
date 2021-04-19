package com.yblxt.sugar.demo.widget.scroll

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.OverScroller
import android.widget.Scroller
import androidx.core.view.GestureDetectorCompat
import com.yblxt.sugar.R
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.round

/**
 * @author evanyu
 * @date 2021-04-08
 */
class ScrollTestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), GestureDetector.OnGestureListener {

    companion object {
        private const val SCROLL_OVER_X: Int = 50
        private const val SCROLL_OVER_Y: Int = 50
    }

    private var scrollMinX: Int = 0
    private var scrollMaxX: Int = 0
    private var scrollMinY: Int = 0
    private var scrollMaxY: Int = 0

    private val scroller: Scroller by lazy {
        Scroller(context)
    }
    private val overScroller: OverScroller by lazy {
        OverScroller(context)
    }
    private val gestureDetector: GestureDetectorCompat by lazy {
        GestureDetectorCompat(context, this@ScrollTestView).apply {
            // 禁止长按事件回调
            setIsLongpressEnabled(false)
        }
    }
    private val icon: ImageView by lazy {
        ImageView(context).apply {
            setImageResource(R.mipmap.ic_launcher)
        }
    }

    init {
        addView(icon, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        scrollMinX = -(measuredWidth - icon.measuredWidth)
        scrollMaxX = 0
        scrollMinY = -(measuredHeight - icon.measuredHeight)
        scrollMaxY = 0
    }

    fun testScroller() {
        scroller.startScroll(0, 0, -(width * 0.6).toInt(), -(height * 0.6).toInt(), 2000)
        invalidate()
    }

    override fun computeScroll() {
        Timber.d("computeScroll -> scroller.computeScrollOffset() = ${scroller.computeScrollOffset()}")
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            invalidate()
        }
        Timber.d("computeScroll -> overScroller.computeScrollOffset() = ${overScroller.computeScrollOffset()}")
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        Timber.d("GestureDetector.OnGestureListener -> onDown(${e.x}, ${e.y})")
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        Timber.d("GestureDetector.OnGestureListener -> onShowPress")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Timber.d("GestureDetector.OnGestureListener -> onSingleTapUp")
        return false
    }

    override fun onScroll(downEvent: MotionEvent, currentEvent: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        Timber.d("GestureDetector.OnGestureListener -> onScroll")
        var targetX: Int = round(scrollX + distanceX).toInt()
        if (targetX > scrollMaxX) {
            targetX = scrollMaxX
        } else if (targetX < scrollMinX) {
            targetX = scrollMinX
        }
        var targetY: Int = round(scrollY + distanceY).toInt()
        if (targetY > scrollMaxY) {
            targetY = scrollMaxY;
        } else if (targetY < scrollMinY) {
            targetY = scrollMinY
        }
        scrollTo(targetX, targetY)
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        Timber.d("GestureDetector.OnGestureListener -> onLongPress")
    }

    override fun onFling(downEvent: MotionEvent, currentEvent: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        Timber.d("GestureDetector.OnGestureListener -> onFling, velocityX = $velocityX, velocityY = $velocityY")
        if (abs(velocityX) > ViewConfiguration.get(context).scaledMinimumFlingVelocity
            || abs(velocityY) > ViewConfiguration.get(context).scaledMinimumFlingVelocity
        ) {
            overScroller.fling(
                scrollX, scrollY, -velocityX.toInt(), -velocityY.toInt(),
                scrollMinX, scrollMaxX, scrollMinY, scrollMaxY, SCROLL_OVER_X, SCROLL_OVER_Y
            )
            invalidate()
        }
        return true
    }

}