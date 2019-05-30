package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class SessionPayoutHistoryAdapter extends RecyclerView.Adapter<SessionPayoutHistoryAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;



    private Context activity;
    private List<SpinnerModel> arrData;

    public SessionPayoutHistoryAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_session_payout_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);

        if (i == 0 || i == 3) {
            holder.txtPaymentStatus.setText("Pending");
            holder.txtPaymentStatus.setTextColor(activity.getResources().getColor(R.color.base_reddish));

        } else {
            holder.txtPaymentStatus.setText("Received");
            holder.txtPaymentStatus.setTextColor(activity.getResources().getColor(R.color.base_green));

        }

        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, view, null));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtPaymentStatus)
        AnyTextView txtPaymentStatus;
        @BindView(R.id.txtSessionBy)
        AnyTextView txtSessionBy;
        @BindView(R.id.txtSessionOn)
        AnyTextView txtSessionOn;
        @BindView(R.id.txtLocation)
        AnyTextView txtLocation;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
