package com.just.fast.module.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * 线性渐变
 *
 * @author JustDo23
 * @webUrl [http://blog.csdn.net/harvic880925/article/details/52350154]
 * @since 2017年21月21日
 */
public class LinearGradientView extends View {

  private Paint mPaint;// 画笔
  private LinearGradient mLinearGradient;// 水平渐变

  public LinearGradientView(Context context) {
    this(context, null);
  }

  public LinearGradientView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setLayerType(LAYER_TYPE_HARDWARE, null);
    mPaint = new Paint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setShader(new LinearGradient(0, getHeight() / 2, getWidth(), getHeight() / 2, 0xFF06C3D7, 0xFF1888EC, Shader.TileMode.MIRROR));
    canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
  }

}
