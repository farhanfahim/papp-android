package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tekrevol.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.managers.DateManager;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.NotificationModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private Context activity;
    private List<NotificationModel> arrData;

    public NotificationAdapter(Context activity, List<NotificationModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;
        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_notifications, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        NotificationModel model = arrData.get(i);

        holder.txtNotification.setText(model.getMessage());
        holder.txtDate.setText(DateManager.convertToUserTimeZone(model.getCreatedAt()));
        holder.txtTime.setVisibility(View.GONE);
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final NotificationModel model) {

    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtNotification)
        AnyTextView txtNotification;
        @BindView(R.id.txtDate)
        AnyTextView txtDate;
        @BindView(R.id.txtTime)
        AnyTextView txtTime;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
