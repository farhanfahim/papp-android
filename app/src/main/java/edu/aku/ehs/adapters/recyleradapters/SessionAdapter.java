package edu.aku.ehs.adapters.recyleradapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;

import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.aku.ehs.R;
import edu.aku.ehs.activities.BaseActivity;
import edu.aku.ehs.callbacks.OnItemClickListener;
import edu.aku.ehs.enums.SessionStatus;
import edu.aku.ehs.models.SessionModel;
import edu.aku.ehs.widget.AnyTextView;
import info.hoang8f.widget.FButton;

/**
 */
public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {


    private final OnItemClickListener onItemClick;



    private BaseActivity activity;
    private ArrayList<SessionModel> arrData;
    private ArrayList<SessionModel> filteredData;
    private Filter mFilter = new ItemFilter();

    public SessionAdapter(BaseActivity activity, ArrayList<SessionModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.filteredData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_session, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SessionModel model = filteredData.get(i);
//        holder.txtSessionName.setText(model.getSessionId() + " - " + model.getDescription());
        holder.txtSessionName.setText(model.getDescription());
        holder.txtStartDate.setText("Start Date: " + model.getDisplayStartDate());
        holder.txtEndDate.setText("End Date: " + model.getDisplayEndDate());
        holder.txtStatus.setText(model.getStatusId());
        holder.txtEnrolledNumber.setText(model.getCountEnrolled() + "");
        holder.txtInProgressNumber.setText(model.getCountInprogress() + "");
        holder.txtAssessedNumber.setText(model.getCountAssessed() + "");
        holder.txtLabTestNumber.setText(model.getCountLabCompleted() + "");


        if (model.getStatusId().equalsIgnoreCase(SessionStatus.CLOSED.canonicalForm())) {
            holder.btnSchedule.setVisibility(View.GONE);
            holder.btnClose.setVisibility(View.GONE);
            holder.imgNext.setVisibility(View.GONE);
//            setColor(holder, activity.getResources().getColor(R.color.c_gray));
            setColor(holder, activity.getResources().getColor(R.color.timeline_out_dark));
            holder.contParent.setAlpha(0.6f);
        } else {
            holder.btnSchedule.setVisibility(View.VISIBLE);
            holder.btnClose.setVisibility(View.VISIBLE);
            holder.imgNext.setVisibility(View.VISIBLE);
            setColor(holder, activity.getResources().getColor(R.color.timeline_out_dark));
            holder.contParent.setAlpha(1f);
        }

        setListener(holder, model);
    }

    private void setColor(ViewHolder holder, int color) {
        holder.contParent.setBackgroundColor(color);
        holder.imgStatus.setColorFilter(color);
        holder.txtStatus.setTextColor(color);
    }

    private void setListener(final ViewHolder holder, final SessionModel model) {
        holder.contListItem.setOnClickListener(v -> onItemClick.onItemClick(holder.getAdapterPosition(), model, v));
        holder.btnClose.setOnClickListener(v -> onItemClick.onItemClick(holder.getAdapterPosition(), model, v));
        holder.btnSchedule.setOnClickListener(v -> onItemClick.onItemClick(holder.getAdapterPosition(), model, v));
    }


    @Override
    public int getItemCount() {
        return filteredData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSessionName)
        AnyTextView txtSessionName;
        @BindView(R.id.btnClose)
        FButton btnClose;
        @BindView(R.id.btnSchedule)
        FButton btnSchedule;
        @BindView(R.id.imgStatus)
        ImageView imgStatus;
        @BindView(R.id.txtStatus)
        AnyTextView txtStatus;
        @BindView(R.id.txtStartDate)
        AnyTextView txtStartDate;
        @BindView(R.id.txtEndDate)
        AnyTextView txtEndDate;
        @BindView(R.id.txtEnrolledNumber)
        AnyTextView txtEnrolledNumber;
        @BindView(R.id.txtLabTestNumber)
        AnyTextView txtLabTestNumber;
        @BindView(R.id.txtInProgressNumber)
        AnyTextView txtInProgressNumber;
        @BindView(R.id.txtAssessedNumber)
        AnyTextView txtAssessedNumber;
        @BindView(R.id.imgNext)
        ImageView imgNext;
        @BindView(R.id.contListItem)
        RoundKornerLinearLayout contListItem;
        @BindView(R.id.contParent)
        RoundKornerLinearLayout contParent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public Filter getFilter() {

        return mFilter;
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<SessionModel> list = arrData;

            int count = list.size();

//            final ArrayList<String> nlist = new ArrayList<String>(count);
            final ArrayList<SessionModel> filterData = new ArrayList<SessionModel>();

            String filterableString1;
            String filterableString2;
            String filterableString3;
            String filterableString4;
            String filterableString5;

            for (int i = 0; i < count; i++) {
                filterableString1 = list.get(i).getSessionId();
                filterableString2 = list.get(i).getDescription();
                filterableString3 = list.get(i).getStatusId();
                filterableString4 = list.get(i).getStartDate();
                filterableString5 = list.get(i).getEndDate();
                if (filterableString1.toLowerCase().contains(filterString)
                        || filterableString2.toLowerCase().contains(filterString)
                        || filterableString3.toLowerCase().contains(filterString)
                        || filterableString4.toLowerCase().contains(filterString)
                        || filterableString5.toLowerCase().contains(filterString)
                        ) {
//                    nlist.add(filterableString);
                    filterData.add(list.get(i));
                }
            }

            results.values = filterData;
            results.count = filterData.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<SessionModel>) results.values;
            notifyDataSetChanged();
        }

    }

    public SessionModel getItem(int position) {
        return filteredData.get(position);
    }


}
