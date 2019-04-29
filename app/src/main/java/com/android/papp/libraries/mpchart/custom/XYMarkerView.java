package com.android.papp.libraries.mpchart.custom;

import android.content.Context;
import android.widget.TextView;


import com.android.papp.libraries.mpchart.components.MarkerView;
import com.android.papp.libraries.mpchart.formatter.IAxisValueFormatter;
import com.android.papp.libraries.mpchart.highlight.Highlight;

import java.text.DecimalFormat;

import com.android.papp.R;

import com.android.papp.libraries.mpchart.data.Entry;
import com.android.papp.libraries.mpchart.utils.MPPointF;

public class XYMarkerView extends MarkerView {

    private TextView tvContent;
    private IAxisValueFormatter xAxisValueFormatter;

    private DecimalFormat format;

    public XYMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);

        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText("x: " + xAxisValueFormatter.getFormattedValue(e.getX(), null) + ", y: " + format.format(e.getY()));

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
