package com.wlwoon.workbook.chart

import android.content.Context
import android.util.AttributeSet
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.YAxis

/**
 * A combined chart that respects the bar widths when calculating axis widths
 */
class CustomCombinedChart : CombinedChart {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var fitBars: Boolean = true

    override fun calcMinMax() {

        if (fitBars && mData.barData != null) {
            mXAxis.calculate(mData.xMin - mData.barData.barWidth / 2f, mData.xMax + mData.barData.barWidth / 2f)
        } else {
            mXAxis.calculate(mData.xMin, mData.xMax)
        }

        // calculate axis range (min / max) according to provided data
        mAxisLeft.calculate(mData.getYMin(YAxis.AxisDependency.LEFT), mData.getYMax(YAxis.AxisDependency.LEFT))
        mAxisRight.calculate(mData.getYMin(YAxis.AxisDependency.RIGHT), mData.getYMax(YAxis.AxisDependency.RIGHT))
    }
}