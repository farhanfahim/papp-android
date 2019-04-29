package com.android.papp.libraries.mpchart.highlight;

import com.android.papp.libraries.mpchart.interfaces.datasets.IPieDataSet;
import com.android.papp.libraries.mpchart.charts.PieChart;
import com.android.papp.libraries.mpchart.data.Entry;

/**
 * Created by philipp on 12/06/16.
 */
public class PieHighlighter extends PieRadarHighlighter<PieChart> {

    public PieHighlighter(PieChart chart) {
        super(chart);
    }

    @Override
    protected Highlight getClosestHighlight(int index, float x, float y) {

        IPieDataSet set = mChart.getData().getDataSet();

        final Entry entry = set.getEntryForIndex(index);

        return new Highlight(index, entry.getY(), x, y, 0, set.getAxisDependency());
    }
}
