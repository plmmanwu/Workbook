package com.wlwoon.workbook;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.wlwoon.workbook.share.ShareInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by wxy on 2021/4/8.
 */

public class LineChartDialog extends Dialog {

    private LineChart mLinechart;
    int days = 1;
    public LineChartDialog(@NonNull Context context) {
        super(context, R.style.DialogAnimFromBottmoTheme);
        this.setCanceledOnTouchOutside(false);
        this.show();
        Window dialogWindow = this.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.DialogAnimFromBottmo);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
    }

    public LineChartDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LineChartDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_line_chart, null, false);
        mLinechart = view.findViewById(R.id.linechart);
        setContentView(view);
        initData();
    }

    private void initData() {
        initLineChart();
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
        mLinechart.setData(null);

        // do not forget to refresh the chart
        mLinechart.invalidate();
        mLinechart.animateX(750);

    }

    void setDatas(List<ShareInfo> shareInfos) {

        mLinechart.setData(generateDataLine(shareInfos));

        // do not forget to refresh the chart
        mLinechart.invalidate();

    }

    private LineData generateDataLine(List<ShareInfo> infos) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < infos.size(); i++) {
            values1.add(new Entry(i, ((float) infos.get(i).getSharePercent())));
        }

        if (mLinechart.getData() != null &&
                mLinechart.getData().getDataSetCount() > 0) {
            LineDataSet set1 = (LineDataSet) mLinechart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            mLinechart.getData().notifyDataChanged();
            mLinechart.notifyDataSetChanged();
        }
        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        return new LineData(sets);
    }

    public void setData(List<ShareInfo> shareInfos) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < shareInfos.size(); i++) {
            values.add(new Entry(i, ((float) shareInfos.get(i).getSharePercent())));
        }

        LineDataSet set1;

        if (mLinechart.getData() != null &&
                mLinechart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLinechart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            mLinechart.getData().notifyDataChanged();
            mLinechart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return mLinechart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            } else {
//            }
            set1.setFillColor(Color.BLACK);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            mLinechart.setData(data);
        }
    }
}
