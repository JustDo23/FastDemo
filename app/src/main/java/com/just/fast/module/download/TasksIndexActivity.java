package com.just.fast.module.download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.just.fast.R;
import com.just.utils.log.LogUtils;
import com.just.utils.toast.ToastUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * 简单界面
 *
 * @author JustDo23
 * @webUrl [https://github.com/lingochamp/FileDownloader]
 * @since 2017年06月13日
 */
public class TasksIndexActivity extends AppCompatActivity implements View.OnClickListener {

  private Button bt_add;
  private Button bt_jump;
  private Button bt_pause;
  private Button bt_delete;

  private String videoUrl = "http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159";
  private String videoName = "自动添加了 Cookie";
  private int downloadId;
  private TasksManagerModel model;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tasks_index);
    initView();
  }

  private void initView() {
    bt_add = (Button) findViewById(R.id.bt_add);
    bt_pause = (Button) findViewById(R.id.bt_pause);
    bt_jump = (Button) findViewById(R.id.bt_jump);
    bt_delete = (Button) findViewById(R.id.bt_delete);
    bt_add.setOnClickListener(this);
    bt_pause.setOnClickListener(this);
    bt_jump.setOnClickListener(this);
    bt_delete.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_add:// 开始
        model = TasksManager.getInstance().insertTask(videoUrl, videoName);// 添加数据库
        // downLoadVideo(model.getUrl(), model.getPath());// 启动下载

        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(model.getUrl())
            .setPath(model.getPath())
            .setCallbackProgressTimes(100)
            .setListener(fileDownloadListener);
        TasksManager.getInstance().addTask(baseDownloadTask);
        int startID = baseDownloadTask.start();
        downloadId = startID;

        // 下面三个数据完全相同
        LogUtils.e("model.getId() = " + model.getId());
        LogUtils.e("startID = " + startID);
        LogUtils.e("baseDownloadTask.getId() = " + baseDownloadTask.getId());
        break;
      case R.id.bt_pause:// 暂停
        FileDownloader.getImpl().pause(downloadId);
        break;
      case R.id.bt_jump:// 跳转下载管理
        startActivity(new Intent(this, TasksManagerActivity.class));
        break;
      case R.id.bt_delete:
        new File(model.getPath()).delete();
        new File(FileDownloadUtils.getTempPath(model.getPath())).delete();
        break;
    }
  }


  // ----- 文件下载  -----

  private void downLoadVideo(String videoUrl) {
    String videoDir = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "just";
    int downloadId = FileDownloader.getImpl().create(videoUrl)
        .setPath(videoDir)
        .setListener(new FileDownloadListener() {

          @Override
          protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            ToastUtil.showShortToast(TasksIndexActivity.this, "pending");
          }

          @Override
          protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
            super.connected(task, etag, isContinue, soFarBytes, totalBytes);
            ToastUtil.showShortToast(TasksIndexActivity.this, "connected");
          }

          @Override
          protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LogUtils.e("下载 ---> progress() --> soFarBytes = " + soFarBytes + " , totalBytes = " + totalBytes);
            ToastUtil.showShortToast(TasksIndexActivity.this, "progress");
          }

          @Override
          protected void completed(BaseDownloadTask task) {
            ToastUtil.showShortToast(TasksIndexActivity.this, "completed");
          }

          @Override
          protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            ToastUtil.showShortToast(TasksIndexActivity.this, "paused");
          }

          @Override
          protected void error(BaseDownloadTask task, Throwable e) {
            ToastUtil.showShortToast(TasksIndexActivity.this, "error");
          }

          @Override
          protected void warn(BaseDownloadTask task) {
            ToastUtil.showShortToast(TasksIndexActivity.this, "warn");
          }
        })
        .start();

  }

  private int downLoadVideo(String videoUrl, String videoDir) {
    int downloadId = FileDownloader.getImpl().create(videoUrl)
        .setPath(videoDir)
        .setListener(fileDownloadListener)
        .start();
    return downloadId;
  }


  private FileDownloadListener fileDownloadListener = new FileDownloadListener() {

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      ToastUtil.showShortToast(TasksIndexActivity.this, "pending");
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
      super.connected(task, etag, isContinue, soFarBytes, totalBytes);
      ToastUtil.showShortToast(TasksIndexActivity.this, "connected");
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      LogUtils.e("下载 ---> progress() --> soFarBytes = " + soFarBytes + " , totalBytes = " + totalBytes);
      ToastUtil.showShortToast(TasksIndexActivity.this, "progress");
    }

    @Override
    protected void completed(BaseDownloadTask task) {
      ToastUtil.showShortToast(TasksIndexActivity.this, "completed");
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      ToastUtil.showShortToast(TasksIndexActivity.this, "paused");
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
      ToastUtil.showShortToast(TasksIndexActivity.this, "error");
    }

    @Override
    protected void warn(BaseDownloadTask task) {
      ToastUtil.showShortToast(TasksIndexActivity.this, "warn");
    }
  };

}
