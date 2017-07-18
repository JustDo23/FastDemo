package com.just.fast.module.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.just.utils.toast.ToastUtil;

/**
 * 静态广播
 *
 * @author JustDo23
 * @since 2017年07月17日
 */
public class CustomReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    ToastUtil.showShortToast(context, "Fast Demo Custom Receiver.");
  }
}
