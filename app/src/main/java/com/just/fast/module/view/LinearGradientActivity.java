package com.just.fast.module.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.just.fast.R;

public class LinearGradientActivity extends AppCompatActivity implements View.OnClickListener {

  private LinearGradientLayout lgl_test;
  private Button bt_control;
  private Button bt_display;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_linear_gradient);
    lgl_test = (LinearGradientLayout) findViewById(R.id.lgl_test);
    bt_control = (Button) findViewById(R.id.bt_control);
    bt_control.setOnClickListener(this);
    bt_display = (Button) findViewById(R.id.bt_display);
    bt_display.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_control:
        if ("start".equals(bt_control.getText().toString())) {
          bt_control.setText("stop");
        } else if ("stop".equals(bt_control.getText().toString())) {
          bt_control.setText("start");
        }
        break;
      case R.id.bt_display:
        if ("hide".equals(bt_display.getText().toString())) {
          bt_display.setText("show");
          lgl_test.setVisibility(View.GONE);
        } else if ("show".equals(bt_display.getText().toString())) {
          bt_display.setText("hide");
          lgl_test.setVisibility(View.VISIBLE);
        }
        break;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    lgl_test.resumeAnimator();
  }

  @Override
  protected void onPause() {
    super.onPause();
    lgl_test.pauseAnimator();
  }
}
