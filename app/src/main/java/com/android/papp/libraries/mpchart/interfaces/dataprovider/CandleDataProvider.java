package com.android.papp.libraries.mpchart.interfaces.dataprovider;

import com.android.papp.libraries.mpchart.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
