package com.android.papp.adapters.recyleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.android.papp.callbacks.OnItemClickListener;
import com.android.papp.models.SpinnerModel;
import com.android.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;

    private View itemView = null;


    private Context activity;
    private List<SpinnerModel> arrData;
    private boolean isUpcoming = false;

    public TaskAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener, boolean isUpcoming) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.isUpcoming = isUpcoming;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);


        if (isUpcoming) {
            holder.txtTitle.setTextColor(activity.getResources().getColor(R.color.c_light_grey));
            holder.txtDesc.setTextColor(activity.getResources().getColor(R.color.c_light_grey));
            holder.imgProfile.setImageResource(R.drawable.img_background_call_button_grey);
        } else {
            holder.txtTitle.setTextColor(activity.getResources().getColor(R.color.txtBlack));
            holder.txtDesc.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            holder.imgProfile.setImageResource(R.drawable.img_medal2);
        }
        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        if (isUpcoming) {
            holder.contParentLayout.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, TaskAdapter.class.getSimpleName()));

        } else {
            holder.contParentLayout.
                    setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, TaskAdapter.class.getSimpleName() + "completed"));

        }
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProfile)
        CircleImageView imgProfile;
        @BindView(R.id.txtTitle)
        AnyTextView txtTitle;
        @BindView(R.id.txtDesc)
        AnyTextView txtDesc;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
