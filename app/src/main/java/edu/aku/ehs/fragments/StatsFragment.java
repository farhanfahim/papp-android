package edu.aku.ehs.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.aku.ehs.R;
import edu.aku.ehs.fragments.abstracts.BaseFragment;
import edu.aku.ehs.libraries.mpchart.animation.Easing;
import edu.aku.ehs.libraries.mpchart.charts.BarChart;
import edu.aku.ehs.libraries.mpchart.charts.PieChart;
import edu.aku.ehs.libraries.mpchart.components.Legend;
import edu.aku.ehs.libraries.mpchart.components.XAxis;
import edu.aku.ehs.libraries.mpchart.components.YAxis;
import edu.aku.ehs.libraries.mpchart.custom.CustomStringFormatter;
import edu.aku.ehs.libraries.mpchart.custom.XYMarkerView;
import edu.aku.ehs.libraries.mpchart.data.BarData;
import edu.aku.ehs.libraries.mpchart.data.BarDataSet;
import edu.aku.ehs.libraries.mpchart.data.BarEntry;
import edu.aku.ehs.libraries.mpchart.data.Entry;
import edu.aku.ehs.libraries.mpchart.data.PieData;
import edu.aku.ehs.libraries.mpchart.data.PieDataSet;
import edu.aku.ehs.libraries.mpchart.data.PieEntry;
import edu.aku.ehs.libraries.mpchart.formatter.IAxisValueFormatter;
import edu.aku.ehs.libraries.mpchart.formatter.PercentFormatter;
import edu.aku.ehs.libraries.mpchart.highlight.Highlight;
import edu.aku.ehs.libraries.mpchart.interfaces.datasets.IBarDataSet;
import edu.aku.ehs.libraries.mpchart.listener.OnChartValueSelectedListener;
import edu.aku.ehs.libraries.mpchart.utils.ColorTemplate;
import edu.aku.ehs.libraries.mpchart.utils.MPPointF;
import edu.aku.ehs.models.ListDivisionCount;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.models.StatsModel;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.TitleBar;

/**
 * Created by hamza.ahmed on 7/19/2018.
 */

public class StatsFragment extends BaseFragment implements OnChartValueSelectedListener {


    //    @BindView(R.id.bar_chart_vertical)
//    BarChart barChartVertical;
    Unbinder unbinder;
    @BindView(R.id.chart1)
    BarChart mChart1;
    @BindView(R.id.chart2)
    PieChart mChart2;
    @BindView(R.id.txtSessionName)
    AnyTextView txtSessionName;
    @BindView(R.id.imgStatus)
    ImageView imgStatus;
    @BindView(R.id.txtStatus)
    AnyTextView txtStatus;
    @BindView(R.id.txtStartDate)
    AnyTextView txtStartDate;
    @BindView(R.id.txtEndDate)
    AnyTextView txtEndDate;
    @BindView(R.id.contListItem)
    RoundKornerLinearLayout contListItem;
    @BindView(R.id.contParent)
    RoundKornerLinearLayout contParent;
    private SessionModel sessionModel;
    private StatsModel statsModel;


    //    protected Typeface mTfRegular;
    protected Typeface tf;


    public static StatsFragment newInstance(SessionModel sessionModel, StatsModel statsModel) {

        Bundle args = new Bundle();

        StatsFragment fragment = new StatsFragment();
        fragment.sessionModel = sessionModel;
        fragment.statsModel = statsModel;
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

        txtSessionName.setText(sessionModel.getDescription());
        txtStartDate.setText("Start Date: " + sessionModel.getDisplayStartDate());
        txtEndDate.setText("End Date: " + sessionModel.getDisplayEndDate());
        txtStatus.setText(sessionModel.getStatusId());

        setChart1(statsModel);

        if (statsModel.getListDivisionCount() != null && !statsModel.getListDivisionCount().isEmpty()) {
            setChart2(statsModel);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setChart1(StatsModel statsModel) {
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

        mChart1.setOnChartValueSelectedListener(this);

        mChart1.setDrawBarShadow(false);
        mChart1.setDrawValueAboveBar(true);

        mChart1.getDescription().setEnabled(false);
        mChart1.setExtraOffsets(10f, 10f, 10f, 10f);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart1.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart1.setPinchZoom(true);
        mChart1.setHighlightFullBarEnabled(false);
        mChart1.setDoubleTapToZoomEnabled(false);
        mChart1.setDrawGridBackground(false);
//        mChart1.setFitBars(true);

        IAxisValueFormatter xAxisFormatter = new CustomStringFormatter(getContext());

        XAxis xAxis = mChart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(5);
        xAxis.setTextSize(10f);
        xAxis.setValueFormatter(xAxisFormatter);

//        IAxisValueFormatter custom = new MyAxisValueFormatter();

//        YAxis leftAxis = mChart1.getAxisLeft();
////        leftAxis.setTypeface(mTfLight);
//        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

//        YAxis rightAxis = mChart1.getAxisRight();
//        rightAxis.setDrawGridLines(false);
////        rightAxis.setTypeface(mTfLight);
//        rightAxis.setLabelCount(8, false);
//        rightAxis.setValueFormatter(custom);
//        rightAxis.setSpaceTop(15f);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.LINE);
        l.setFormSize(8f);
        l.setTextSize(15f);
        //        l.setExtra(ColorTemplate.CUSTOM_COLORS, getContext().getResources().getStringArray(R.array.stats_xAxis));

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(mChart1); // For bounds control
        mChart1.setMarker(mv); // Set the marker to the chart

        setData1(statsModel);
    }

    private void setChart2(StatsModel statsModel) {
        mChart2.setUsePercentValues(true);
        mChart2.getDescription().setEnabled(false);
//        mChart2.setExtraOffsets(5, 10, 5, 5);

        mChart2.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.assets_fonts_folder) + "Roboto-Regular_1B.ttf");
//
        mChart2.setCenterTextTypeface(Typeface.createFromAsset(getContext().getAssets(), getContext().getString(R.string.assets_fonts_folder) + "Roboto-Regular_1B.ttf"));
//        mChart2.setCenterText(generateCenterSpannableText());
        mChart2.setCenterText("Total Employees: " + statsModel.getTotalEmployee());
        mChart2.setCenterTextSize(14f);

        mChart2.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        mChart2.setDrawHoleEnabled(true);
        mChart2.setHoleColor(Color.WHITE);

        mChart2.setTransparentCircleColor(Color.WHITE);
        mChart2.setTransparentCircleAlpha(255); //110

        mChart2.setHoleRadius(58f);  //58
        mChart2.setTransparentCircleRadius(55f);  //61
        //        mChart2.setCenterTextRadiusPercent(10f);

        mChart2.setDrawCenterText(true);

        mChart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart2.setRotationEnabled(true);
        mChart2.setHighlightPerTapEnabled(true);

        // mChart2.setUnit(" €");
        // mChart2.setDrawUnitsInChart(true);

        // add a selection listener
        mChart2.setOnChartValueSelectedListener(this);

        setData2(statsModel);

        mChart2.animateY(1400, Easing.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setFormSize(10f);
        l.setTextSize(10f);
        l.setEnabled(false);
    }

    private void setData1(StatsModel statsModel) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, statsModel.getTotalEmployee()));
        yVals1.add(new BarEntry(1, statsModel.getCount_MetabolicSyndrome()));
        yVals1.add(new BarEntry(2, statsModel.getCount_Referred()));
        yVals1.add(new BarEntry(3, statsModel.getCount_HepC()));
        yVals1.add(new BarEntry(4, statsModel.getCount_Anemia()));
        yVals1.add(new BarEntry(5, statsModel.getCount_PsychosocialHistory()));


        BarDataSet set1;

        if (mChart1.getData() != null &&
                mChart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart1.getData().notifyDataChanged();
            mChart1.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, sessionModel.getDescription() + " Stats");

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

            mChart1.setData(data);
            mChart1.notifyDataSetChanged();
            mChart1.getData().notifyDataChanged();
        }
    }


    private void setData2(StatsModel statsModel) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        for (ListDivisionCount listDivisionCount : statsModel.getListDivisionCount()) {
            entries.add(new PieEntry(listDivisionCount.getCount_Employees(), listDivisionCount.getDivisionName() + " (" + listDivisionCount.getCount_Employees() + ")"));

        }

        PieDataSet dataSet = new PieDataSet(entries, sessionModel.getDescription());
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);


        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);


        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setUsingSliceColorAsValueLineColor(true);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart2.setData(data);

        // undo all highlights
        mChart2.highlightValues(null);

        mChart2.invalidate();
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Session: " + sessionModel.getDescription() + " Statistics");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    protected RectF mOnValueSelectedRectF = new RectF();

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        if (e instanceof BarEntry) {
            RectF bounds = mOnValueSelectedRectF;
            mChart1.getBarBounds((BarEntry) e, bounds);
            MPPointF position = mChart1.getPosition(e, YAxis.AxisDependency.LEFT);

            MPPointF.recycleInstance(position);
        } else {
            Log.i("VAL SELECTED",
                    "Value: " + e.getY() + ", xIndex: " + e.getX()
                            + ", DataSet index: " + h.getDataSetIndex());
        }


    }

    @Override
    public void onNothingSelected() {
    }


}
