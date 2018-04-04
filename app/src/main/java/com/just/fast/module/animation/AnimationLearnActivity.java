package com.just.fast.module.animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.just.fast.R;

/**
 * 动画入门
 *
 * @author JustDo23
 * @webUrl [http://wiki.jikexueyuan.com/project/android-animation/]
 * @since 2017年21月22日
 */
public class AnimationLearnActivity extends AppCompatActivity implements View.OnClickListener {

  private ImageView iv_result;
  private Button bt_scale;// 缩放
  private Animation animationScale;
  private Button bt_alpha;// 透明
  private Animation animationAlpha;
  private Button bt_rotate;// 旋转
  private Animation animationRotate;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_animation_learn);
    iv_result = (ImageView) findViewById(R.id.iv_result);
    bt_scale = (Button) findViewById(R.id.bt_scale);
    bt_scale.setOnClickListener(this);
    animationScale = AnimationUtils.loadAnimation(this, R.anim.anim_learn_scale);
    bt_alpha = (Button) findViewById(R.id.bt_alpha);
    bt_alpha.setOnClickListener(this);
    animationAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_learn_alpha);
    bt_rotate = (Button) findViewById(R.id.bt_rotate);
    bt_rotate.setOnClickListener(this);
    animationRotate = AnimationUtils.loadAnimation(this, R.anim.anim_learn_rotate);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_scale:
        iv_result.startAnimation(animationScale);
        break;
      case R.id.bt_alpha:
        iv_result.startAnimation(animationAlpha);
        break;
      case R.id.bt_rotate:
        iv_result.startAnimation(animationRotate);
        break;
    }
  }
}
