package edu.aku.ehs.fragments;

import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.libraries.mpchart.charts.BarChart;
import edu.aku.ehs.libraries.mpchart.components.Legend;
import edu.aku.ehs.libraries.mpchart.components.XAxis;
import edu.aku.ehs.libraries.mpchart.components.YAxis;
import edu.aku.ehs.libraries.mpchart.custom.CustomStringFormatter;
import edu.aku.ehs.libraries.mpchart.custom.XYMarkerView;
import edu.aku.ehs.libraries.mpchart.data.BarData;
import edu.aku.ehs.libraries.mpchart.data.BarDataSet;
import edu.aku.ehs.libraries.mpchart.data.BarEntry;
import edu.aku.ehs.libraries.mpchart.data.Entry;
import edu.aku.ehs.libraries.mpchart.formatter.IAxisValueFormatter;
import edu.aku.ehs.libraries.mpchart.highlight.Highlight;
import edu.aku.ehs.libraries.mpchart.interfaces.datasets.IBarDataSet;
import edu.aku.ehs.libraries.mpchart.listener.OnChartValueSelectedListener;
import edu.aku.ehs.libraries.mpchart.utils.MPPointF;
import edu.aku.ehs.widget.TitleBar;

import static edu.aku.ehs.libraries.mpchart.utils.ColorTemplate.CUSTOM_COLORS;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class StatsFragment extends BaseFragment implements OnChartValueSelectedListener {


    //    @BindView(R.id.bar_chart_vertical)
//    BarChart barChartVertical;
    Unbinder unbinder;
    @BindView(R.id.chart1)
    BarChart mChart;


//    protected Typeface mTfRegular;
//    protected Typeface mTfLight;


    public static StatsFragment newInstance() {

        Bundle args = new Bundle();

        StatsFragment fragment = new StatsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getDrawerLockMode() {
        return 0;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_statistics;
    }

    @Override
    public void setTitlebar(TitleBar titleBar) {
        titleBar.resetViews();
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitle("Stats");
        titleBar.showHome(getBaseActivity());
        titleBar.showBackButton(getBaseActivity());
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mTfRegular = Typeface.createFromAsset(getBaseActivity().getAssets(), getString(R.string.font_medium));
//        mTfLight = Typeface.createFromAsset(getBaseActivity().getAssets(), getString(R.string.font_regular));


//        //Add single bar
//        BarChartModel barChartModel = new BarChartModel();
//        barChartModel.setBarValue(50);
//        barChartModel.setBarColor(Color.parseColor("#9C27B0"));
//        barChartModel.setBarTag(null); //You can set your own tag to bar model
//        barChartModel.setBarText("50");
//
//        barChartVertical.addBar(barChartModel);
//
////Add mutliple bar at once as list;
//        List<BarChartModel> barChartModelList = new ArrayList<>();
//
////populate bar array list and add to barchart as a list.
//        barChartVertical.addBar(barChartModelList);

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setDrawGridBackground(false);
//        mChart.setFitBars(true);

        IAxisValueFormatter xAxisFormatter = new CustomStringFormatter(getContext());

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setValueFormatter(xAxisFormatter);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();

//        YAxis leftAxis = mChart.getAxisLeft();
////        leftAxis.setTypeface(mTfLight);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
////        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);
        l.setFormSize(6f);
        l.setTextSize(10f);
        //        l.setExtra(ColorTemplate.CUSTOM_COLORS, getContext().getResources().getStringArray(R.array.stats_xAxis));

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData(5, 100);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Session Stats");

            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

//            int startColor1 = CUSTOM_COLORS[0];
//            int startColor2 = CUSTOM_COLORS[1];
//            int startColor3 = CUSTOM_COLORS[2];
//            int startColor4 = CUSTOM_COLORS[3];
//            int startColor5 = CUSTOM_COLORS[4];

            int startColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_green_light);
            int startColor4 = ContextCompat.getColor(getContext(), R.color.pastel_cyan);
            int startColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);

//            List<GradientColor> gradientColors = new ArrayList<>();
//            gradientColors.add(new GradientColor(startColor1, endColor1));
//            gradientColors.add(new GradientColor(startColor2, endColor2));
//            gradientColors.add(new GradientColor(startColor3, endColor3));
//            gradientColors.add(new GradientColor(startColor4, endColor4));
//            gradientColors.add(new GradientColor(startColor5, endColor5));
//
//            set1.setGradientColors(gradientColors);

            List<Integer> colorList = new ArrayList<>();
            colorList.add(startColor1);
            colorList.add(startColor2);
            colorList.add(startColor3);
            colorList.add(startColor4);
            colorList.add(startColor5);

            set1.setColors(colorList);


            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(14f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }


}
