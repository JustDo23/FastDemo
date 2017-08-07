package com.just.fast.module.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
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
    setHighlightValue(lc_learn, lineDataSet);// 设置突显值
    setSelectedListener(lc_learn, lineDataSet);// 设置选中突显值后的监听回调
    setAxis(lc_learn);// 设置轴信息
    setYAxis(lc_learn);// 设置轴信息
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

  /**
   * 设置突显[不明]
   *
   * @param lineChart
   */
  private void setHighlightValue(LineChart lineChart, LineDataSet lineDataSet) {
    Entry entry = lineDataSet.getEntryForIndex(3);
    lineChart.highlightValue(entry.getX(), -1, true);// 突显[x坐标][-1代表所有][是否允许监听回调]

    Entry entry6 = lineDataSet.getEntryForIndex(6);
    Highlight highlight = new Highlight(entry6.getX(), entry6.getY(), 6);
    lineChart.highlightValue(highlight, true);// 突显[null代表所有][是否允许监听回调] OnChartValueSelectedListener

    Highlight[] highlightArray = new Highlight[1];
    highlightArray[0] = new Highlight(lineDataSet.getEntryForIndex(1).getX(), lineDataSet.getEntryForIndex(1).getY(), 1);
    lineChart.highlightValues(highlightArray);// 突显 提供数组

    Highlight[] highlighted = lineChart.getHighlighted();// 返回数组

    // lineChart.setHighlighter();// 自定义高亮
  }

  /**
   * 设置突显值被选中的回调
   *
   * @param lineChart
   */
  private void setSelectedListener(LineChart lineChart, final LineDataSet lineDataSet) {
    lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

      /**
       * 高亮值选中回调
       * @param e
       * @param h
       */
      @Override
      public void onValueSelected(Entry e, Highlight h) {
        LogUtils.e("OnChartValueSelectedListener --> onValueSelected() --> " + lineDataSet.getEntryIndex(e));
      }

      /**
       * 没有选中高亮值回调
       */
      @Override
      public void onNothingSelected() {
        LogUtils.e("OnChartValueSelectedListener --> onNothingSelected()");
      }
    });
  }

  /**
   * 设置轴信息
   *
   * @param lineChart
   */
  private void setAxis(LineChart lineChart) {
    XAxis xAxis = lineChart.getXAxis();// X 轴
    xAxis.setEnabled(true);// 是否允许轴显示
    xAxis.setDrawLabels(true);// 是否允许轴标签轴描述显示
    xAxis.setDrawAxisLine(true);// 是否允许轴线显示
    xAxis.setDrawGridLines(true);// 是否允许网格中轴线垂直线显示


    xAxis.setAxisMaximum(150.0f);// 设置该轴的最大值[轴的终止值]设置后不会自动计算
    xAxis.resetAxisMaximum();// 重置该轴的最大值重置后可自动计算
    xAxis.setAxisMinimum(40.0f);// 设置该轴的最小值[轴的起始值]设置后不会自动计算
    xAxis.resetAxisMinimum();// 重置该轴的最小值重置后可自动计算
    // xAxis.setInverted(false);// [倒立]没有该方法
    xAxis.setSpaceMax(0.0f);// 轴最大值继续扩大的距离[轴的终止值]
    xAxis.setSpaceMin(0.0f);// 轴最小值继续缩小的距离[轴的起始值]
    // xAxis.setSpaceTop(0.0f);
    // xAxis.setSpaceBottom(0.0f);
    // xAxis.setShowOnlyMinMax(true);// 只显示轴的最大和最小值


    xAxis.setLabelCount(8, false);// 设置轴上的标签个数[个数][是否强制等于个数]
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 设置轴位置 [上边][下边][两边][上边内部][下边内部]
    xAxis.setGranularityEnabled(false);// 放大时轴标签限制特性 是否允许[默认false]
    xAxis.setGranularity(0.5f);// 放大时间距


    xAxis.setTextColor(Color.BLUE);// 设置轴标签字体颜色
    xAxis.setTextSize(12.0f);// 设置轴标签字体大小[单位dp]
    // xAxis.setTypeface();// 设置轴标签的字体
    xAxis.setGridColor(Color.GRAY);// 设置网格中轴线垂直线颜色
    xAxis.setGridLineWidth(2.0f);// 设置网格中轴线垂直线宽度[单位dp]
    xAxis.setAxisLineColor(Color.parseColor("#00FFFF"));// 设置轴线颜色
    xAxis.setAxisLineWidth(2.0f);// 设置轴线宽度[单位dp]
    xAxis.enableGridDashedLine(12.0f, 12.0f, 0.0f);// 设置网格中轴线垂直线为虚线模式[线条长度][线条间距][偏移量][单位px]


    LimitLine limitLine = new LimitLine(20.0f, "限制线");
    limitLine.setLineColor(Color.parseColor("#FFB90F"));// 限制线颜色
    limitLine.setLineWidth(5.0f);// 限制线的线条宽度
    limitLine.setTextColor(Color.parseColor("#8B658B"));// 限制线的字体颜色
    limitLine.setTextSize(16.0f);// 限制线的字体大小
    xAxis.addLimitLine(limitLine);// 添加限制线
    xAxis.setDrawLimitLinesBehindData(false);// [true,设置显示限制线在数据后]默认false
    xAxis.removeLimitLine(limitLine);// 移除限制线


    xAxis.setLabelRotationAngle(90.0f);// 设置轴标签文字的旋转角度


    YAxis leftAxis = lineChart.getAxisLeft();// 左边Y轴
    LimitLine ll = new LimitLine(10f, "Critical Blood Pressure");
    ll.setLineColor(Color.RED);// 颜色
    ll.setLineWidth(4f);// 线条宽度
    ll.setTextColor(Color.BLACK);// 字体颜色
    ll.setTextSize(12f);// 字体大小
    leftAxis.addLimitLine(ll);
    leftAxis.removeLimitLine(ll);


    YAxisRenderer rendererLeftYAxis = lineChart.getRendererLeftYAxis();
    YAxisRenderer rendererRightYAxis = lineChart.getRendererRightYAxis();
  }

  /**
   * 设置轴信息
   *
   * @param lineChart
   */
  private void setYAxis(LineChart lineChart) {
    YAxis axisLeft = lineChart.getAxisLeft();// 左边Y轴
    YAxis axisRight = lineChart.getAxisRight();// 右边Y轴
    YAxis axisYLeft = lineChart.getAxis(YAxis.AxisDependency.LEFT);// 左边Y轴
    YAxis axisYRight = lineChart.getAxis(YAxis.AxisDependency.RIGHT);// 右边Y轴

    axisRight.setEnabled(false);

    // 柱状图效果明显的零线
    axisLeft.setDrawZeroLine(true);// 绘制零线[默认false]
    axisLeft.setZeroLineColor(Color.parseColor("#FF6A6A"));// 设置零线颜色
    axisLeft.setZeroLineWidth(12.0f);// 设置零线宽度[单位dp]
  }
}
