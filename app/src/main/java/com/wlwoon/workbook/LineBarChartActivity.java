package com.wlwoon.workbook;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wlwoon.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LineBarChartActivity extends BaseActivity {

    @BindView(R.id.linechart)
    LineChart mLinechart;
    @BindView(R.id.barchart)
    BarChart mBarchart;
    @BindView(R.id.mixinchart)
    CombinedChart mMixinchart;
    private int itemcount=5;
    private IndexAxisValueFormatter mFormatter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_bar_chart;
    }

    @Override
    protected void initData(Bundle savedInstanceState, Bundle extras) {
        mLinechart.setVisibility(View.GONE);
        initLineChart();
//        mBarchart.setVisibility(View.GONE);
        initBarChart();

        initMixinChart();

    }

    private void initMixinChart() {
        mMixinchart.getDescription().setEnabled(false);
        mMixinchart.setPinchZoom(false);

        mMixinchart.setDrawBarShadow(false);
        mMixinchart.setDrawGridBackground(false);
        YAxis rightAxis = mMixinchart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(15);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mMixinchart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(15);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mMixinchart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        mFormatter = new IndexAxisValueFormatter();
//        xAxis.setValueFormatter(mFormatter);
//        xAxis.setYOffset(15);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return ((int)value)+1+"";
            }
        });


        xAxis.setLabelCount(itemcount);

        CombinedData data = new CombinedData();

        LineData lineData = generateDataLine2(1);
//        LineData lineData = generateLineData();
        BarData barData = generateDataBar2(1);
//        BarData barData = generateBarData();

        data.setData(lineData);
        data.setData(barData);

        mMixinchart.setData(data);

        mMixinchart.invalidate();
    }

    private void initLineChart() {
        mLinechart.getDescription().setEnabled(false);
        mLinechart.setDrawGridBackground(false);

        XAxis xAxis = mLinechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = mLinechart.getAxisLeft();
////        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mLinechart.getAxisRight();
////        rightAxis.setTypeface(mTf);
//        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZe

        // set data
        mLinechart.setData(generateDataLine(1));

        // do not forget to refresh the chart
        // mLinechart.invalidate();
        mLinechart.animateX(750);

    }



    private void initBarChart() {

        mBarchart.getDescription().setEnabled(false);
        mBarchart.setDrawGridBackground(false);
        mBarchart.setDrawBarShadow(false);

        XAxis xAxis = mBarchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = mBarchart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarchart.getAxisRight();
//        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        mChartData.setValueTypeface(mTf);

        // set data
        mBarchart.setData(generateDataBar(1));
        mBarchart.setFitBars(true);

        // do not forget to refresh the chart
//        mBarchart.invalidate();
        mBarchart.animateY(700);
    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> values2 = new ArrayList<>();

//        for (int i = 0; i < 12; i++) {
//            values2.add(new Entry(i, values1.get(i).getY() - 30));
//        }
//
//        LineDataSet d2 = new LineDataSet(values2, "New DataSet " + cnt + ", (2)");
//        d2.setLineWidth(2.5f);
//        d2.setCircleRadius(4.5f);
//        d2.setHighLightColor(Color.rgb(244, 117, 117));
//        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
//        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
//        sets.add(d2);

        return new LineData(sets);
    }

    private LineData generateDataLine2(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < datas.length; i++) {
            values1.add(new Entry(i, datas[i]));
//            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> values2 = new ArrayList<>();


        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
//        sets.add(d2);

        return new LineData(sets);
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return Bar data
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < datas.length; i++) {
            entries.add(new BarEntry(i, datas[i]));
//            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    int[] datas={7,9,16,11,5};

    int[] datas2 = {4, 2, 8, 7, 2};

    private BarData generateDataBar2(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int i = 0; i < datas2.length; i++) {
            entries.add(new BarEntry(i, datas2[i]));
            entries2.add(new BarEntry(i+0.3f,datas[i]-datas2[i]));
//            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        List<IBarDataSet> sets = new ArrayList<>();
        // 此处有两个DataSet，所以有两条柱子，BarEntry（）中的x和y分别表示显示的位置和高度
        // x是横坐标，表示位置，y是纵坐标，表示高度
        BarDataSet barDataSet1 = new BarDataSet(entries, "");
        barDataSet1.setValueTextColor(Color.RED); // 值的颜色
        barDataSet1.setValueTextSize(11f); // 值的大小
        barDataSet1.setColor(Color.parseColor("#0066cc")); // 柱子的颜色
//            barDataSet1.setLabel("蔬菜"); // 设置标签之后，图例的内容默认会以设置的标签显示

        sets.add(barDataSet1);

        BarDataSet barDataSet2 = new BarDataSet(entries2, "");
        // 不显示第二根柱子上的值
        barDataSet2.setValueTextColor(Color.BLACK); // 值的颜色
        barDataSet2.setValueTextSize(11f); // 值的大小
        barDataSet2.setDrawValues(true); // 不显示值
        barDataSet2.setColor(Color.parseColor("#E71818"));
        sets.add(barDataSet2);

//        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
//        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        d.setHighLightAlpha(255);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.3f);
        return cd;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();


        for (int index = 0; index < itemcount; index++) {
            entries.add(new Entry(index, getRandom(15, 5)));
        }

        LineDataSet set = new LineDataSet(entries, "Nhiệt Độ");
        set.setColor(Color.parseColor("#b2322a"));

        set.setFillColor(Color.parseColor("#b2322a"));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.parseColor("#b2322a"));


        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setDrawCircles(false);

        d.addDataSet(set);

        return d;
    }

    private BarData generateBarData() {

        BarData d = new BarData();

        ArrayList<BarEntry> entries1 = new ArrayList<>();


        for (int index = 0; index < itemcount; index++) {
            entries1.add(new BarEntry(index, getRandom(300, 100)));
        }


        BarDataSet set1 = new BarDataSet(entries1, "Lượng Mưa");
        set1.setColor(Color.parseColor("#0c94ee"));
        set1.setValueTextColor(Color.parseColor("#0c94ee"));
        set1.setValueTextSize(10f);
        set1.setDrawValues(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set1);

        return d;
    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }


}