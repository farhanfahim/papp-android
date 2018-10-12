package edu.aku.ehs.libraries.mpchart.interfaces.dataprovider;

import edu.aku.ehs.libraries.mpchart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
