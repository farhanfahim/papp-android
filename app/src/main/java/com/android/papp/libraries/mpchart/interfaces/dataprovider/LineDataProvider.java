package com.android.papp.libraries.mpchart.interfaces.dataprovider;

import com.android.papp.libraries.mpchart.components.YAxis;
import com.android.papp.libraries.mpchart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
