package com.just.fast.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭工具类
 *
 * @webUrl [https://github.com/Blankj/AndroidUtilCode]
 */
public class CloseUtil {

  private CloseUtil() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }

  /**
   * 关闭IO
   *
   * @param closeables closeables
   */
  public static void closeIO(Closeable... closeables) {
    if (closeables == null) return;
    for (Closeable closeable : closeables) {
      if (closeable != null) {
        try {
          closeable.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 安静关闭IO
   *
   * @param closeables closeables
   */
  public static void closeIOQuietly(Closeable... closeables) {
    if (closeables == null) return;
    for (Closeable closeable : closeables) {
      if (closeable != null) {
        try {
          closeable.close();
        } catch (IOException ignored) {
        }
      }
    }
  }

}
