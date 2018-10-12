package edu.aku.ehs.libraries.mpchart.interfaces.dataprovider;

import edu.aku.ehs.libraries.mpchart.components.YAxis.AxisDependency;
import edu.aku.ehs.libraries.mpchart.data.BarLineScatterCandleBubbleData;
import edu.aku.ehs.libraries.mpchart.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
