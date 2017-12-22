package com.just.fast.module.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.just.utils.log.LogUtils;

/**
 * 线性渐变平移动画布局
 *
 * @author JustDo23
 * @since 2017年21月21日
 */
public class LinearGradientLayout extends View implements ValueAnimator.AnimatorUpdateListener {

  private Paint mPaint;// 渐变
  private LinearGradient mLinearGradient;// 水平渐变
  private int colors[];// 渐变颜色数组
  private float positions[];// 每种颜色占用位置
  private int translationOffset;// 平移长度
  private Matrix mMatrix;// 矩阵
  private ValueAnimator mValueAnimator;// 动画

  public LinearGradientLayout(Context context) {
    this(context, null);
  }

  public LinearGradientLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LinearGradientLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mPaint = new Paint();
    mMatrix = new Matrix();
    LogUtils.e("---> init()");
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    LogUtils.e("---> onLayout()");
    if (mValueAnimator == null) {
      mValueAnimator = ValueAnimator.ofInt(2 * getMeasuredWidth());
      mValueAnimator.addUpdateListener(this);
      mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
      mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);// ValueAnimator.INFINITE
      mValueAnimator.setDuration(2000);
      mValueAnimator.start();
    }
    if (mLinearGradient == null) {
      mLinearGradient = new LinearGradient(-getMeasuredWidth(), getMeasuredHeight() / 2, 0, getMeasuredHeight() / 2, 0xFF06C3D7, 0xFF1888EC, Shader.TileMode.MIRROR);
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    LogUtils.e("---> onDraw()");
    mMatrix.setTranslate(translationOffset, 0);
    mLinearGradient.setLocalMatrix(mMatrix);
    mPaint.setShader(mLinearGradient);
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
  }

  @Override
  public void onAnimationUpdate(ValueAnimator animation) {
    LogUtils.e("---> onAnimationUpdate()");
    if (getVisibility() == VISIBLE) {// 显示情况下进行重绘
      translationOffset = (Integer) animation.getAnimatedValue();
      LogUtils.e("---> " + translationOffset);
      postInvalidate();
    }
  }

  @Override
  protected void onVisibilityChanged(View changedView, int visibility) {
    super.onVisibilityChanged(changedView, visibility);
    LogUtils.e("---> onVisibilityChanged() --> " + visibility);
    switch (visibility) {
      case VISIBLE:
        resumeAnimator();
        break;
      case GONE:
      case INVISIBLE:
        pauseAnimator();
        break;
    }
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    destroyAnimator();
  }

  public void pauseAnimator() {
    if (mValueAnimator != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        mValueAnimator.pause();
      } else {
        mValueAnimator.end();
      }
    }
  }

  public void resumeAnimator() {
    if (mValueAnimator != null) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        mValueAnimator.resume();
      } else {
        mValueAnimator.start();
      }
    }
  }

  public void destroyAnimator() {
    if (mValueAnimator != null) {
      mValueAnimator.end();
      mValueAnimator.cancel();
      mValueAnimator.removeAllUpdateListeners();
      mValueAnimator = null;
    }
  }

}
