package com.just.fast.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.just.fast.R;
import com.just.fast.module.calendar.CalendarActivity;
import com.just.fast.module.calendar.LoadersActivity;
import com.just.fast.module.calendar.OfficialCalendarActivity;
import com.just.fast.module.chart.ChartListActivity;
import com.just.fast.module.chart.LineChartActivity;
import com.just.fast.module.contentprovider.ContentProviderActivity;
import com.just.fast.module.custom.CustomLayoutActivity;
import com.just.fast.module.download.TasksIndexActivity;
import com.just.fast.module.download.TasksManagerActivity;
import com.just.fast.module.file.FileActivity;
import com.just.fast.module.file.FileInternalActivity;
import com.just.fast.module.file.MonitorExternalStorageActivity;
import com.just.fast.module.file.PicToExternalStorageActivity;
import com.just.fast.module.launch.StartFromBrowserActivity;
import com.just.fast.module.media.SurfaceMediaActivity;
import com.just.fast.module.recorder.MediaRecorderAudioActivity;
import com.just.fast.module.vector.VectorActivity;
import com.just.fast.module.vector.VectorAnimActivity;
import com.just.fast.module.vector.VectorDrawActivity;
import com.just.fast.module.vlayout.VirtualLayoutActivity;
import com.just.fast.module.widget.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * [主界面]列表头部吸附
 *
 * @author JustDo23
 * @since 2017年08月08日
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, StickyListHeadersListView.OnStickyHeaderChangedListener {

  private StickyListHeadersListView lv_sticky;// 列表
  private List<String> titleList = new ArrayList<>();// 头部内容集合
  private Map<Integer, List<String>> contentMap = new HashMap<>();// 内容集合
  private MainAdapter mainAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    lv_sticky = (StickyListHeadersListView) findViewById(R.id.lv_sticky);
    initTitles();// 初始化标题集合
    initContents();// 初始化内容集合
    mainAdapter = new MainAdapter(this, titleList, contentMap);
    lv_sticky.setAdapter(mainAdapter);
    lv_sticky.setOnItemClickListener(this);
    lv_sticky.setOnStickyHeaderChangedListener(this);
    lv_sticky.setAreHeadersSticky(true);
    lv_sticky.setDrawingListUnderStickyHeader(true);
  }

  /**
   * 初始化标题集合
   */
  private void initTitles() {
    titleList.add("被启动");
    titleList.add("Media");
    titleList.add("File");
    titleList.add("文件下载");
    titleList.add("SVG");
    titleList.add("自定义");
    titleList.add("录音");
    titleList.add("MPChart");
    titleList.add("ContentProvider");
    titleList.add("日历事件提醒");
    titleList.add("Library");
    titleList.add("意图");
    titleList.add("控件");
  }

  /**
   * 初始化内容集合
   */
  private void initContents() {
    List<String> contentList00 = new ArrayList<>();
    contentList00.add("从浏览器启动应用");
    contentMap.put(0, contentList00);
    List<String> contentList01 = new ArrayList<>();
    contentList01.add("SurfaceView 播放视频");
    contentMap.put(1, contentList01);
    List<String> contentList02 = new ArrayList<>();
    contentList02.add("文件路径");
    contentList02.add("监听外部存储");
    contentList02.add("文件写入外部存储");
    contentList02.add("使用内部存储");
    contentMap.put(2, contentList02);
    List<String> contentList03 = new ArrayList<>();
    contentList03.add("流利说下载界面");
    contentList03.add("流利说下载管理界面");
    contentMap.put(3, contentList03);
    List<String> contentList04 = new ArrayList<>();
    contentList04.add("Vector 入门");
    contentList04.add("Vector 路径");
    contentList04.add("Vector 动画");
    contentMap.put(4, contentList04);
    List<String> contentList05 = new ArrayList<>();
    contentList05.add("官方简单自定义布局");
    contentMap.put(5, contentList05);
    List<String> contentList06 = new ArrayList<>();
    contentList06.add("官方简单录音");
    contentMap.put(6, contentList06);
    List<String> contentList07 = new ArrayList<>();
    contentList07.add("线性表");
    contentList07.add("表集合");
    contentMap.put(7, contentList07);
    List<String> contentList08 = new ArrayList<>();
    contentList08.add("ContentProvider 访问其他程序");
    contentMap.put(8, contentList08);
    List<String> contentList09 = new ArrayList<>();
    contentList09.add("日历事件提醒测试");
    contentList09.add("官方日历提供程序");
    contentList09.add("学习使用加载器");
    contentMap.put(9, contentList09);
    List<String> contentList10 = new ArrayList<>();
    contentList10.add("阿里 VLayout");
    contentMap.put(10, contentList10);
    List<String> contentList11 = new ArrayList<>();
    contentList11.add("启动系统文件管理");
    contentMap.put(11, contentList11);
    List<String> contentList12 = new ArrayList<>();
    contentList12.add("网页");
    contentMap.put(12, contentList12);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    String content = ((TextView) view).getText().toString();
    switch (content) {
      case "从浏览器启动应用":
        startActivity(new Intent(this, StartFromBrowserActivity.class));
        break;
      case "SurfaceView 播放视频":
        startActivity(new Intent(this, SurfaceMediaActivity.class));
        break;
      case "文件路径":
        startActivity(new Intent(this, FileActivity.class));
        break;
      case "监听外部存储":
        startActivity(new Intent(this, MonitorExternalStorageActivity.class));
        break;
      case "文件写入外部存储":
        startActivity(new Intent(this, PicToExternalStorageActivity.class));
        break;
      case "使用内部存储":
        startActivity(new Intent(this, FileInternalActivity.class));
        break;
      case "流利说下载界面":
        startActivity(new Intent(this, TasksIndexActivity.class));
        break;
      case "流利说下载管理界面":
        startActivity(new Intent(this, TasksManagerActivity.class));
        break;
      case "Vector 入门":
        startActivity(new Intent(this, VectorActivity.class));
        break;
      case "Vector 路径":
        startActivity(new Intent(this, VectorDrawActivity.class));
        break;
      case "Vector 动画":
        startActivity(new Intent(this, VectorAnimActivity.class));
        break;
      case "官方简单自定义布局":
        startActivity(new Intent(this, CustomLayoutActivity.class));
        break;
      case "官方简单录音":
        startActivity(new Intent(this, MediaRecorderAudioActivity.class));
        break;
      case "线性表":
        startActivity(new Intent(this, LineChartActivity.class));
        break;
      case "表集合":
        startActivity(new Intent(this, ChartListActivity.class));
        break;
      case "ContentProvider 访问其他程序":
        startActivity(new Intent(this, ContentProviderActivity.class));
        break;
      case "日历事件提醒测试":
        startActivity(new Intent(this, CalendarActivity.class));
        break;
      case "官方日历提供程序":
        startActivity(new Intent(this, OfficialCalendarActivity.class));
        break;
      case "学习使用加载器":
        startActivity(new Intent(this, LoadersActivity.class));
        break;
      case "阿里 VLayout":
        startActivity(new Intent(this, VirtualLayoutActivity.class));
        break;
      case "启动系统文件管理":
        startSystemFile();
        break;
      case "网页":
        startActivity(new Intent(this, WebViewActivity.class));
        break;
    }
  }

  @Override
  public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {

  }


  /**
   * 启动系统文件管理
   */
  private void startSystemFile() {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/jpeg");
    startActivity(intent);
  }

}
