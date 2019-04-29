package com.android.papp.libraries.mpchart.custom;


import android.content.Context;

import com.android.papp.libraries.mpchart.components.AxisBase;
import com.android.papp.libraries.mpchart.formatter.IAxisValueFormatter;

import com.android.papp.R;

public class CustomStringFormatter implements IAxisValueFormatter {


    private final Context mContext;

    public CustomStringFormatter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int v = (int) value;
        if (mContext.getResources().getStringArray(R.array.stats_xAxis).length > v) {
            return mContext.getResources().getStringArray(R.array.stats_xAxis)[v];
        } else {
            return "Unknown";
        }
    }
}