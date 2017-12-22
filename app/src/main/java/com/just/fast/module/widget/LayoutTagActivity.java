package com.just.fast.module.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.debug.hv.ViewServer;
import com.just.fast.R;

/**
 * 布局标签 include merge
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/training/improving-layouts/reusing-layouts.html]
 * @since 2017年09月21日
 */
public class LayoutTagActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ViewServer.get(this).addWindow(this);
    setContentView(R.layout.activity_layout_tag);
  }


  public void onResume() {
    super.onResume();
    ViewServer.get(this).setFocusedWindow(this);
  }

  public void onDestroy() {
    super.onDestroy();
    ViewServer.get(this).removeWindow(this);
  }

}
