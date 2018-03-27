package edu.aku.akuh_health_first.adapters.recyleradapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
        import android.app.Activity;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import java.util.ArrayList;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import edu.aku.akuh_health_first.R;
        import edu.aku.akuh_health_first.callbacks.OnItemClickListener;
        import edu.aku.akuh_health_first.models.LstLaboratorySpecimenResults;
        import edu.aku.akuh_health_first.widget.AnyTextView;

/**
 */
public class ClinicalLabDetailAdapterv1 extends RecyclerView.Adapter<ClinicalLabDetailAdapterv1.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private Activity activity;
    private ArrayList<LstLaboratorySpecimenResults> arrData;

    public ClinicalLabDetailAdapterv1(Activity activity, ArrayList<LstLaboratorySpecimenResults> arrayList, OnItemClickListener onItemClickListener) {
        this.arrData = arrayList;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_clinical_lab_detail_v1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        final LstLaboratorySpecimenResults model = arrData.get(holder.getAdapterPosition());

        holder.txtReportName.setText(model.getReportName());
        holder.txtNormalRangeFormatted.setText(model.getNormalRangeFormatted());
        holder.txtResult.setText(model.getResult() + " " + model.getUnit());

        holder.txtResultPrevious1.setText(model.getPrevResult1());
        holder.txtResultPrevious2.setText(model.getPrevResult2());
        holder.txtResultPrevious3.setText(model.getPrevResult3());

        holder.txtResultPrevious3Date.setText(model.getPrevResult3Dttm());
        holder.txtResultPrevious1Date.setText(model.getPrevResult1Dttm());
        holder.txtResultPrevious2Date.setText(model.getPrevResult2Dttm());

//        setListener(holder, model);


    }

    private void setListener(final ViewHolder holder, final LstLaboratorySpecimenResults neurophysiology) {
        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(holder.getAdapterPosition(), neurophysiology);
            }
        });
    }


    public void addItem(ArrayList<LstLaboratorySpecimenResults> homeCategories) {
        this.arrData = homeCategories;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgIcon)
        ImageView imgIcon;
        @BindView(R.id.txtReportName)
        AnyTextView txtReportName;
        @BindView(R.id.txtNormalRangeFormatted)
        AnyTextView txtNormalRangeFormatted;
        @BindView(R.id.txtResult)
        AnyTextView txtResult;
        @BindView(R.id.txtResultPrevious3)
        AnyTextView txtResultPrevious3;
        @BindView(R.id.txtResultPrevious3Date)
        AnyTextView txtResultPrevious3Date;
        @BindView(R.id.txtResultPrevious1)
        AnyTextView txtResultPrevious1;
        @BindView(R.id.txtResultPrevious1Date)
        AnyTextView txtResultPrevious1Date;
        @BindView(R.id.txtResultPrevious2)
        AnyTextView txtResultPrevious2;
        @BindView(R.id.txtResultPrevious2Date)
        AnyTextView txtResultPrevious2Date;
        @BindView(R.id.cardView2)
        CardView cardView2;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
