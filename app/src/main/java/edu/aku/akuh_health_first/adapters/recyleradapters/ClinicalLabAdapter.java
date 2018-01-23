package edu.aku.akuh_health_first.adapters.recyleradapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.akuh_health_first.R;
import edu.aku.akuh_health_first.callbacks.OnItemClickListener;
import edu.aku.akuh_health_first.models.LaboratoryModel;
import edu.aku.akuh_health_first.views.AnyTextView;

/**
 */
public class ClinicalLabAdapter extends RecyclerView.Adapter<ClinicalLabAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;

    private Activity activity;
    private ArrayList<LaboratoryModel> arrData;

    public ClinicalLabAdapter(Activity activity, ArrayList<LaboratoryModel> arrayList, OnItemClickListener onItemClickListener) {
        this.arrData = arrayList;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_clinical_lab, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        final LaboratoryModel model = arrData.get(holder.getAdapterPosition());

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final LaboratoryModel neurophysiology) {
        holder.btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(holder.getAdapterPosition(), neurophysiology);
            }
        });
    }


    public void addItem(ArrayList<LaboratoryModel> homeCategories) {
        this.arrData = homeCategories;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSpecimenNumber)
        AnyTextView txtSpecimenNumber;
        @BindView(R.id.txtRequestNumber)
        AnyTextView txtRequestNumber;
        @BindView(R.id.txtEnteredDateTime)
        AnyTextView txtEnteredDateTime;
        @BindView(R.id.txtVisitDateTime)
        AnyTextView txtVisitDateTime;
        @BindView(R.id.txtVisitIDandType)
        AnyTextView txtVisitIDandType;
        @BindView(R.id.txtVisitLocationID)
        AnyTextView txtVisitLocationID;
        @BindView(R.id.cardView2)
        CardView cardView2;
        @BindView(R.id.btnShowDetail)
        Button btnShowDetail;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
