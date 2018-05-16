package edu.aku.family_hifazat.adapters.recyleradapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.family_hifazat.R;
import edu.aku.family_hifazat.models.CardioModel;
import edu.aku.family_hifazat.widget.AnyTextView;

/**
 */
public class CardioAdapter extends RecyclerView.Adapter<CardioAdapter.ViewHolder> {


    private final AdapterView.OnItemClickListener onItemClick;


    private Activity activity;
    private ArrayList<CardioModel> arrListCardioModel;

    public CardioAdapter(Activity activity, ArrayList<CardioModel> userList, AdapterView.OnItemClickListener onItemClickListener) {
        this.arrListCardioModel = userList;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_cps_nps_endo_summary_, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        final CardioModel model = arrListCardioModel.get(holder.getAdapterPosition());
        holder.txtDateTime.setText(model.getRequestServiceDateTime());
        holder.txtName.setText(model.getService());
        holder.RlReport.setVisibility(View.VISIBLE);
        holder.RlGraph.setVisibility(View.VISIBLE);
        holder.txtStatusType.setVisibility(View.VISIBLE);
        holder.imgStatus.setVisibility(View.VISIBLE);
        holder.txtDrName.setText(model.getLastFileUser());

        setEnability(holder, model);
        setListener(holder, model);

        holder.txtStatusType.setText(model.getStatus());

        if (model.getStatus().equalsIgnoreCase("Finalized") || model.getStatus().equalsIgnoreCase("Completed")) {
            holder.RlReport.setVisibility(View.VISIBLE);
            holder.RlGraph.setVisibility(View.VISIBLE);

            setViews(holder, activity.getResources().getColor(R.color.base_green),
                    R.drawable.rounded_box_filled_base_green, R.drawable.cardiopulmonary_green,
                    R.drawable.b_cardiopulmonary_transparent);

        } else {
            holder.RlGraph.setEnabled(false);
            holder.RlGraph.setAlpha(.15f);
            holder.RlReport.setEnabled(false);
            holder.RlReport.setAlpha(.15f);

            setViews(holder, activity.getResources().getColor(R.color.base_amber),
                    R.drawable.rounded_box_filled_base_amber, R.drawable.cardiopulmonary_amber,
                    R.drawable.b_cardiopulmonary_transparent);
        }


    }

    private void setViews(ViewHolder holder, int color, int backgroundResource, int circular_background, int img_transparent) {
        holder.cardView2.setCardBackgroundColor(color);
        holder.imgIcon.setImageResource(circular_background);
        holder.imgTransparent.setImageResource(img_transparent);
        holder.imgIcon.setColorFilter(color);
        holder.txtStatusType.setTextColor(color);
        holder.imgStatus.setColorFilter(color);
    }

    private void setEnability(ViewHolder holder, CardioModel cardioModel) {

        if (cardioModel.getGraphAvailable().equalsIgnoreCase("false")) {
            holder.RlGraph.setEnabled(false);

            holder.RlGraph.setAlpha(.15f);
        } else {
            holder.RlGraph.setEnabled(true);

            holder.RlGraph.setAlpha(1f);
        }

        if (cardioModel.getReportable().equalsIgnoreCase("false")) {
            holder.RlReport.setEnabled(false);
            holder.RlReport.setAlpha(.15f);

        } else {
            holder.RlReport.setEnabled(true);
            holder.RlReport.setAlpha(1f);
        }
    }

    private void setListener(final ViewHolder holder, final CardioModel cardioModel) {
        holder.RlGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(null, v, holder.getAdapterPosition(), 0);
            }
        });

        holder.RlReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(null, v, holder.getAdapterPosition(), 0);
            }
        });

    }

    public CardioModel getItem(int position) {
        return arrListCardioModel.get(position);
    }

    public void addItem(ArrayList<CardioModel> homeCategories) {
        this.arrListCardioModel = homeCategories;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrListCardioModel.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDateTime)
        AnyTextView txtDateTime;
        @BindView(R.id.txtStatusType)
        AnyTextView txtStatusType;
        @BindView(R.id.imgIcon)
        ImageView imgIcon;
        @BindView(R.id.imgStatus)
        ImageView imgStatus;
        @BindView(R.id.imgTransparent)
        ImageView imgTransparent;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtDrName)
        AnyTextView txtDrName;
        @BindView(R.id.contListItem)
        LinearLayout contListItem;
        @BindView(R.id.btnReportColorCode1)
        AnyTextView btnReportColorCode1;

        @BindView(R.id.btnGraphColorCode)
        AnyTextView btnGraphColorCode;
        @BindView(R.id.RlReport)
        RelativeLayout RlReport;
        @BindView(R.id.RlGraph)
        RelativeLayout RlGraph;
        @BindView(R.id.frameColorCode)
        FrameLayout frameColorCode;
        @BindView(R.id.cardView2)
        CardView cardView2;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
