package com.just.fast.module.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.just.fast.R;

/**
 * 使用[WebView]
 *
 * @author JustDo23
 * @since 2017年09月12日
 */
public class WebViewActivity extends AppCompatActivity {

  private String url = "https://www.baidu.com";
  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    webView = (WebView) findViewById(R.id.webView);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.loadUrl(url);
  }

}
