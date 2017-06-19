package com.just.fast.module.download;

import android.text.TextUtils;
import android.util.SparseArray;

import com.just.fast.base.FastApplication;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 管理类
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManager {

  private TasksManagerDao dao;
  private static TasksManager instance;

  private TasksManager() {
    dao = new TasksManagerDao(FastApplication.mContext);
  }

  public static TasksManager getInstance() {
    if (instance == null) {
      synchronized (TasksManager.class) {
        if (instance == null) {
          instance = new TasksManager();
        }
      }
    }
    return instance;
  }


  public TasksManagerModel insertTask(String url, String name) {
    if (TextUtils.isEmpty(url)) {
      return null;
    }
    TasksManagerModel model = new TasksManagerModel();
    model.setUrl(url);
    model.setPath(FileDownloadUtils.getDefaultSaveFilePath(url));
    model.setName(name);
    model.setId(FileDownloadUtils.generateId(url, model.getPath()));
    return dao.insertTask(model);
  }

  public List<TasksManagerModel> getTaskList() {
    return dao.getTaskList();
  }


  private FileDownloadConnectListener listener;// 连接监听事件

  public void onCreate(WeakReference<TasksManagerActivity> activityWeakReference) {
    if (!FileDownloader.getImpl().isServiceConnected()) {// 服务不连接
      FileDownloader.getImpl().bindService();// 连接服务
      registerServiceConnectionListener(activityWeakReference);
    }
  }

  private void registerServiceConnectionListener(final WeakReference<TasksManagerActivity> activityWeakReference) {
    if (listener != null) {// 删除上次的回调绑定记录
      FileDownloader.getImpl().removeServiceConnectListener(listener);
    }

    listener = new FileDownloadConnectListener() {// 重新进行初始化

      @Override
      public void connected() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
          return;
        }
        activityWeakReference.get().postNotifyDataChanged();// 通知适配器刷新
      }

      @Override
      public void disconnected() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
          return;
        }
        activityWeakReference.get().postNotifyDataChanged();// 通知适配器刷新
      }
    };

    FileDownloader.getImpl().addServiceConnectListener(listener);
  }

  private void unregisterServiceConnectionListener() {
    FileDownloader.getImpl().removeServiceConnectListener(listener);
    listener = null;
  }


  public boolean isReady() {
    return FileDownloader.getImpl().isServiceConnected();
  }

  public int getStatus(int id, String path) {
    return FileDownloader.getImpl().getStatus(id, path);
  }

  public long getTotal(int id) {
    return FileDownloader.getImpl().getTotal(id);
  }

  public long getSoFar(int id) {
    return FileDownloader.getImpl().getSoFar(id);
  }


  private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

  public void updateViewHolder(int id, TasksManagerAdapter.ViewHolder holder) {
    BaseDownloadTask task = taskSparseArray.get(id);
    if (task == null) {
      return;
    }
    task.setTag(holder);
  }

  public void addTask(BaseDownloadTask task) {
    taskSparseArray.put(task.getId(), task);
  }

  public BaseDownloadTask getTaskByID(int id) {
    return taskSparseArray.get(id);
  }

  // ------------


  public void onDestroy() {
    unregisterServiceConnectionListener();
    releaseTask();
  }

  public void releaseTask() {
    taskSparseArray.clear();
  }


  public void removeTaskForViewHolder(int id) {
    taskSparseArray.remove(id);
  }


}
