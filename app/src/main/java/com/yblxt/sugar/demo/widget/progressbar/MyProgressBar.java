package com.yblxt.sugar.demo.widget.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.yblxt.sugar.R;

/**
 * @author evanyu
 * @date 2019-09-17
 */
public class MyProgressBar extends ProgressBar {

    private OnProgressChangeListener mOnProgressChangeListener;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.ProgressBar);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar, defStyleAttr, defStyleRes);
            // android:enabled
            boolean enabled = ta.getBoolean(R.styleable.MyProgressBar_android_enabled, true);
            setEnabled(enabled);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1f : 0.36f);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        mOnProgressChangeListener = listener;
    }

    @Override
    public synchronized void setProgress(int newProgress) {
        int oldProgress = getProgress();
        super.setProgress(newProgress);
        if (mOnProgressChangeListener != null && newProgress != oldProgress) {
            mOnProgressChangeListener.onProgressChanged(this, newProgress);
        }
    }

    public interface OnProgressChangeListener {
        void onProgressChanged(MyProgressBar progressBar, int progress);
    }

}
