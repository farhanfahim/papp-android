package com.android.papp.libraries.mpchart.interfaces.dataprovider;

import com.android.papp.libraries.mpchart.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
