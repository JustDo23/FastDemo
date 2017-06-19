package com.just.fast.utils.sdcard;

import android.os.Environment;

import com.just.fast.utils.CloseUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * SD 卡工具类
 *
 * @author JustDo23
 */
public class SDCardUtil {

  private SDCardUtil() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 判断 SD 卡是否可用
   *
   * @return true, 可以使用
   */
  public static boolean isSDCardEnable() {
    return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
  }

  /**
   * 获取 SD 卡根目录 [一般是 /storage/emulated/0/ ]
   *
   * @return SD 卡根目录 不存在返回 null
   */
  public static String getSDCardRootPath() {
    if (!isSDCardEnable()) return null;
    return Environment.getExternalStorageDirectory().getPath() + File.separator;
  }

  /**
   * 获取SD卡路径
   * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
   *
   * @return SD卡路径
   */
  public static String getSDCardPath() {
    if (!isSDCardEnable()) return null;
    String cmd = "cat /proc/mounts";
    Runtime run = Runtime.getRuntime();
    BufferedReader bufferedReader = null;
    try {
      Process p = run.exec(cmd);
      bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
      String lineStr;
      while ((lineStr = bufferedReader.readLine()) != null) {
        if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
          String[] strArray = lineStr.split(" ");
          if (strArray.length >= 5) {
            return strArray[1].replace("/.android_secure", "") + File.separator;
          }
        }
        if (p.waitFor() != 0 && p.exitValue() == 1) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      CloseUtil.closeIO(bufferedReader);
    }
    return Environment.getExternalStorageDirectory().getPath() + File.separator;
  }
}
