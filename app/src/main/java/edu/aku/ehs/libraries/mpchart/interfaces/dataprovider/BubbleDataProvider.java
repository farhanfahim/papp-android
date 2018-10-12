package edu.aku.ehs.libraries.mpchart.interfaces.dataprovider;

import edu.aku.ehs.libraries.mpchart.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
