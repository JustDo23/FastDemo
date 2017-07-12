package com.just.fast.module.vector;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.just.fast.R;

/**
 * 简单了解 Android 中的 SVG 即 Vector
 *
 * @author JustDo23
 * @webUrl http://www.jianshu.com/p/e3614e7abc03
 * @since 2017年07月11日
 */
public class VectorActivity extends AppCompatActivity {

  static {// 为了向下兼容
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
  }

  private ImageView iv_vector;
  private ImageView iv_anim;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vector);

    iv_vector = (ImageView) findViewById(R.id.iv_vector);
    iv_vector.setImageResource(R.drawable.vector_rectangle);

    iv_anim = (ImageView) findViewById(R.id.iv_anim);
    AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animated_vector);
    iv_anim.setImageDrawable(animatedVectorDrawableCompat);
    ((Animatable) iv_anim.getDrawable()).start();
  }

}
