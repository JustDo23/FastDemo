package com.just.fast.module.file;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.just.fast.R;

/**
 * Here is an example of typical code to monitor the state of external storage.
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/reference/android/os/Environment.html#getExternalStorageDirectory()]
 * @since 2017年06月07日
 */
public class MonitorExternalStorageActivity extends AppCompatActivity {

  private BroadcastReceiver mExternalStorageReceiver;// 广播需要进行注册
  private boolean mExternalStorageAvailable = false;
  private boolean mExternalStorageWriteable = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_monitor_external_storage);
  }

  private void updateExternalStorageState() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      mExternalStorageAvailable = mExternalStorageWriteable = true;
    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      mExternalStorageAvailable = true;
      mExternalStorageWriteable = false;
    } else {
      mExternalStorageAvailable = mExternalStorageWriteable = false;
    }
    // handleExternalStorageState(mExternalStorageAvailable, mExternalStorageWriteable);
  }

  void startWatchingExternalStorage() {
    mExternalStorageReceiver = new BroadcastReceiver() {

      @Override
      public void onReceive(Context context, Intent intent) {
        Log.i("test", "Storage: " + intent.getData());
        updateExternalStorageState();
      }
    };
    IntentFilter filter = new IntentFilter();
    filter.addAction(Intent.ACTION_MEDIA_MOUNTED);// 监听接收的字符串 挂载
    filter.addAction(Intent.ACTION_MEDIA_REMOVED);// 监听接收的字符串 移除
    registerReceiver(mExternalStorageReceiver, filter);
    updateExternalStorageState();
  }

  void stopWatchingExternalStorage() {
    unregisterReceiver(mExternalStorageReceiver);
  }
}
