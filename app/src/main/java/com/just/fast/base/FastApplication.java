package com.just.fast.base;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * 自定义全局 Application 入口及初始化
 *
 * @author JustDo23
 */
public class FastApplication extends Application {

  public static Context mContext;

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = this;
    initLocalCookie();
    initFileDownloader();
  }

  /**
   * 初始化流利说文件下载
   */
  private void initFileDownloader() {
    FileDownloader.init(getApplicationContext());// 仅仅是缓存Application的Context，不耗时
  }

  /**
   * 初始化本地 Cookie
   */
  private void initLocalCookie() {
    CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
  }

}
