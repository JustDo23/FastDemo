package com.just.fast.module.vector;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

import com.just.fast.R;

/**
 * 简单了解 Vector 实现动画效果
 *
 * @author JustDo23
 * @webUrl [http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0123/2346.html]
 * @webUrl [http://jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0201/2396.html]
 * @webUrl [http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0301/2514.html]
 * @webUrl [http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0301/2515.html]
 * @webUrl [http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0306/2553.html]
 * @since 2017年07月14日
 */
public class VectorAnimActivity extends AppCompatActivity implements View.OnClickListener {

  static {// 为了向下兼容
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  private ImageView iv_start;
  private ImageView iv_shape;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vector_anima);

    iv_start = (ImageView) findViewById(R.id.iv_start);
    iv_start.setOnClickListener(this);
    iv_shape = (ImageView) findViewById(R.id.iv_shape);
    iv_shape.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.iv_start:
        AnimatedVectorDrawableCompat startDrawable = (AnimatedVectorDrawableCompat) iv_start.getDrawable();
        startDrawable.start();
        break;
      case R.id.iv_shape:// 这中变换比较坑
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          AnimatedVectorDrawable shapeDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.animated_vector_shape);
          iv_shape.setImageDrawable(shapeDrawable);
          shapeDrawable.start();
        } else {
          ((Animatable) iv_shape.getDrawable()).start();
        }
        break;
    }
  }
}
