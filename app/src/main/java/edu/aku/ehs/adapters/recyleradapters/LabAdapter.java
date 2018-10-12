package edu.aku.ehs.adapters.recyleradapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.helperclasses.Spanny;
import edu.aku.ehs.helperclasses.StringHelper;
import edu.aku.ehs.models.EmpLabs;
import edu.aku.ehs.widget.AnyTextView;
import edu.aku.ehs.widget.CustomTypefaceSpan;

/**
 */
public class LabAdapter extends RecyclerView.Adapter<LabAdapter.ViewHolder> {

    Typeface bold;

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<EmpLabs> arrData;

    public LabAdapter(Context activity, List<EmpLabs> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        bold = Typeface.createFromAsset(activity.getAssets(), "fonts/HelveticaNeue Medium.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_lab, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        EmpLabs model = arrData.get(i);
        holder.txtLabName.setText(model.getTestID());
        holder.txtRange.setText(model.getNormalRangeFormatted());
        if (StringHelper.checkNotNullAndNotEmpty(model.getDisplaySpecimenDate())) {
            holder.txtDate.setText("Speciment Date: " + model.getDisplaySpecimenDate());
        } else {
            holder.txtDate.setText(model.getDisplaySpecimenDate());

        }
        holder.txtUnit.setText(model.getUnit());

        CustomTypefaceSpan customTypefaceSpan;

        /**
         *
         * Colors of Result accordingly
         * Panic High ---- > Red color with bold font
         * High       ---- > Red
         * Panic low  ---- > Blue color with bold font
         * Low        ---- > Blue
         **/

        /**
         *
         * Colors of Result accordingly
         * Panic High ---- > Red color with bold font
         * High       ---- > Red
         * Panic low  ---- > Blue color with bold font
         * Low        ---- > Blue
         **/
        if (model.getAbnormalFlag() == null || model.getAbnormalFlag().isEmpty()) {
            holder.txtResult.setTextColor(activity.getResources().getColor(R.color.txtBlue));
            holder.txtState.setTextColor(activity.getResources().getColor(R.color.txtBlue));
            holder.txtState.setBackground(activity.getResources().getDrawable(R.drawable.rounded_stroke_white));
            customTypefaceSpan = new CustomTypefaceSpan(bold);
        } else if (model.getAbnormalFlag().equalsIgnoreCase("Low")) {
            customTypefaceSpan = new CustomTypefaceSpan(bold);
            holder.txtResult.setTextColor(activity.getResources().getColor(R.color.panic_blue));
            holder.txtState.setTextColor(activity.getResources().getColor(R.color.panic_blue));
            holder.txtState.setBackground(activity.getResources().getDrawable(R.drawable.rounded_stroke_blue));

        } else if (model.getAbnormalFlag().equalsIgnoreCase("High")) {
            customTypefaceSpan = new CustomTypefaceSpan(bold);
            holder.txtState.setTextColor(activity.getResources().getColor(R.color.base_reddish));
            holder.txtResult.setTextColor(activity.getResources().getColor(R.color.base_reddish));
            holder.txtState.setBackground(activity.getResources().getDrawable(R.drawable.rounded_stroke_red));

        } else if (model.getAbnormalFlag().equalsIgnoreCase("Panic High") || model.getAbnormalFlag().equalsIgnoreCase("ph")) {
            holder.txtState.setTextColor(activity.getResources().getColor(R.color.base_reddish));
            holder.txtResult.setTextColor(activity.getResources().getColor(R.color.base_reddish));
            holder.txtState.setBackground(activity.getResources().getDrawable(R.drawable.rounded_stroke_red));

            model.setAbnormalFlag("High");
            customTypefaceSpan = new CustomTypefaceSpan(bold);
        } else {
            holder.txtState.setTextColor(activity.getResources().getColor(R.color.panic_blue));
            holder.txtResult.setTextColor(activity.getResources().getColor(R.color.panic_blue));
            holder.txtState.setBackground(activity.getResources().getDrawable(R.drawable.rounded_stroke_blue));
            customTypefaceSpan = new CustomTypefaceSpan(bold);
            model.setAbnormalFlag("Low");
        }

        if (model.getResult() != null) {
            Spanny resultSpanny = new Spanny(model.getResult(), customTypefaceSpan);
            holder.txtResult.setText(resultSpanny);
        } else {
            holder.txtResult.setText(model.getResult());
        }
        if (model.getAbnormalFlag() != null) {
            Spanny stateSpanny = new Spanny(model.getAbnormalFlag(), customTypefaceSpan);
            holder.txtState.setText(stateSpanny);
        } else {
            holder.txtState.setText(model.getAbnormalFlag());
        }


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final EmpLabs model) {
//        holder.contListItem.setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtLabName)
        AnyTextView txtLabName;
        @BindView(R.id.txtRange)
        AnyTextView txtRange;
        @BindView(R.id.txtResult)
        AnyTextView txtResult;
        @BindView(R.id.txtUnit)
        AnyTextView txtUnit;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtState)
        AnyTextView txtState;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
