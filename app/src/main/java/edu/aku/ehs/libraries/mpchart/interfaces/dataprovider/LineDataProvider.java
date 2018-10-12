package edu.aku.ehs.libraries.mpchart.interfaces.dataprovider;

import edu.aku.ehs.libraries.mpchart.components.YAxis;
import edu.aku.ehs.libraries.mpchart.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
