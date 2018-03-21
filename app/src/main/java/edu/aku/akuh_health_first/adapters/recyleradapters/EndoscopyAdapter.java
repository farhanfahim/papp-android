package edu.aku.akuh_health_first.adapters.recyleradapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.aku.akuh_health_first.R;
import edu.aku.akuh_health_first.callbacks.OnItemClickListener;
import edu.aku.akuh_health_first.models.EndoscopyModel;
import edu.aku.akuh_health_first.widget.AnyTextView;

/**
 */
public class EndoscopyAdapter extends RecyclerView.Adapter<EndoscopyAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;



    private Activity activity;
    private ArrayList<EndoscopyModel> arrData;

    public EndoscopyAdapter(Activity activity, ArrayList<EndoscopyModel> arrayList, OnItemClickListener onItemClickListener) {
        this.arrData = arrayList;
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

        final EndoscopyModel model = arrData.get(holder.getAdapterPosition());


        holder.txtName.setText(model.getProcedures());
        holder.txtDateTime.setText(model.getProceduredatetime());
        holder.txtDrName.setText("Dr." + model.getPhysicianname());
        holder.txtStatusType.setVisibility(View.INVISIBLE);
        setListener(holder, model);
        holder.RlReport.setVisibility(View.VISIBLE);


        setListener(holder, model);
        holder.txtStatusType.setText("Finalised");
        setViews(holder, activity.getResources().getColor(R.color.base_green), R.drawable.rounded_box_filled_base_green, R.drawable.a_endoscopy_green);


    }

    private void setViews(ViewHolder holder, int color, int backgroundResource, int circular_background) {
        holder.frameColorCode.setBackgroundColor(color);
        holder.txtStatusType.setBackgroundResource(backgroundResource);
        holder.imgIcon.setImageResource(circular_background);
    }

    private void setListener(final ViewHolder holder, final EndoscopyModel endoscopyModel) {
        holder.RlReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(holder.getAdapterPosition(), endoscopyModel);
            }
        });
    }


    public void addItem(ArrayList<EndoscopyModel> homeCategories) {
        this.arrData = homeCategories;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtDateTime)
        AnyTextView txtDateTime;
        @BindView(R.id.txtStatusType)
        AnyTextView txtStatusType;
        @BindView(R.id.imgIcon)
        CircleImageView imgIcon;
        @BindView(R.id.txtName)
        AnyTextView txtName;
        @BindView(R.id.txtDrName)
        AnyTextView txtDrName;
        @BindView(R.id.contListItem)
        LinearLayout contListItem;
        @BindView(R.id.btnReportColorCode1)
        AnyTextView btnReportColorCode1;
        @BindView(R.id.RlReport)
        RelativeLayout RlReport;
        @BindView(R.id.btnGraphColorCode)
        AnyTextView btnGraphColorCode;
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
