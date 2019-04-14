package com.uplan.miyao.ui.financial.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.uplan.miyao.R;
import com.uplan.miyao.ui.financial.view.MyLineChart;
import com.uplan.miyao.widget.mpchart.DetailsMarkerView;
import com.uplan.miyao.widget.mpchart.PositionMarker;
import com.uplan.miyao.widget.mpchart.RoundMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinancialWebActivity2 extends AppCompatActivity {
    MyLineChart mLineChart;
    @BindView(R.id.ll_recent_1)
    LinearLayout llRecent1;
    @BindView(R.id.ll_recent_2)
    LinearLayout llRecent2;
    @BindView(R.id.ll_recent_3)
    LinearLayout llRecent3;
    @BindView(R.id.ll_recent_4)
    LinearLayout llRecent4;
    @BindView(R.id.ll_recent_5)
    LinearLayout llRecent5;
    @BindView(R.id.underline_1)
    TextView underline1;
    @BindView(R.id.underline_2)
    TextView underline2;
    @BindView(R.id.underline_3)
    TextView underline3;
    @BindView(R.id.underline_4)
    TextView underline4;
    @BindView(R.id.underline_5)
    TextView underline5;

    private List<String> xlist = new ArrayList<>();
    private List<Entry> zuhelist = new ArrayList<>();
    private List<Entry> hushenlist = new ArrayList<>();
    private List<Entry> zhongzhenglist = new ArrayList<>();

    /** 成立以来的天数 */
    private int max = 30 * 24;

    public static void start(Context context) {
        Intent starter = new Intent(context, FinancialWebActivity2.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_financial);
        ButterKnife.bind(this);
        mLineChart = (MyLineChart) findViewById(R.id.chart);
        initLineChart();
        initPieChart();
    }

    private void initLineChart() {
        //1.设置x轴和y轴的点
        onClick(llRecent1);
        //2.把数据赋值到你的线条

        LineDataSet dataSet = (LineDataSet) dataAssignmentZuhe();
        LineDataSet dataSet2 = (LineDataSet) dataAssignmentHushen();
        LineDataSet dataSet3 = (LineDataSet) dataAssignmentZhongzheng();

        mLineChart.setScaleEnabled(false);

        //mLineChart.getLineData().getDataSets().get(0).setVisible(true);
        //设置样式
        YAxis rightAxis = mLineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
      //  YAxis leftAxis = mLineChart.getAxisLeft();
        //设置图表左边的y轴禁用
       // leftAxis.setEnabled(false);
        rightAxis.setAxisMaximum(dataSet.getYMax() * 2);
       // leftAxis.setAxisMaximum(dataSet.getYMax() * 2);
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

        List<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(dataSet);
        lineDataSets.add(dataSet2);
        lineDataSets.add(dataSet3);

        // LineData lineData = new LineData(dataSet);
        LineData lineData = new LineData(lineDataSets);
        //是否绘制线条上的文字
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.invalidate(); // refresh


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

    private ILineDataSet dataAssignmentZuhe() {
        LineDataSet dataSet = new LineDataSet(zuhelist, "Label"); // add entries to dataset
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.parseColor("#2492D6"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#2492D6"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        return dataSet;
    }

    private ILineDataSet dataAssignmentHushen() {
        LineDataSet dataSet2 = new LineDataSet(hushenlist, "Label"); // add entries to dataset
        dataSet2.setDrawCircles(false);
        dataSet2.setColor(Color.parseColor("#B9B9B9"));//线条颜色
        dataSet2.setCircleColor(Color.parseColor("#B9B9B9"));//圆点颜色
        dataSet2.setLineWidth(1f);//线条宽度
        return dataSet2;
    }

    private ILineDataSet dataAssignmentZhongzheng() {
        LineDataSet dataSet3 = new LineDataSet(zhongzhenglist, "Label"); // add entries to dataset
        dataSet3.setDrawCircles(false);
        dataSet3.setColor(Color.parseColor("#D4E383"));//线条颜色
        dataSet3.setCircleColor(Color.parseColor("#D4E383"));//圆点颜色
        dataSet3.setLineWidth(1f);//线条宽度
        return dataSet3;
    }


    private void initPieChart() {
        PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
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

    private void initPieChartData(PieChart pieChart) {
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

    @OnClick({R.id.ll_recent_1, R.id.ll_recent_2, R.id.ll_recent_3, R.id.ll_recent_4, R.id.ll_recent_5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_recent_1:
                clearList();
                invisibleUnderLine();
                underline1.setVisibility(View.VISIBLE);
                for (int i = 0; i < 30; i++) {
                    xlist.add(" ");
                }
                for (int i = 0; i < 30; i++)
                    zuhelist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30; i++)
                    hushenlist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30; i++)
                    zhongzhenglist.add(new Entry(i, new Random().nextInt(300)));


                LineDataSet dataSet1 = (LineDataSet) dataAssignmentZuhe();
                LineDataSet dataSet1_2 = (LineDataSet) dataAssignmentHushen();
                LineDataSet dataSet1_3 = (LineDataSet) dataAssignmentZhongzheng();

                List<ILineDataSet> lineDataSets = new ArrayList<>();
                lineDataSets.add(dataSet1);
                lineDataSets.add(dataSet1_2);
                lineDataSets.add(dataSet1_3);
                LineData lineData = new LineData(lineDataSets);
                mLineChart.setData(lineData);
                mLineChart.invalidate(); // refresh
                break;
            case R.id.ll_recent_2:
                clearList();
                invisibleUnderLine();
                underline2.setVisibility(View.VISIBLE);
                for (int i = 0; i < 30 * 3; i++) {
                    xlist.add(" ");
                }
                for (int i = 0; i < 30 * 3; i++)
                    zuhelist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 3; i++)
                    hushenlist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 3; i++)
                    zhongzhenglist.add(new Entry(i, new Random().nextInt(300)));

                LineDataSet dataSet2 = (LineDataSet) dataAssignmentZuhe();
                LineDataSet dataSet2_2 = (LineDataSet) dataAssignmentHushen();
                LineDataSet dataSet2_3 = (LineDataSet) dataAssignmentZhongzheng();

                List<ILineDataSet> lineDataSets2 = new ArrayList<>();
                lineDataSets2.add(dataSet2);
                lineDataSets2.add(dataSet2_2);
                lineDataSets2.add(dataSet2_3);
                LineData lineData2 = new LineData(lineDataSets2);
                mLineChart.setData(lineData2);
                mLineChart.invalidate(); // refresh
                break;
            case R.id.ll_recent_3:
                clearList();
                invisibleUnderLine();
                underline3.setVisibility(View.VISIBLE);
                for (int i = 0; i < 30 * 6; i++) {
                    xlist.add(" ");
                }
                for (int i = 0; i < 30 * 6; i++)
                    zuhelist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 6; i++)
                    hushenlist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 6; i++)
                    zhongzhenglist.add(new Entry(i, new Random().nextInt(300)));

                LineDataSet dataSet3 = (LineDataSet) dataAssignmentZuhe();
                LineDataSet dataSet3_2 = (LineDataSet) dataAssignmentHushen();
                LineDataSet dataSet3_3 = (LineDataSet) dataAssignmentZhongzheng();

                List<ILineDataSet> lineDataSets3 = new ArrayList<>();
                lineDataSets3.add(dataSet3);
                lineDataSets3.add(dataSet3_2);
                lineDataSets3.add(dataSet3_3);
                LineData lineData3 = new LineData(lineDataSets3);
                mLineChart.setData(lineData3);
                mLineChart.invalidate(); // refresh
                break;
            case R.id.ll_recent_4:
                clearList();
                invisibleUnderLine();
                underline4.setVisibility(View.VISIBLE);
                for (int i = 0; i < 30 * 12; i++) {
                    xlist.add(" ");
                }
                for (int i = 0; i < 30 * 12; i++)
                    zuhelist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 12; i++)
                    hushenlist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < 30 * 12; i++)
                    zhongzhenglist.add(new Entry(i, new Random().nextInt(300)));


                LineDataSet dataSet4 = (LineDataSet) dataAssignmentZuhe();
                LineDataSet dataSet4_2 = (LineDataSet) dataAssignmentHushen();
                LineDataSet dataSet4_3 = (LineDataSet) dataAssignmentZhongzheng();

                List<ILineDataSet> lineDataSets4 = new ArrayList<>();
                lineDataSets4.add(dataSet4);
                lineDataSets4.add(dataSet4_2);
                lineDataSets4.add(dataSet4_3);
                LineData lineData4 = new LineData(lineDataSets4);
                mLineChart.setData(lineData4);
                mLineChart.invalidate(); // refresh
                break;
            case R.id.ll_recent_5:
                clearList();
                invisibleUnderLine();
                underline5.setVisibility(View.VISIBLE);
                for (int i = 0; i < max; i++) {
                    xlist.add(" ");
                }
                for (int i = 0; i < max; i++)
                    zuhelist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < max; i++)
                    hushenlist.add(new Entry(i, new Random().nextInt(300)));
                for (int i = 0; i < max; i++)
                    zhongzhenglist.add(new Entry(i, new Random().nextInt(300)));


                LineDataSet dataSet5 = (LineDataSet) dataAssignmentZuhe();
                LineDataSet dataSet5_2 = (LineDataSet) dataAssignmentHushen();
                LineDataSet dataSet5_3 = (LineDataSet) dataAssignmentZhongzheng();

                List<ILineDataSet> lineDataSets5 = new ArrayList<>();
                lineDataSets5.add(dataSet5);
                lineDataSets5.add(dataSet5_2);
                lineDataSets5.add(dataSet5_3);
                LineData lineData5 = new LineData(lineDataSets5);
                mLineChart.setData(lineData5);
                mLineChart.invalidate(); // refresh
                break;
        }
    }

    private void clearList() {
        xlist.clear();
        zuhelist.clear();
        hushenlist.clear();
        zhongzhenglist.clear();

    }

    private void invisibleUnderLine() {
        underline1.setVisibility(View.INVISIBLE);
        underline2.setVisibility(View.INVISIBLE);
        underline3.setVisibility(View.INVISIBLE);
        underline4.setVisibility(View.INVISIBLE);
        underline5.setVisibility(View.INVISIBLE);
    }
}
