package edu.aku.ehs.libraries.mpchart.interfaces.dataprovider;

import edu.aku.ehs.libraries.mpchart.data.CombinedData;

/**
 * Created by philipp on 11/06/16.
 */
public interface CombinedDataProvider extends LineDataProvider, BarDataProvider, BubbleDataProvider, CandleDataProvider, ScatterDataProvider {

    CombinedData getCombinedData();
}
