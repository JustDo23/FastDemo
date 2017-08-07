package com.just.fast.module.chart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.just.fast.R;
import com.just.utils.log.LogUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 使用[MPAndroidChart]进行图表绘制[Setting Data]
 *
 * @author JustDo23
 * @since 2017年07月27日
 */
public class ChartListActivity extends AppCompatActivity {

  private LineChart chart_line;// 线性表
  private BarChart chart_bar;// 柱状图
  private ScatterChart chart_scatter;// 分散图
  private BubbleChart chart_bubble;// 气泡图
  private CandleStickChart chart_candle_stick;// 烛台[炒股]
  private PieChart chart_pie;// 烛台[炒股]

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart_list);
    // 线性表
    chart_line = (LineChart) findViewById(R.id.chart_line);
    setData4LineChart(chart_line);
    setXAxisCustom(chart_line);
    // 柱形图
    chart_bar = (BarChart) findViewById(R.id.chart_bar);
    // setData4BarChart(chart_bar);
    setData4BarChartTwo(chart_bar);
    // 散点图
    chart_scatter = (ScatterChart) findViewById(R.id.chart_scatter);
    setData4ScatterChart(chart_scatter);
    // 饼图
    chart_pie = (PieChart) findViewById(R.id.chart_pie);
    setData4PieChart(chart_pie);

    chart_bubble = (BubbleChart) findViewById(R.id.chart_bubble);
    chart_bubble.setVisibility(View.GONE);
    chart_candle_stick = (CandleStickChart) findViewById(R.id.chart_candle_stick);
    chart_candle_stick.setVisibility(View.GONE);

    chart_line.setVisibility(View.VISIBLE);
    chart_bar.setVisibility(View.GONE);
    chart_scatter.setVisibility(View.GONE);
    chart_pie.setVisibility(View.GONE);
  }


  /**
   * 为线性表设置数据
   */
  private void setData4LineChart(LineChart linechart) {
    Random random = new Random();

    List<Entry> entryListDove = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      entryListDove.add(new Entry(i * 4 + 1, random.nextInt(230), "D" + i));
    }
    LineDataSet lineDataSetDove = new LineDataSet(entryListDove, "Dove");// 单一构造方法[参数List<Entry>][参数label字符串]
    lineDataSetDove.setAxisDependency(YAxis.AxisDependency.LEFT);// 轴

    List<Entry> entryListAlps = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      entryListAlps.add(new Entry(i * 4 + 1, random.nextInt(230) * -1, "A" + i));
    }
    LineDataSet lineDataSetAlps = new LineDataSet(entryListAlps, "Alps");
    lineDataSetAlps.setAxisDependency(YAxis.AxisDependency.LEFT);// 轴

    setChartColors(lineDataSetDove, lineDataSetAlps);// 设置颜色
    setValueFormatter(lineDataSetDove);// 设置值格式

    List<ILineDataSet> lineDataSetList = new ArrayList<>();
    lineDataSetList.add(lineDataSetDove);
    lineDataSetList.add(lineDataSetAlps);
    LineData lineData = new LineData(lineDataSetList);// 构造方法多个
    linechart.setData(lineData);// 给控件设置数据[参数ChartData]
    linechart.invalidate();// 控件刷新
    setGeneralStyle(linechart);// 设置一般样式
  }

  /**
   * 自定义 X 轴描述
   */
  private void setXAxisCustom(LineChart linechart) {
    IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {

      @Override
      public String getFormattedValue(float value, AxisBase axis) {
        LogUtils.e("value = " + value);
        return "X" + value;
      }
    };
    XAxis xAxis = linechart.getXAxis();// X 轴
    xAxis.setGranularity(1.0f);// 最小间距
    xAxis.setValueFormatter(iAxisValueFormatter);// 修改 X 轴的信息
  }

  /**
   * 自定义值格式
   *
   * @param lineDataSet
   */
  private void setValueFormatter(LineDataSet lineDataSet) {
    IValueFormatter iValueFormatter = new IValueFormatter() {

      private DecimalFormat mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal

      @Override
      public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value) + " $";
      }
    };
    lineDataSet.setValueFormatter(iValueFormatter);
  }

  /**
   * 线性表注意排序
   */
  private void noteSort() {
    List<Entry> entries = new ArrayList<>();
    Collections.sort(entries, new EntryXComparator());
  }

  /**
   * 设置一般样式
   */
  private void setGeneralStyle(LineChart linechart) {
    linechart.setBackgroundColor(Color.parseColor("#FFE4C4"));// 设置图表背景
    Description description = new Description();// 图表的描述
    description.setEnabled(true);
    description.setTextColor(Color.parseColor("#FF0000"));// 描述的字体颜色
    description.setText("线性表");// 配置描述信息
    description.setPosition(350, 350);// 描述的位置
    description.setTextSize(12);// 描述的字体大小
    linechart.setDescription(description);// 设置图表的描述

    linechart.setNoDataText("Sorry. No Data.");// 数据为空是的显示

    linechart.setDrawGridBackground(true);// 允许网格背景
    linechart.setGridBackgroundColor(Color.parseColor("#F0FFF0"));// 设置网格背景

    linechart.setDrawBorders(true);// 允许绘制边境
    linechart.setBorderColor(Color.parseColor("#836FFF"));// 边境颜色
    linechart.setBorderWidth(6.0f);// 边境宽度

    linechart.setMaxVisibleValueCount(100);// 不觉明历
  }


  /**
   * 为柱状图设置数据
   *
   * @param chart_bar 柱状图
   */
  private void setData4BarChart(BarChart chart_bar) {
    Random random = new Random();

    List<BarEntry> barEntryListDove = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      barEntryListDove.add(new BarEntry(i * 4 + 1, random.nextInt(120) + 6, "D" + i));
    }
    BarDataSet barDataSetDove = new BarDataSet(barEntryListDove, "Dove");

    List<BarEntry> barEntryListAlps = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      barEntryListAlps.add(new BarEntry(i * 4 + 3, (random.nextInt(120) + 6) * -1, "A" + i));
    }
    BarDataSet barDataSetAlps = new BarDataSet(barEntryListAlps, "Alps");

    BarData barData = new BarData();// 构造方法多个
    barData.addDataSet(barDataSetDove);
    barData.addDataSet(barDataSetAlps);// 居然没有显示
    chart_bar.setData(barData);// 给控件设置数据[参数ChartData]
    chart_bar.invalidate();// 控件刷新
  }

  /**
   * 为柱状图设置数据
   *
   * @param chart_bar 柱状图
   */
  private void setData4BarChartTwo(BarChart chart_bar) {
    Random random = new Random();

    List<BarEntry> barEntryListDove = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      barEntryListDove.add(new BarEntry(i * 4 + 1, random.nextInt(120) + 6, "D" + i));
    }
    BarDataSet barDataSetDove = new BarDataSet(barEntryListDove, "Dove");

    List<BarEntry> barEntryListAlps = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      barEntryListAlps.add(new BarEntry(i * 4 + 1, (random.nextInt(120) + 6), "A" + i));
    }
    BarDataSet barDataSetAlps = new BarDataSet(barEntryListAlps, "Alps");


    float groupSpace = 1.6f;
    float barSpace = 0.2f; // x2 dataset
    float barWidth = 0.5f; // x2 dataset

    BarData barData = new BarData();// 构造方法多个
    barData.addDataSet(barDataSetDove);
    barData.addDataSet(barDataSetAlps);// 居然没有显示
    barData.setBarWidth(barWidth);// 设置柱子的宽度
    chart_bar.setData(barData);// 给控件设置数据[参数ChartData]
    chart_bar.groupBars(0.0f, groupSpace, barSpace); // perform the "explicit" grouping
    chart_bar.setFitBars(true);// 设置X轴精确绘制柱子
    chart_bar.invalidate();// 控件刷新

    XAxis xAxis = chart_bar.getXAxis();// 获取 X 轴
    xAxis.setCenterAxisLabels(true);// 使 X 轴的标签居中
  }


  /**
   * 散图
   *
   * @param scatterChart
   */
  private void setData4ScatterChart(ScatterChart scatterChart) {
    List<Entry> entryList = new ArrayList<>();
    entryList.add(new Entry(10, 20));
    entryList.add(new Entry(20, 30));
    entryList.add(new Entry(30, 40));
    entryList.add(new Entry(40, 50));
    entryList.add(new Entry(50, 60));
    entryList.add(new Entry(60, 70));
    entryList.add(new Entry(70, 80));
    ScatterDataSet scatterDataSet = new ScatterDataSet(entryList, "Dove");
    ScatterData scatterData = new ScatterData(scatterDataSet);
    scatterChart.setData(scatterData);
    scatterChart.invalidate();
  }


  /**
   * 饼图设置数据
   *
   * @param pieChart
   */
  private void setData4PieChart(PieChart pieChart) {
    List<PieEntry> pieEntryList = new ArrayList<>();
    pieEntryList.add(new PieEntry(18.5f, "Green"));
    pieEntryList.add(new PieEntry(26.7f, "Yellow"));
    pieEntryList.add(new PieEntry(24.0f, "Red"));
    pieEntryList.add(new PieEntry(30.8f, "Blue"));
    PieDataSet pieDataSet = new PieDataSet(pieEntryList, "Election Results");
    PieData pieData = new PieData(pieDataSet);
    pieChart.setData(pieData);
    pieChart.invalidate();
  }


  /**
   * 设置颜色
   */
  private void setChartColors(LineDataSet dataSetDove, LineDataSet dataSetAlps) {
    dataSetDove.setColors(ColorTemplate.VORDIPLOM_COLORS);
    dataSetDove.setColors(new int[]{R.color.Plum, R.color.Orchid, R.color.MediumOrchid, R.color.DarkOrchid}, this);
    dataSetAlps.setColors(new int[]{}, this);
    dataSetAlps.getColors().add(Color.parseColor("#FF00FF"));
    dataSetAlps.getColors().add(Color.parseColor("#FF4040"));
    dataSetAlps.getColors().add(Color.parseColor("#FF1493"));
    dataSetAlps.getColors().add(Color.parseColor("#FF7F24"));
  }


}
