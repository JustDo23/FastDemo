package com.just.fast.module.file;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.just.fast.R;
import com.just.utils.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 存储[内部存储][外部存储]
 *
 * @author JustDo23
 * @webUrl [http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2013/0923/1557.html]
 * @since 2017年06月07日
 */
public class FileActivity extends AppCompatActivity {

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file);
    filePath(this);
    // accessTest();// 可以别的应用在外部存储的文件
  }

  /**
   * 查看文件路径
   *
   * @param context 上下文
   */
  private void filePath(Context context) {
    // 通过 Context 获取内部存储路径
    LogUtils.e("context.getFilesDir() = " + context.getFilesDir());// [ /data/data/com.just.fast/files ]
    LogUtils.e("context.getCacheDir() = " + context.getCacheDir());// [ /data/data/com.just.fast/cache ]
    LogUtils.e("context.getDir(dirName, MODE_PRIVATE) = " + context.getDir("dirName", MODE_PRIVATE));// [ /data/data/com.just.fast/app_dirName ]
    // 获取外部存储根路径[先判断外部存储是否挂载][读写文件需要权限]
    LogUtils.e("Environment.getExternalStorageState() = " + Environment.getExternalStorageState());// [ mounted ]
    LogUtils.e("Environment.getExternalStorageDirectory() = " + Environment.getExternalStorageDirectory());// [ /storage/emulated/0 ]
    // [不建议在外部存储根目录操作][因此获取外部存储私有路径][不需要权限][私有文件]
    LogUtils.e("context.getExternalFilesDir(null) = " + context.getExternalFilesDir(null));// [ /storage/emulated/0/Android/data/com.just.fast/files ]
    LogUtils.e("context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) = " + context.getExternalFilesDir(Environment.DIRECTORY_MOVIES));// [ /storage/emulated/0/Android/data/com.just.fast/files/Movies ]
    LogUtils.e("context.getExternalCacheDir() = " + context.getExternalCacheDir());// [ /storage/emulated/0/Android/data/com.just.fast/cache ]
    // [不建议在外部存储根目录操作][因此获取外部存储共享路径][读写文件需要权限][公共文件]
    LogUtils.e("Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) = " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));// [ /storage/emulated/0/Music ]

    // 其他路径[获取系统根路径下的相关路径]
    LogUtils.e("Environment.getRootDirectory() = " + Environment.getRootDirectory());// [ /system ]
    LogUtils.e("Environment.getDataDirectory() = " + Environment.getDataDirectory());// [ /data ]
    LogUtils.e("Environment.getDownloadCacheDirectory() = " + Environment.getDownloadCacheDirectory());// [ /cache ]

    // [true,仿真外部存储][一体机之类]
    LogUtils.e("Environment.isExternalStorageEmulated() = " + Environment.isExternalStorageEmulated());// [ true ]
    // [true,外部存储物理硬件可以移除][SD卡子类][false,一体机之类]
    LogUtils.e("Environment.isExternalStorageRemovable() = " + Environment.isExternalStorageRemovable());// [ false ]
  }

  /**
   * 测试-访问别的应用的的文件[可以访问]
   */
  private void accessTest() {
    File file = new File("/storage/emulated/0/Android/data/com.youku.phone/files/hotstartad/hotstartjson");
    if (file.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] hots = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = fileInputStream.read();
        while (-1 != length) {
          fileInputStream.read(hots, 0, length);
          byteArrayOutputStream.write(hots, 0, length);
          length = fileInputStream.read();
        }
        LogUtils.e("数据 ： " + byteArrayOutputStream.toString());
        fileInputStream.close();
        byteArrayOutputStream.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


}
