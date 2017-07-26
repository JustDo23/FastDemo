package com.just.fast.module.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.just.fast.R;
import com.just.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 使用[MPAndroidChart]进行图表绘制
 *
 * @author JustDo23
 * @webUrl [https://github.com/PhilJay/MPAndroidChart]
 * @webUrl [https://github.com/PhilJay/MPAndroidChart/wiki]
 * @webUrl [https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started]
 * @since 2017年07月24日
 */
public class LineChartActivity extends AppCompatActivity {

  private LineChart lc_learn;// 线性表
  private List<Entry> entryList = new ArrayList<>();// Entry 集合[坐标信息]
  private LineDataSet lineDataSet;// 一套坐标封装
  private LineData lineData;// 多套封装并提交给控件

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_line_chart);
    lc_learn = (LineChart) findViewById(R.id.lc_learn);// 线性表
    initEntryList();// 初始化坐标信息
    lineDataSet = new LineDataSet(entryList, "Label");// 一组数据封装[同类数据]
    lineDataSet.setColor(0xFFFF8800);// 样式[颜色]
    lineDataSet.setValueTextColor(0xFFFF4444);// 样式[颜色]
    lineData = new LineData(lineDataSet);// 多组数据封装并提供给控件[允许样式]
    lc_learn.setData(lineData);// 将数据提供给控件
    lc_learn.invalidate();// 控件进行刷新

    enableInteraction(lc_learn);// 允许交互事件
    setDeceleration(lc_learn);// 设置减速
    setOnChartListener(lc_learn);// 设置手势监听事件
    setHighlighting(lc_learn);// 设置点击高亮显示
    setHighlightingOne(lineDataSet);// 设置高亮及样式
  }

  /**
   * 初始化 Entry 集合
   */
  private void initEntryList() {
    Random random = new Random();
    for (int i = 0; i < 12; i++) {
      entryList.add(new Entry(i * 4 + 1, random.nextInt(50) + 1));
    }
  }

  /**
   * 允许交互事件
   *
   * @param lineChart
   */
  private void enableInteraction(LineChart lineChart) {
    lineChart.setTouchEnabled(true);// 允许所有的事件
    lineChart.setDragEnabled(true);// 允许拖拽
    lineChart.setScaleEnabled(true);// 允许所有轴缩放
    lineChart.setScaleXEnabled(true);// 允许X轴缩放
    lineChart.setScaleYEnabled(true);// 允许Y轴缩放
    lineChart.setPinchZoom(true);// [true则XY轴同时缩放][false则XY轴分别独立缩放]
    lineChart.setDoubleTapToZoomEnabled(true);// 允许双击放大
  }

  /**
   * 减速
   *
   * @param lineChart
   */
  private void setDeceleration(LineChart lineChart) {
    lineChart.setDragDecelerationEnabled(true);// [true则允许惯性滑动][false则禁止惯性滑动]
    lineChart.setDragDecelerationFrictionCoef(0.9f);// 设置惯性摩擦系数范围[0,1]越高摩擦越小
  }

  /**
   * 设置手势监听事件
   *
   * @param lineChart
   */
  private void setOnChartListener(LineChart lineChart) {
    lineChart.setOnChartGestureListener(new OnChartGestureListener() {

      /**
       * 开始手势事件[ACTION_DOWN]
       * @param me
       * @param lastPerformedGesture
       */
      @Override
      public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        LogUtils.e("--> onChartGestureStart()");
      }

      /**
       * 结束手势事件[ACTION_UP][ACTION_CANCEL]
       * @param me
       * @param lastPerformedGesture
       */
      @Override
      public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        LogUtils.e("--> onChartGestureEnd()");
      }

      /**
       * 长按事件
       * @param me
       */
      @Override
      public void onChartLongPressed(MotionEvent me) {
        LogUtils.e("--> onChartLongPressed()");
      }

      /**
       * 双击事件
       * @param me
       */
      @Override
      public void onChartDoubleTapped(MotionEvent me) {
        LogUtils.e("--> onChartDoubleTapped()");
      }

      /**
       * 单击事件
       * @param me
       */
      @Override
      public void onChartSingleTapped(MotionEvent me) {
        LogUtils.e("--> onChartSingleTapped()");
      }

      /**
       * 滑动事件[一屏显示不能滚动情况下]
       * @param me1
       * @param me2
       * @param velocityX
       * @param velocityY
       */
      @Override
      public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        LogUtils.e("--> onChartFling()");
      }

      /**
       * 缩放事件
       * @param me
       * @param scaleX
       * @param scaleY
       */
      @Override
      public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        LogUtils.e("--> onChartScale()");
      }

      /**
       * 滑动事件[惯性滑动等]
       * @param me
       * @param dX
       * @param dY
       */
      @Override
      public void onChartTranslate(MotionEvent me, float dX, float dY) {
        LogUtils.e("--> onChartTranslate()");
      }
    });
  }

  /**
   * 点击值高亮的设置
   *
   * @param lineChart
   */
  private void setHighlighting(LineChart lineChart) {
    lineChart.setHighlightPerDragEnabled(true);// 设置拖动时高亮显示[缩放至最小一屏显示]
    lineChart.setHighlightPerTapEnabled(true);// 设置点击时高亮显示
    lineChart.setMaxHighlightDistance(500);// 设置高亮最大间距[单位dp]默认值500dp
  }

  /**
   * 为某个设置高亮[样式][颜色]
   *
   * @param LineDataSet
   */
  private void setHighlightingOne(LineDataSet LineDataSet) {
    LineDataSet.setHighlightEnabled(true);// 运行高亮
    lineDataSet.setDrawHighlightIndicators(true);// 允许高亮指示符
    lineDataSet.setHighLightColor(Color.GREEN);// 高亮指示符的颜色
  }

}
