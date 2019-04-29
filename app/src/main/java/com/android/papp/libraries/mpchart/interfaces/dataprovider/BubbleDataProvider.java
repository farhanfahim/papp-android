package com.android.papp.libraries.mpchart.interfaces.dataprovider;

import com.android.papp.libraries.mpchart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
