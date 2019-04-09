package com.uplan.miyao.ui.financial.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.uplan.miyao.R;
import com.uplan.miyao.widget.mpchart.DetailsMarkerView;
import com.uplan.miyao.widget.mpchart.PositionMarker;
import com.uplan.miyao.widget.mpchart.RoundMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FinancialActivity extends AppCompatActivity {
    MyLineChart mLineChart;
    public static void start(Context context) {
        Intent starter = new Intent(context, FinancialActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial);
        mLineChart = (MyLineChart) findViewById(R.id.chart);

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();
                for (ILineDataSet set : dataSets)
                    set.setVisible(!set.isVisible());
                mLineChart.animateXY(500, 500);
                mLineChart.invalidate();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1,准备要更换的数据
                List<Entry> entries = new ArrayList<>();
                for (int i = 0; i < 12; i++)
                    entries.add(new Entry(i, new Random().nextInt(300)));

                //2. 获取LineDataSet线条数据集
                List<ILineDataSet> dataSets = mLineChart.getLineData().getDataSets();

                //是否存在
                if (dataSets != null && dataSets.size() > 0) {
                    //直接更换数据源
                    for (ILineDataSet set : dataSets) {
                        LineDataSet data = (LineDataSet) set;
                        data.setValues(entries);
                    }
                } else {
                    //重新生成LineDataSet线条数据集
                    LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                    dataSet.setDrawCircles(false);
                    dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
                    dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
                    dataSet.setLineWidth(1f);//线条宽度
                    LineData lineData = new LineData(dataSet);
                    //是否绘制线条上的文字
                    lineData.setDrawValues(false);
                    mLineChart.setData(lineData);
                }
                //更新
                mLineChart.invalidate();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空数据
                mLineChart.getLineData().clearValues();
                mLineChart.highlightValues(null);
                mLineChart.invalidate();
            }
        });


        findViewById(R.id.btn_slide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float scaleX = mLineChart.getScaleX();
                if (scaleX == 1)
                    mLineChart.zoomToCenter(5, 1f);
                else {
                    BarLineChartTouchListener barLineChartTouchListener = (BarLineChartTouchListener) mLineChart.getOnTouchListener();
                    barLineChartTouchListener.stopDeceleration();
                    mLineChart.fitScreen();
                }

                mLineChart.invalidate();
            }
        });

        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            entries.add(new Entry(i, new Random().nextInt(300)));

        for (int i = 0; i < 12; i++)
            entries2.add(new Entry(i, new Random().nextInt(600)));



        //2.把数据赋值到你的线条
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        //2.把数据赋值到你的线条
        LineDataSet dataSet2 = new LineDataSet(entries2, "Label"); // add entries to dataset
        dataSet2.setDrawCircles(false);
        dataSet2.setColor(Color.parseColor("#7d7d7d"));//线条颜色
        dataSet2.setCircleColor(Color.parseColor("#7d7d7d"));//圆点颜色
        dataSet2.setLineWidth(1f);//线条宽度


        mLineChart.setScaleEnabled(false);

        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表左边的y轴禁用
        leftAxis.setEnabled(false);
        rightAxis.setAxisMaximum(dataSet.getYMax() * 2);
        leftAxis.setAxisMaximum(dataSet.getYMax() * 2);
        //设置x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.parseColor("#333333"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularity(1f);//禁止放大x轴标签重绘
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(String.valueOf(i + 1).concat("月"));
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(list));

        //透明化图例
        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextColor(Color.WHITE);
        //legend.setYOffset(-2);

        //点击图表坐标监听
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //查看覆盖物是否被回收
                if (mLineChart.isMarkerAllNull()) {
                    //重新绑定覆盖物
                    createMakerView();
                    //并且手动高亮覆盖物
                    mLineChart.highlightValue(h);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //隐藏x轴描述
        Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);

        //创建覆盖物
        createMakerView();

        //3.chart设置数据

        List<ILineDataSet> lineDataSets=new ArrayList<>();
        lineDataSets.add(dataSet);
        lineDataSets.add(dataSet2);

       // LineData lineData = new LineData(dataSet);
        LineData lineData = new LineData(lineDataSets);
        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh


        initPieChart();
    }



    /**
     * 创建覆盖物
     */
    public void createMakerView() {
        DetailsMarkerView detailsMarkerView = new DetailsMarkerView(this);
        detailsMarkerView.setChartView(mLineChart);
        mLineChart.setDetailsMarkerView(detailsMarkerView);
        mLineChart.setPositionMarker(new PositionMarker(this));
        mLineChart.setRoundMarker(new RoundMarker(this));
    }


    private void initPieChart(){
        PieChart pieChart= (PieChart) findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(false);//这货，是否使用百分比显示，但是我还是没操作出来。
        Description description = pieChart.getDescription();
        description.setText("Assets View"); //设置描述的文字
        pieChart.setHighlightPerTapEnabled(true); //设置piecahrt图表点击Item高亮是否可用
        pieChart.animateX(2000);
        initPieChartData(pieChart);

        pieChart.setDrawEntryLabels(true); // 设置entry中的描述label是否画进饼状图中
        pieChart.setEntryLabelColor(Color.GRAY);//设置该文字是的颜色
        pieChart.setEntryLabelTextSize(10f);//设置该文字的字体大小

        pieChart.setDrawHoleEnabled(true);//设置圆孔的显隐，也就是内圆
        pieChart.setHoleRadius(28f);//设置内圆的半径。外圆的半径好像是不能设置的，改变控件的宽度和高度，半径会自适应。
        pieChart.setHoleColor(Color.WHITE);//设置内圆的颜色
        pieChart.setDrawCenterText(true);//设置是否显示文字
        pieChart.setCenterText("Test");//设置饼状图中心的文字
        pieChart.setCenterTextSize(10f);//设置文字的消息
        pieChart.setCenterTextColor(Color.RED);//设置文字的颜色
        pieChart.setTransparentCircleRadius(31f);//设置内圆和外圆的一个交叉园的半径，这样会凸显内外部的空间
        pieChart.setTransparentCircleColor(Color.BLACK);//设置透明圆的颜色
        pieChart.setTransparentCircleAlpha(50);//设置透明圆你的透明度



        Legend legend = pieChart.getLegend();//图例
        legend.setEnabled(true);//是否显示
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//对齐
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//对齐
        legend.setForm(Legend.LegendForm.DEFAULT);//设置图例的图形样式,默认为圆形
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的排列走向:vertacal相当于分行
        legend.setFormSize(6f);//设置图例的大小
        legend.setTextSize(8f);//设置图注的字体大小
        legend.setFormToTextSpace(4f);//设置图例到图注的距离

        legend.setDrawInside(true);//不是很清楚
        legend.setWordWrapEnabled(false);//设置图列换行(注意使用影响性能,仅适用legend位于图表下面)，我也不知道怎么用的
        legend.setTextColor(Color.BLACK);

    }

    private void initPieChartData(PieChart pieChart){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(70f, "cash banlance : 1500"));
        pieEntries.add(new PieEntry(30f, "consumption banlance : 500"));
        pieEntries.add(new PieEntry(30f, "else : 500"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, null);
        pieDataSet.setColors(Color.parseColor("#f17548"), Color.parseColor("#FF9933"), Color.YELLOW);
        pieDataSet.setSliceSpace(3f);//设置每块饼之间的空隙
        pieDataSet.setSelectionShift(10f);//点击某个饼时拉长的宽度

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);

        pieChart.setData(pieData);
        pieChart.invalidate();

    }

}
