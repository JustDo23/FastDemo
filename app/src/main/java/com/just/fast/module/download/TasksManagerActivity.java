package com.just.fast.module.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.just.fast.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 任务管理界面
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManagerActivity extends AppCompatActivity {

  private RecyclerView rv_task_list;// 下载列表
  private TextView tv_empty;// 空布局
  private TasksManagerAdapter adapter;
  private List<TasksManagerModel> tasksManagerModelList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tasks_manager);
    initData();
    tv_empty = (TextView) findViewById(R.id.tv_empty);
    rv_task_list = (RecyclerView) findViewById(R.id.rv_task_list);
    rv_task_list.setLayoutManager(new LinearLayoutManager(this));
    adapter = new TasksManagerAdapter(tasksManagerModelList);
    rv_task_list.setAdapter(adapter);
    tasksManagerModelList.clear();
    tasksManagerModelList.addAll(TasksManager.getInstance().getTaskList());
    adapter.notifyDataSetChanged();
    if (tasksManagerModelList.size() == 0) {
      tv_empty.setVisibility(View.VISIBLE);
    }
    TasksManager.getInstance().onCreate(new WeakReference<>(this));
  }


  public void postNotifyDataChanged() {
    if (adapter != null) {
      runOnUiThread(new Runnable() {

        @Override
        public void run() {
          if (adapter != null) {
            adapter.notifyDataSetChanged();
          }
        }
      });
    }
  }

  @Override
  protected void onDestroy() {
//    TasksManager.getInstance().onDestroy();
//    adapter = null;
//    FileDownloader.getImpl().pauseAll();
    super.onDestroy();
  }

  // -----------------

  private void initData() {
    String url01 = "http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk";
    String name01 = "大个儿 APK";
    String url02 = "http://dg.101.hk/1.rar";
    String name02 = "小个儿压缩包";
    String url03 = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";
    String name03 = "测试下载";
    TasksManager.getInstance().insertTask(url01, name01);// 添加数据库
    TasksManager.getInstance().insertTask(url02, name02);// 添加数据库
    TasksManager.getInstance().insertTask(url03, name03);// 添加数据库
  }


}
