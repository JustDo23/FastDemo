package com.just.fast.utils.size;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * [各种尺寸的工具类]
 *
 * @author JustDo23
 */
public class SizeUtil {

  /**
   * 根据手机的分辨率从 dp的单位 转成为 px(像素)
   *
   * @return 像素
   */
  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   *
   * @return dp
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   *
   * @return sp
   */
  public static int px2sp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   *
   * @return 像素
   */
  public static int sp2px(Context context, float spValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (spValue * scale + 0.5f);
  }

  /**
   * 获取屏幕宽度[像素]
   *
   * @param context 上下文对象
   * @return 屏幕的宽度[像素]
   */
  public final static int getWindowsWidth(Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    return dm.widthPixels;
  }

  /**
   * 获取屏幕高度[像素]
   *
   * @param context 上下文对象
   * @return 屏幕的宽度[像素]
   */
  public final static int getWindowsHeight(Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    return dm.heightPixels;
  }

}