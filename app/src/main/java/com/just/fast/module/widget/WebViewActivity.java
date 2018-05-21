package com.just.fast.module.widget;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ConsoleMessage;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.just.fast.R;
import com.just.utils.log.LogUtils;

/**
 * 使用[WebView]
 *
 * @author JustDo23
 * @WebUrl [http://lipeng1667.github.io/2017/01/03/android-webview-https-and-taobaoke/]
 * @WebUrl [https://www.jianshu.com/p/f1803962c0f1]
 * @WebUrl [chrome://inspect]
 * @since 2017年09月12日
 */
public class WebViewActivity extends AppCompatActivity {

  private String url = "https://h5.mgzf.com/event/goallout?partnervip=bdyl_zyzh_35";
  private WebView webView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true);
    }

    webView = (WebView) findViewById(R.id.webView);
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);

    webView.setWebViewClient(new WebViewClient() {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtils.e("Finish");
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        LogUtils.e("onReceivedSslError ---> " + error.toString());
        handler.proceed();// 任何证书都不校验
      }

      @Override
      public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        LogUtils.e("onReceivedError ---> " + error.toString());
        super.onReceivedError(view, request, error);
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        LogUtils.e("onReceivedError ---> " + errorCode);
        super.onReceivedError(view, errorCode, description, failingUrl);
      }

      @Override
      public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        LogUtils.e("onReceivedHttpError ---> " + errorResponse.toString());
        super.onReceivedHttpError(view, request, errorResponse);
      }
    });

    webView.setWebChromeClient(new WebChromeClient() {

      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        LogUtils.e("newProgress = " + newProgress);
      }

      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        LogUtils.e(String.format("%s @ %d: %s", consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId()));
        return true;
      }
    });

    webView.loadUrl(url);
  }

}
