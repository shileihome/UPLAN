package com.uplan.miyao.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.uplan.miyao.R;
import com.uplan.miyao.base.helper.QMUIDisplayHelper;

/**
 * 进度提示
 */
public class DownLoadLineProgress extends View {


    private float lineWidth = 2;
    private int width;
    private int height;
    private int mProgress;
    private int mBackColor;
    private int mProgressColor;

    /** 进度条paint */
    private Paint mProgressPaint;

    /** 北京paint */
    private Paint mBackgrounPaint;

    /** 线冒的长度 */
    private int mCapWidth;

    public DownLoadLineProgress(Context context) {
        this(context, null);
    }

    public DownLoadLineProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownLoadLineProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void initView() {
        lineWidth = QMUIDisplayHelper.dp2px(getContext(), 1);
        mBackColor = getResources().getColor(R.color.color_dddddd);
        mProgressColor = getResources().getColor(R.color.colorAccent);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(QMUIDisplayHelper.dp2px(getContext(), 12));

        mBackgrounPaint = new Paint();
        mBackgrounPaint.setColor(mBackColor);
        mBackgrounPaint.setStyle(Paint.Style.STROKE);
        mBackgrounPaint.setAntiAlias(true);
        mBackgrounPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackgrounPaint.setStrokeWidth(QMUIDisplayHelper.dp2px(getContext(), 12));
        mCapWidth = QMUIDisplayHelper.dp2px(getContext(), 8);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mCapWidth, height / 2, width - mCapWidth, height / 2, mBackgrounPaint);
        canvas.drawLine(mCapWidth, height / 2, mCapWidth + (mProgress / 100f) * (width - (2 * mCapWidth)), height / 2, mProgressPaint);
    }


    public void startAnim() {

        ValueAnimator valueAnim = ValueAnimator.ofInt(0, 90);
        valueAnim.setRepeatCount(ValueAnimator.RESTART);
        valueAnim.setDuration(2000);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mProgress = (int) animation.getAnimatedValue();
                invalidate();

            }
        });
        valueAnim.start();
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }
}
