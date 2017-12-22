package com.just.fast.module.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class ShimmerTextView extends TextView {
  private Paint mPaint;
  private int mDx;
  private LinearGradient mLinearGradient;

  public ShimmerTextView(Context context) {
    super(context);
    init();
  }

  public ShimmerTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ShimmerTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    mPaint = getPaint();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);

    ValueAnimator animator = ValueAnimator.ofInt(0, 2 * getMeasuredWidth());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mDx = (Integer) animation.getAnimatedValue();
        postInvalidate();
      }
    });
    animator.setRepeatMode(ValueAnimator.RESTART);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setDuration(2000);
    animator.start();

    mLinearGradient = new LinearGradient(-getMeasuredWidth(), 0, 0, 0, new int[]{
        getCurrentTextColor(), 0xff00ff00, getCurrentTextColor()
    },
        new float[]{
            0,
            0.5f,
            1
        },
        Shader.TileMode.CLAMP
    );
  }

  @Override
  protected void onDraw(Canvas canvas) {

    Matrix matrix = new Matrix();
    matrix.setTranslate(mDx, 0);
    mLinearGradient.setLocalMatrix(matrix);
    mPaint.setShader(mLinearGradient);

    super.onDraw(canvas);
  }
}