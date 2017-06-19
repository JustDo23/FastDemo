package com.just.fast.module.download;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.fast.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.List;

/**
 * 任务管理界面适配器
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManagerAdapter extends RecyclerView.Adapter<TasksManagerAdapter.ViewHolder> {

  private List<TasksManagerModel> tasksManagerModelList;

  public TasksManagerAdapter(List<TasksManagerModel> tasksManagerModelList) {
    this.tasksManagerModelList = tasksManagerModelList;
  }

  @Override
  public int getItemCount() {
    return tasksManagerModelList == null ? 0 : tasksManagerModelList.size();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tasks_manager_item, null);
    ViewHolder holder = new ViewHolder(rootView);
    holder.bt_task_action.setOnClickListener(buttonOnClickListener);
    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    TasksManagerModel model = tasksManagerModelList.get(position);
    holder.tv_task_name.setText(model.getName());
    holder.tv_task_status.setText("状态");
    holder.pb_task.setMax(100);
    holder.pb_task.setProgress(0);
    holder.bt_task_action.setText("开始");

    holder.setTasksManagerModel(model);
    holder.bt_task_action.setTag(holder);
    TasksManager.getInstance().updateViewHolder(model.getId(), holder);

    if (TasksManager.getInstance().isReady()) {
      int status = TasksManager.getInstance().getStatus(model.getId(), model.getPath());
      if (!new File(model.getPath()).exists() && !new File(FileDownloadUtils.getTempPath(model.getPath())).exists()) {
        // not exist file
        holder.updateNotDownloaded(status, 0, 0);
        return;
      }
      switch (status) {
        case FileDownloadStatus.pending:
        case FileDownloadStatus.started:
        case FileDownloadStatus.connected:
          // start task, but file not created yet
          holder.updateDownloading(status, TasksManager.getInstance().getSoFar(model.getId()), TasksManager.getInstance().getTotal(model.getId()));
          break;
        case FileDownloadStatus.progress:
          // downloading
          holder.updateDownloading(status, TasksManager.getInstance().getSoFar(model.getId()), TasksManager.getInstance().getTotal(model.getId()));
          TasksManager.getInstance().getTaskByID(model.getId()).setListener(fileDownloadListener);
          break;
        case FileDownloadStatus.completed:
          // already downloaded and exist
          holder.updateDownloaded();
          break;
        default:
          // not start
          holder.updateNotDownloaded(status, TasksManager.getInstance().getSoFar(model.getId()), TasksManager.getInstance().getTotal(model.getId()));
          break;
      }
    } else {
      holder.tv_task_status.setText("状态：未连接");
    }
  }


  class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tv_task_name;// 标题
    private TextView tv_task_status;// 状态
    private ProgressBar pb_task;// 进度
    private Button bt_task_action;// 按钮
    private TasksManagerModel tasksManagerModel;

    public ViewHolder(View itemView) {
      super(itemView);
      tv_task_name = (TextView) itemView.findViewById(R.id.tv_task_name);
      tv_task_status = (TextView) itemView.findViewById(R.id.tv_task_status);
      pb_task = (ProgressBar) itemView.findViewById(R.id.pb_task);
      bt_task_action = (Button) itemView.findViewById(R.id.bt_task_action);
    }

    public TasksManagerModel getTasksManagerModel() {
      return tasksManagerModel;
    }

    public void setTasksManagerModel(TasksManagerModel tasksManagerModel) {
      this.tasksManagerModel = tasksManagerModel;
    }

    public void updateDownloaded() {
      pb_task.setProgress(100);
      tv_task_status.setText("状态：下载完成");
      bt_task_action.setText("删除");
    }

    public void updateNotDownloaded(int status, long sofar, long total) {
      if (sofar > 0 && total > 0) {
        float percent = sofar / (float) total;
        pb_task.setProgress((int) (percent * 100));
      } else {
        pb_task.setProgress(0);
      }

      switch (status) {
        case FileDownloadStatus.error:
          tv_task_status.setText("状态：出错");
          break;
        case FileDownloadStatus.paused:
          tv_task_status.setText("状态：暂停");
          break;
        default:
          tv_task_status.setText("状态：未下载");
          break;
      }
      bt_task_action.setText("开始");
    }

    public void updateDownloading(int status, long sofar, long total) {
      float percent = sofar / (float) total;
      pb_task.setProgress((int) (percent * 100));

      switch (status) {
        case FileDownloadStatus.pending:
          tv_task_status.setText("状态：队列中");
          break;
        case FileDownloadStatus.started:
          tv_task_status.setText("状态：开始下载");
          break;
        case FileDownloadStatus.connected:
          tv_task_status.setText("状态：已连接上");
          break;
        case FileDownloadStatus.progress:
          tv_task_status.setText("状态：下载中...");
          break;
        default:
          tv_task_status.setText("正在下载" + (int) (percent * 100) + "%");
          break;
      }
      bt_task_action.setText("暂停");
    }

  }


  private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      if (v.getTag() == null) {
        return;
      }
      ViewHolder viewHolder = (ViewHolder) v.getTag();
      TasksManagerModel model = viewHolder.getTasksManagerModel();
      String action = ((TextView) v).getText().toString();
      switch (action) {
        case "暂停":
          FileDownloader.getImpl().pause(model.getId());
          break;
        case "开始":
          BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(model.getUrl())
              .setPath(model.getPath())
              .setCallbackProgressTimes(100)
              .setListener(fileDownloadListener);
          TasksManager.getInstance().addTask(baseDownloadTask);
          TasksManager.getInstance().updateViewHolder(model.getId(), viewHolder);

          baseDownloadTask.start();
          break;
        case "删除":
          break;
      }

    }
  };

  private FileDownloadListener fileDownloadListener = new FileDownloadListener() {

    private ViewHolder checkCurrentHolder(BaseDownloadTask task) {
      if (task.getTag() == null) {
        return null;
      }
      ViewHolder viewHolder = (ViewHolder) task.getTag();
      if (viewHolder.getTasksManagerModel().getId() != task.getId()) {
        return null;
      }
      return viewHolder;
    }

    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateDownloading(FileDownloadStatus.pending, soFarBytes, totalBytes);
    }

    @Override
    protected void started(BaseDownloadTask task) {
      super.started(task);
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.tv_task_status.setText("状态：开始");
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
      super.connected(task, etag, isContinue, soFarBytes, totalBytes);
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
    }

    @Override
    protected void completed(BaseDownloadTask task) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateDownloaded();
      TasksManager.getInstance().removeTaskForViewHolder(task.getId());
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
      viewHolder.bt_task_action.setText("开始");
      TasksManager.getInstance().removeTaskForViewHolder(task.getId());
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
      viewHolder.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes(), task.getLargeFileTotalBytes());
      TasksManager.getInstance().removeTaskForViewHolder(task.getId());
    }

    @Override
    protected void warn(BaseDownloadTask task) {
      ViewHolder viewHolder = checkCurrentHolder(task);
      if (viewHolder == null) {
        return;
      }
    }
  };

}
