package com.just.fast.module.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import com.just.fast.R;

/**
 * ViewStub 学习使用
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/reference/android/view/ViewStub.html]
 * @since 2017年09月20日
 */
public class ViewStubActivity extends AppCompatActivity implements View.OnClickListener {

  private ViewStub viewStub;// 占位控件
  private Button bt_show;// 显示按钮
  private Button bt_hide;// 隐藏按钮
  private TextView tv_show_title;// 标题

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_stub);
    viewStub = (ViewStub) findViewById(R.id.viewStub);// 寻找控件
    // 设置加载监听回调，成功加载后回调[只会回调一次]
    viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {

      /**
       * Listener used to receive a notification after a ViewStub has successfully inflated its layout resource.
       *
       * @param stub ViewStub 对象
       * @param inflated 被加载填充的布局
       */
      @Override
      public void onInflate(ViewStub stub, View inflated) {
        tv_show_title = (TextView) inflated.findViewById(R.id.tv_show_title);
        tv_show_title.setText("ShowTitle");
      }
    });
    bt_show = (Button) findViewById(R.id.bt_show);
    bt_hide = (Button) findViewById(R.id.bt_hide);
    bt_show.setOnClickListener(this);
    bt_hide.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_show:// 显示
        try {
          View titleBar = viewStub.inflate();// 第二次加载会抛出异常
          tv_show_title = (TextView) titleBar.findViewById(R.id.tv_show_title);
          tv_show_title.setText("Title");
        } catch (Exception e) {
          viewStub.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.bt_hide:// 隐藏
        viewStub.setVisibility(View.GONE);
        break;
    }
  }

  public void viewStubShow() {
    viewStub.setVisibility(View.VISIBLE);
    if (tv_show_title == null) {
      tv_show_title = (TextView) findViewById(R.id.tv_show_title);
      tv_show_title.setText("Title");
    }
  }

}
