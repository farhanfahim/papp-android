package edu.aku.ehs.libraries.mpchart.custom;


import java.text.DecimalFormat;

import edu.aku.ehs.libraries.mpchart.components.AxisBase;
import edu.aku.ehs.libraries.mpchart.formatter.IAxisValueFormatter;

public class MyAxisValueFormatter implements IAxisValueFormatter
{

    private DecimalFormat mFormat;

    public MyAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }
}