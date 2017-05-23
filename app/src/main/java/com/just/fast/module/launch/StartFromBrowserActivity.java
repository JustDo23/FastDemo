package com.just.fast.module.launch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.just.fast.R;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从浏览器启动应用
 *
 * @author JustDo23
 * @since 2017年05月22日
 */
public class StartFromBrowserActivity extends AppCompatActivity {

  @BindView(R.id.tv_info) TextView tv_info;// 信息
  @BindView(R.id.bt_start_browser) Button bt_start_browser;

  private HashMap<String, String> browserMap = new HashMap<>();// 浏览器数据

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start_from_browser);
    ButterKnife.bind(this);
    printInfo();
    getParameterFromBrowser();
  }


  private void printInfo() {
    Intent intent = getIntent();
    String uri = intent.getDataString();
    tv_info.setText("Info : " + uri);
    Uri data = intent.getData();
    if (data == null) return;
    Log.e("JustDo23", "data : " + data);
    Log.e("JustDo23", "getScheme : " + data.getScheme());
    Log.e("JustDo23", "getHost : " + data.getHost());
    Log.e("JustDo23", "getAuthority : " + data.getAuthority());
    Log.e("JustDo23", "getPath : " + data.getPath());
    Log.e("JustDo23", "getLastPathSegment : " + data.getLastPathSegment());
    Log.e("JustDo23", "getQueryParameterNames : " + data.getQueryParameterNames());
    Log.e("JustDo23", "getQueryParameter : " + data.getQueryParameter("url"));
  }


  /**
   * 从浏览器获取数据[方法一]
   */
  private void getParameterFromBrowser() {
    Intent intent = getIntent();
    Uri data = intent.getData();
    if (data == null) return;
    browserMap.put("scheme", data.getScheme());
    browserMap.put("host", data.getHost());
    Set<String> queryParameterNames = data.getQueryParameterNames();
    String[] queryParamNameArray = (String[]) queryParameterNames.toArray();
    for (int i = 0; i < queryParamNameArray.length; i++) {
      String key = queryParamNameArray[i];
      browserMap.put(key, data.getQueryParameter(key));
    }
  }


  /**
   * 从浏览器获取数据[方法二]
   */
  private void getDataFromBrowser() {
    Intent intent = getIntent();
    String uri = intent.getDataString();
    if (uri != null) {
      String scheme = intent.getScheme();
      String leave = uri.split(scheme + "://")[1];
      String query;
      if (leave.contains("?")) {
        query = leave.split("\\?")[1];
      } else {
        query = leave;
      }
      browserMap = new HashMap();
      String[] split = query.split("&");
      for (int i = 0; i < split.length; i++) {
        String key = split[i].split("=")[0];
        String value = split[i].split("=")[1];
        browserMap.put(key, value);
      }
      browserMap.toString();
    }
  }


  @OnClick(R.id.bt_start_browser)
  public void onClick() {

  }
}
