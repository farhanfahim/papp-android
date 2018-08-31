package edu.aku.ehs.adapters.recyleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.models.EmployeeAssessmentModel;
import edu.aku.ehs.widget.AnyTextView;

/**
 */
public class EmployeeAssessmentAdapter extends RecyclerView.Adapter<EmployeeAssessmentAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;


    private BaseActivity activity;
    private ArrayList<EmployeeAssessmentModel> arrData;

    public EmployeeAssessmentAdapter(BaseActivity activity, ArrayList<EmployeeAssessmentModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_employee_assement, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        EmployeeAssessmentModel model = arrData.get(i);
        holder.txtAssessmentName.setText(model.getAssessmentName());
        holder.txtDate.setText(model.getDate());

        switch (model.getEmployeeAssessmentState()) {
            case INPROGRESS:
                holder.txtStatus.setText("In Progress");
                holder.imgStatus.setColorFilter(activity.getResources().getColor(R.color.pastel_blue));
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.pastel_blue));
                holder.contParent.setBackgroundColor(activity.getResources().getColor(R.color.pastel_blue));
                holder.imgNext.setVisibility(View.VISIBLE);
                break;
            case COMPLETED:
                holder.txtStatus.setText("Completed");
                holder.imgStatus.setColorFilter(activity.getResources().getColor(R.color.pastel_cyan));
                holder.txtStatus.setTextColor(activity.getResources().getColor(R.color.pastel_cyan));
                holder.contParent.setBackgroundColor(activity.getResources().getColor(R.color.pastel_cyan));
                holder.imgNext.setVisibility(View.GONE);
                break;
        }
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final EmployeeAssessmentModel model) {
        holder.contListItem.setOnClickListener(v -> onItemClick.onItemClick(holder.getAdapterPosition(), model, v));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAssessmentName)
        AnyTextView txtAssessmentName;
        @BindView(R.id.imgStatus)
        ImageView imgStatus;
        @BindView(R.id.imgNext)
        ImageView imgNext;
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.contListItem)
        RoundKornerLinearLayout contListItem;
        @BindView(R.id.contParent)
        RoundKornerLinearLayout contParent;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
