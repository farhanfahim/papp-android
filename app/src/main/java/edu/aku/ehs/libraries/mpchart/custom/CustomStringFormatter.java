package edu.aku.ehs.libraries.mpchart.custom;


import android.content.Context;

import edu.aku.ehs.R;
import edu.aku.ehs.libraries.mpchart.components.AxisBase;
import edu.aku.ehs.libraries.mpchart.formatter.IAxisValueFormatter;

public class CustomStringFormatter implements IAxisValueFormatter {


    private final Context mContext;

    public CustomStringFormatter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int v = (int) value;
        if (mContext.getResources().getStringArray(R.array.stats_xAxis).length >= v) {
            return mContext.getResources().getStringArray(R.array.stats_xAxis)[v - 1];
        } else {
            return "Unknown";
        }
    }
}