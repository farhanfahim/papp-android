package com.android.papp.libraries.mpchart.custom;


import android.content.Context;

import com.android.papp.libraries.mpchart.components.AxisBase;
import com.android.papp.libraries.mpchart.formatter.IAxisValueFormatter;
import com.android.papp.models.TupleModel;

public class DivisionStringFormatter implements IAxisValueFormatter {


    private final Context mContext;
    private final TupleModel statsModel;

    public DivisionStringFormatter(Context mContext, TupleModel statsModel) {
        this.mContext = mContext;
        this.statsModel = statsModel;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int v = (int) value;
//        if (v == 0) {
//            return mContext.getResources().getStringArray(R.array.stats_xAxis)[v];
//        } else if (statsModel.getListDivisionCount().size() >= v) {
//            return statsModel.getListDivisionCount().get(v - 1).getDivisionName();
//        } else {
            return "Unknown";
//        }
    }
}