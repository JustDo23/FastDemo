package com.just.fast.module.file;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.just.fast.R;
import com.just.utils.log.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用内部存储
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/guide/topics/data/data-storage.html#filesInternal]
 * @since 2017年06月19日
 */
public class FileInternalActivity extends AppCompatActivity {

  @BindView(R.id.tv_write) TextView tv_write;
  @BindView(R.id.tv_read) TextView tv_read;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_internal);
    ButterKnife.bind(this);
    write2Internal();// 写
    tv_write.setText("写入成功");
    readFromInternal();// 读
    LogUtils.e(getInternalRootFilePath());// 根目录
    lookInternalFileList();// 看看文件列表
    deleteInternalFile();// 删除文件
    createInternalFileDir();// 创建目录
    lookInternalFileList();// 看看文件列表
  }

  /**
   * 向内部存储写数据
   */
  private void write2Internal() {
    String fileName = "just";// 文件名
    String writeContent = "Test open and write file internal.";// 写入内容
    try {
      FileOutputStream fileOutputStream = this.openFileOutput(fileName, MODE_APPEND);// 打开文件输出流
      fileOutputStream.write(writeContent.getBytes());// 流写入数据
      fileOutputStream.close();// 关闭流
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 从内部存储读数据
   */
  private void readFromInternal() {
    String fileName = "just";// 文件名
    try {
      FileInputStream fileInputStream = this.openFileInput(fileName);// 打开文件输入流
      byte[] allByte = new byte[fileInputStream.available()];// 字节数组
      fileInputStream.read(allByte);// 读取
      String readContent = new String(allByte);// 转换为字符串
      fileInputStream.close();
      tv_read.setText(readContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取内部存储根目录
   *
   * @return 内部存储根目录
   */
  private String getInternalRootFilePath() {
    return this.getFilesDir().getPath();
  }

  /**
   * 查看内部存储中文件列表
   */
  private void lookInternalFileList() {
    LogUtils.e("list start");
    String[] files = this.fileList();
    for (String file : files) {
      LogUtils.e(file);
    }
  }

  /**
   * 删除内部存储上文件
   */
  private void deleteInternalFile() {
    String fileName = "just";// 文件名
    this.deleteFile(fileName);
  }

  /**
   * 创建内部存储上的目录
   */
  private void createInternalFileDir() {
    String dirName = "do";// 目录名
    File dir = this.getDir(dirName, MODE_PRIVATE);
    LogUtils.e(dir.getAbsolutePath());
  }

}
