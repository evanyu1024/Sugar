package com.yblxt.sugar.demo.widget.button;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.yblxt.sugar.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author evanyu
 * @date 2019/09/20
 */
public class MyToggleButton extends CompoundButton {

    private static final String TAG = "MyToggleButton";

    private static final int TYPE_ROUND = 0;
    private static final int TYPE_RECTANGLE = 1;

    private static final int DRAWABLE_TYPE_NORMAL = 0;
    private static final int DRAWABLE_TYPE_BG_BOTTOM = 1;
    private static final int DRAWABLE_TYPE_ICON = 2;

    @IntDef({DRAWABLE_TYPE_NORMAL, DRAWABLE_TYPE_BG_BOTTOM, DRAWABLE_TYPE_ICON})
    @Retention(RetentionPolicy.SOURCE)
    private @interface DrawableType { }

    private static final int TEXT_DRAWABLE_LEFT_PADDING = 8;

    private final Canvas mDrawableToBitmapCanvas = new Canvas();
    private Drawable mIconDrawable, mBgDrawable;
    private Drawable mCheckedCoverLayerDrawable;
    private int mBgWidth, mBgHeight;
    private float mWidthScale = 1;
    private float mHeightScale = 1;
    private int mToggleType;
    private Drawable mDrawableLeft = null;
    private boolean mIsClickToggle;
    private final DstBitmapHelper mDstBitmapHelper = new DstBitmapHelper();
    private final AnimHelper mAnimHelper = new AnimHelper();

    public MyToggleButton(Context context) {
        this(context, null);
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.MyToggleButton);
    }

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        parseAttrs(context, attrs, defStyleAttr, defStyleRes);
    }

    private void parseAttrs(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = null;
        try {
            ta = context.obtainStyledAttributes(attrs, R.styleable.MyToggleButton, defStyleAttr, defStyleRes);
            mToggleType = ta.getInteger(R.styleable.MyToggleButton_toggleType, TYPE_ROUND);
            mIconDrawable = ta.getDrawable(R.styleable.MyToggleButton_toggleIcon);
            mIsClickToggle = ta.getBoolean(R.styleable.MyToggleButton_toggleClickToggle, true);
            setAnimationDuration(ta.getInteger(R.styleable.MyToggleButton_toggleAnimDuration,
                    AnimHelper.ANIM_DURATION_DEFAULT));
            if (mToggleType == TYPE_ROUND) {
                mBgDrawable = ContextCompat.getDrawable(context, R.drawable.slt_bg_toggle_round);
                mCheckedCoverLayerDrawable
                        = ContextCompat.getDrawable(context, R.drawable.bg_toggle_round_checked_cover_layer);
            } else {
                mBgDrawable = ContextCompat.getDrawable(context, R.drawable.slt_bg_toggle_rectangle);
                mCheckedCoverLayerDrawable
                        = ContextCompat.getDrawable(context, R.drawable.bg_toggle_rectangle_checked_cover_layer);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // measure width
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        mBgWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == View.MeasureSpec.EXACTLY && mToggleType == TYPE_ROUND) {
            mWidthScale = View.MeasureSpec.getSize(widthMeasureSpec) * 1.0f / mBgWidth;
        }

        // measure height
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        mBgHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == View.MeasureSpec.EXACTLY && mToggleType == TYPE_ROUND) {
            mHeightScale = View.MeasureSpec.getSize(widthMeasureSpec) * 1.0f / mBgHeight;
        }

        int maxHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBgHeight, heightMode);
        int maxWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(mBgWidth, widthMode);
        drawableLeftPadding();
        super.onMeasure(maxWidthMeasureSpec, maxHeightMeasureSpec);

        // dstBitmap
        mDstBitmapHelper.onMeasure();

        // animation
        mAnimHelper.onMeasure();
    }

    private void drawableLeftPadding() {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            mDrawableLeft = drawables[0];
        }
        if (mDrawableLeft != null) {
            int textWidth = (int) (getTextSize() * getText().length());
            int intrinsicWidth = mDrawableLeft.getIntrinsicWidth();
            int contentWidth = textWidth + intrinsicWidth + TEXT_DRAWABLE_LEFT_PADDING;
            int paddingWidth = getMeasuredWidth() / 2 - contentWidth / 2;
            setCompoundDrawablesWithIntrinsicBounds(mDrawableLeft, null, null, null);
            setPadding(paddingWidth, 0, 0, 0);
            setCompoundDrawablePadding(-paddingWidth);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap dstBitmap = mDstBitmapHelper.getBitmap();
        if (dstBitmap != null) {
            canvas.drawBitmap(dstBitmap, 0f, 0f, mDstBitmapHelper.mDstBitmapPaint);
        }
        // animation
        mAnimHelper.onDraw(canvas);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        return drawableToBitmap(drawable, DRAWABLE_TYPE_NORMAL);
    }

    private Bitmap drawableToBitmap(Drawable drawable, @DrawableType int type) {
        if (drawable == null) {
            return null;
        }
        int left = 0;
        int top = 0;
        int right = mBgWidth;
        int bottom = mBgHeight;
        int bmWidth = right;
        int bmHeight = bottom;
        switch (type) {
            case DRAWABLE_TYPE_ICON:
                right = (int) (drawable.getIntrinsicWidth() * mWidthScale);
                bottom = (int) (drawable.getIntrinsicHeight() * mHeightScale);
                bmWidth = right;
                bmHeight = bottom;
                break;
            case DRAWABLE_TYPE_NORMAL:
            default:
                break;
        }
        drawable.setBounds(left, top, right, bottom);
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(bmWidth, bmHeight, config);
        mDrawableToBitmapCanvas.setBitmap(bitmap);
        drawable.draw(mDrawableToBitmapCanvas);
        mDrawableToBitmapCanvas.setBitmap(null);
        return bitmap;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mBgDrawable != null && mBgDrawable.isStateful()
                && mBgDrawable.setState(getDrawableState())) {
            invalidateDrawable(mBgDrawable);
        }
        if (mIconDrawable != null && mIconDrawable.isStateful()
                && mIconDrawable.setState(getDrawableState())) {
            invalidateDrawable(mIconDrawable);
        }
        if (mDstBitmapHelper != null) {
            mDstBitmapHelper.onStateChanged();
        }
        invalidate();
    }

    /**
     * Set icon drawable resource.
     *
     * @param resId icon drawable resource id
     */
    public void setIcon(@DrawableRes int resId) {
        this.setIcon(ContextCompat.getDrawable(getContext(), resId));
    }

    /**
     * Set icon drawable.
     *
     * @param drawable icon drawable
     */
    public void setIcon(@NonNull Drawable drawable) {
        mIconDrawable = drawable;
        mDstBitmapHelper.reset();
        invalidate();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(isEnabled() ? 1.0f : 0.36f);
    }

    @Override
    public void toggle() {
        if (mIsClickToggle) {
            super.toggle();
        }
        TextPaint paint = getPaint();
        paint.setFakeBoldText(isChecked());
    }

    public void setAnimationDuration(int duration) {
        mAnimHelper.setDuration(duration);
    }

    public void startAnimation() {
        mAnimHelper.start();
        mDstBitmapHelper.onStateChanged();
    }

    public void startAnimation(int duration) {
        mAnimHelper.start(duration);
        mDstBitmapHelper.onStateChanged();
    }

    public void cancelAnimation() {
        mAnimHelper.cancel();
        mDstBitmapHelper.onStateChanged();
    }

    public void resumeAnimation() {
        mAnimHelper.resume();
        mDstBitmapHelper.onStateChanged();
    }

    public void pauseAnimation() {
        mAnimHelper.pause();
        mDstBitmapHelper.onStateChanged();
    }

    public boolean isAnimationRunning() {
        return mAnimHelper.isRunning();
    }

    private class AnimHelper {

        static final int INDEX_ANIM_PART_1 = 0;
        static final int INDEX_ANIM_PART_2 = 1;
        static final int INDEX_ANIM_PART_3 = 2;
        static final int INDEX_ANIM_PART_4 = 3;
        static final int ANIM_PATH_COUNT = 4;
        static final float ANIM_VALUE_START = 0f;
        static final float ANIM_VALUE_END = ANIM_PATH_COUNT * 2f - 0.001f;
        static final int ANIM_DURATION_MIN = 500; // unit: ms
        static final int ANIM_DURATION_DEFAULT = 3000; // unit: ms
        static final int ANIM_STROKE_WIDTH = 4;
        Paint mAnimPaint;
        Path[] mAnimPaths = new Path[ANIM_PATH_COUNT];
        Path mAnimDstPath = new Path();
        PathMeasure[] mAnimPathMeasures = new PathMeasure[ANIM_PATH_COUNT];
        ValueAnimator mAnimator;
        Interpolator mAnimInterpolator = new LinearInterpolator();
        int mAnimDuration = ANIM_DURATION_DEFAULT;
        float mCurAnimValue;
        float mWidthHalf, mHeightHalf;
        int mAnimLeft, mAnimTop, mAnimRight, mAnimBottom;

        // circle
        static final float CIRCLE_ANIM_OFFSET_BENCHMARK = 90f;
        static final int CIRCLE_ANIM_OFFSET = 14;
        static final int CIRCLE_SWEEP_ANGLE = 82;
        final float[] mCircleAnimStartAngles = {94, 184, 274, 4};
        float mCircleOffsetFactor = 1f;
        RectF mCircleAnimRectF = new RectF();

        // rectangle
        static final int RECT_ANIM_HORIZONTAL_OFFSET = 30;
        static final int RECT_ANIM_VERTICAL_OFFSET = 52;
        static final int ANIM_DASH = 10;
        static final int ANIM_DASH_HALF = ANIM_DASH / 2;
        static final int ANIM_ROUND_RADIUS = 16;
        static final int ANIM_ROUND_DIAMETER = ANIM_ROUND_RADIUS * 2;
        static final int RECT_SWEEP_ANGLE = 90;
        final float[] mRectangleAnimStartAngles = {90, 180, 270, 0};
        RectF[] mRectangleAnimRoundRectFs;

        public AnimHelper() {
            // init paint
            mAnimPaint = new Paint();
            mAnimPaint.setAntiAlias(true);
            mAnimPaint.setStrokeWidth(ANIM_STROKE_WIDTH);
            mAnimPaint.setColor(Color.parseColor("#925BE3"));
            mAnimPaint.setStyle(Paint.Style.STROKE);
            // init paths and pathMeasures
            for (int i = 0; i < ANIM_PATH_COUNT; i++) {
                mAnimPaths[i] = new Path();
                mAnimPathMeasures[i] = new PathMeasure();
            }
        }

        void setDuration(int duration) {
            mAnimDuration = duration >= ANIM_DURATION_MIN ? duration : ANIM_DURATION_DEFAULT;
        }

        void onMeasure() {
            cancel();
            mWidthHalf = getMeasuredWidth() / 2f;
            mHeightHalf = getMeasuredHeight() / 2f;
            if (mToggleType == TYPE_ROUND) {
                int xOffset = (int) ((getMeasuredWidth() / CIRCLE_ANIM_OFFSET_BENCHMARK) * CIRCLE_ANIM_OFFSET);
                int yOffset = (int) ((getMeasuredHeight() / CIRCLE_ANIM_OFFSET_BENCHMARK) * CIRCLE_ANIM_OFFSET);
                mAnimLeft = xOffset;
                mAnimTop = yOffset;
                mAnimRight = getMeasuredWidth() - xOffset;
                mAnimBottom = getMeasuredHeight() - yOffset;
                prepareCircleAnim();
            } else {
                mAnimLeft = RECT_ANIM_HORIZONTAL_OFFSET;
                mAnimTop = RECT_ANIM_VERTICAL_OFFSET;
                mAnimRight = getMeasuredWidth() - RECT_ANIM_HORIZONTAL_OFFSET;
                mAnimBottom = getMeasuredHeight() - RECT_ANIM_VERTICAL_OFFSET;
                prepareRectangleAnim();
            }
        }

        void prepareCircleAnim() {
            if (mCircleAnimRectF == null) {
                mCircleAnimRectF = new RectF();
            }

            Path path;
            PathMeasure pathMeasure;
            mCircleAnimRectF.set(mAnimLeft, mAnimTop, mAnimRight, mAnimBottom);
            for (int i = 0; i < ANIM_PATH_COUNT; i++) {
                path = mAnimPaths[i];
                pathMeasure = mAnimPathMeasures[i];
                path.reset();
                path.arcTo(mCircleAnimRectF, mCircleAnimStartAngles[i], CIRCLE_SWEEP_ANGLE, false);
                pathMeasure.setPath(path, false);
            }
        }

        void prepareRectangleAnim() {
            if (mRectangleAnimRoundRectFs == null) {
                mRectangleAnimRoundRectFs = new RectF[ANIM_PATH_COUNT];
                for (int i = 0; i < ANIM_PATH_COUNT; i++) {
                    mRectangleAnimRoundRectFs[i] = new RectF();
                }
            }

            for (int i = 0; i < ANIM_PATH_COUNT; i++) {
                Path path = mAnimPaths[i];
                PathMeasure pathMeasure = mAnimPathMeasures[i];
                RectF rect = mRectangleAnimRoundRectFs[i];
                float startAngle = mRectangleAnimStartAngles[i];
                path.reset();
                switch (i) {
                    case INDEX_ANIM_PART_1:
                        path.moveTo(mWidthHalf - ANIM_DASH_HALF, mAnimBottom);
                        path.lineTo(mAnimLeft + ANIM_ROUND_RADIUS, mAnimBottom);
                        rect.set(mAnimLeft, mAnimBottom - ANIM_ROUND_DIAMETER,
                                mAnimLeft + ANIM_ROUND_DIAMETER, mAnimBottom);
                        path.arcTo(rect, startAngle, RECT_SWEEP_ANGLE, false);
                        path.lineTo(mAnimLeft, mHeightHalf + ANIM_DASH_HALF);
                        break;
                    case INDEX_ANIM_PART_2:
                        path.moveTo(mAnimLeft, getMeasuredHeight() / 2f - ANIM_DASH_HALF);
                        path.lineTo(mAnimLeft, mAnimTop + ANIM_ROUND_RADIUS);
                        rect.set(mAnimLeft, mAnimTop,
                                mAnimLeft + ANIM_ROUND_DIAMETER, mAnimTop + ANIM_ROUND_DIAMETER);
                        path.arcTo(rect, startAngle, RECT_SWEEP_ANGLE, false);
                        path.lineTo(mWidthHalf - ANIM_DASH_HALF, mAnimTop);
                        break;
                    case INDEX_ANIM_PART_3:
                        path.moveTo(mWidthHalf + ANIM_DASH_HALF, mAnimTop);
                        path.lineTo(mAnimRight - ANIM_ROUND_RADIUS, mAnimTop);
                        rect.set(mAnimRight - ANIM_ROUND_DIAMETER, mAnimTop,
                                mAnimRight, mAnimTop + ANIM_ROUND_DIAMETER);
                        path.arcTo(rect, startAngle, RECT_SWEEP_ANGLE, false);
                        path.lineTo(mAnimRight, mHeightHalf - ANIM_DASH_HALF);
                        break;
                    case INDEX_ANIM_PART_4:
                        path.moveTo(mAnimRight, mHeightHalf + ANIM_DASH_HALF);
                        path.lineTo(mAnimRight, mAnimBottom - ANIM_ROUND_RADIUS);
                        rect.set(mAnimRight - ANIM_ROUND_DIAMETER, mAnimBottom - ANIM_ROUND_DIAMETER,
                                mAnimRight, mAnimBottom);
                        path.arcTo(rect, startAngle, RECT_SWEEP_ANGLE, false);
                        path.lineTo(mWidthHalf + ANIM_DASH_HALF, mAnimBottom);
                        break;
                    default:
                        break;
                }
                pathMeasure.setPath(path, false);
            }
        }

        void onDraw(Canvas canvas) {
            if (!isRunning()) {
                return;
            }
            if (mCurAnimValue < ANIM_PATH_COUNT) {
                drawRound1();
            } else {
                drawRound2();
            }
            canvas.drawPath(mAnimDstPath, mAnimPaint);
        }

        void drawRound1() {
            PathMeasure lastPart, curPart;
            for (int i = 1; i <= ANIM_PATH_COUNT; i++) {
                if (mCurAnimValue < i) {
                    if (i == 1) {
                        mAnimDstPath.reset();
                        lastPart = null;
                    } else {
                        lastPart = mAnimPathMeasures[i - 2];
                    }
                    curPart = mAnimPathMeasures[i - 1];
                } else {
                    continue;
                }
                if (lastPart != null) {
                    lastPart.getSegment(0, lastPart.getLength(), mAnimDstPath, true);
                }
                float startD = 0;
                float stopD = curPart.getLength() * (mCurAnimValue - i + 1);
                curPart.getSegment(startD, stopD, mAnimDstPath, true);
                return;
            }
        }

        void drawRound2() {
            mAnimDstPath.reset();
            int curPathIndex = 0;
            for (int i = ANIM_PATH_COUNT + 1; i <= ANIM_PATH_COUNT * 2; i++) {
                if (mCurAnimValue < i) {
                    curPathIndex = i - ANIM_PATH_COUNT - 1;
                    PathMeasure pathMeasure = mAnimPathMeasures[curPathIndex];
                    float startD = pathMeasure.getLength() * (mCurAnimValue - i + 1);
                    float stopD = pathMeasure.getLength();
                    pathMeasure.getSegment(startD, stopD, mAnimDstPath, true);
                    break;
                }
            }
            for (int i = curPathIndex + 1; i < ANIM_PATH_COUNT; i++) {
                mAnimPathMeasures[i].getSegment(0, mAnimPathMeasures[i].getLength(), mAnimDstPath, true);
            }
        }

        void start() {
            start(mAnimDuration);
        }

        void start(int duration) {
            if (duration < ANIM_DURATION_MIN) {
                duration = mAnimDuration;
            }
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            if (mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
            }
            mAnimator = ValueAnimator.ofFloat(ANIM_VALUE_START, ANIM_VALUE_END);
            mAnimator.setInterpolator(mAnimInterpolator);
            mAnimator.addUpdateListener(animation -> {
                mCurAnimValue = (Float) animation.getAnimatedValue();
                invalidate();
            });
            mAnimator.setDuration(duration);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.start();
        }

        void cancel() {
            if (mAnimator != null) {
                mAnimator.cancel();
                mAnimator = null;
                mCurAnimValue = 0;
                invalidate();
            }
        }

        void resume() {
            if (mAnimator != null) {
                mAnimator.resume();
            }
        }

        void pause() {
            if (mAnimator != null) {
                mAnimator.pause();
            }
        }

        boolean isRunning() {
            if (mAnimator != null) {
                return mAnimator.isRunning();
            }
            return false;
        }
    }

    class DstBitmapHelper {

        final Xfermode mDstBitmapXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        final Canvas mDstBitmapCanvas = new Canvas();
        final HashMap<String, Bitmap> mDstBitmapCache = new HashMap<>();
        final Paint mDstBitmapPaint = new Paint();
        Bitmap mDstBitmap;
        int mMeasureWidthCache, mMeasureHeightCache;

        String makeKey(int[] state) {
            return Arrays.toString(state) + isAnimationRunning();
        }

        void onStateChanged() {
            int[] state = getDrawableState();
            if (state == null) {
                mDstBitmap = null;
                return;
            }
            String key = makeKey(state);
            mDstBitmap = mDstBitmapCache.get(key);
            if (mDstBitmap == null && getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
                mDstBitmap = createDstBitmap();
                if (mDstBitmap != null) {
                    mDstBitmapCache.put(key, mDstBitmap);
                }
            }
        }

        Bitmap createDstBitmap() {
            Bitmap dstBitmap = null;
            mDstBitmapCanvas.setBitmap(null);
            mDstBitmapPaint.setXfermode(mDstBitmapXfermode);
            // background
            Bitmap bgBitmap = drawableToBitmap(mBgDrawable);
            if (bgBitmap != null) {
                dstBitmap = bgBitmap;
                mDstBitmapCanvas.setBitmap(dstBitmap);
            }
            // checked cover layer
            Bitmap checkedCoverLayerBitmap = drawableToBitmap(mCheckedCoverLayerDrawable);
            if (checkedCoverLayerBitmap != null && isChecked() && !isAnimationRunning()) {
                if (dstBitmap == null) {
                    dstBitmap = checkedCoverLayerBitmap;
                    mDstBitmapCanvas.setBitmap(dstBitmap);
                } else {
                    mDstBitmapCanvas.drawBitmap(checkedCoverLayerBitmap, 0, 0, mDstBitmapPaint);
                }
            }
            // icon
            Bitmap iconBitmap = drawableToBitmap(mIconDrawable, DRAWABLE_TYPE_ICON);
            if (iconBitmap != null) {
                if (dstBitmap == null) {
                    dstBitmap = iconBitmap;
                } else {
                    float x = (getMeasuredWidth() - iconBitmap.getWidth()) / 2.0F;
                    float y = (getMeasuredHeight() - iconBitmap.getHeight()) / 2.0F;
                    mDstBitmapCanvas.drawBitmap(iconBitmap, x, y, mDstBitmapPaint);
                }
            }
            mDstBitmapPaint.setXfermode(null);
            mDstBitmapCanvas.setBitmap(null);
            return dstBitmap;
        }

        Bitmap getBitmap() {
            return mDstBitmap;
        }

        void onMeasure() {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0 && height > 0 && (width != mMeasureWidthCache || height != mMeasureHeightCache)) {
                reset();
                mMeasureWidthCache = width;
                mMeasureHeightCache = height;
            }
        }

        void reset() {
            for (Bitmap bitmap : mDstBitmapCache.values()) {
                if (bitmap != null) {
                    bitmap.recycle();
                }
            }
            mDstBitmapCache.clear();
            onStateChanged();
        }
    }

}
