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
public class OnGoingTaskAdapter extends RecyclerView.Adapter<OnGoingTaskAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;


    private View itemView = null;


    private Context activity;
    private List<SpinnerModel> arrData;

    public OnGoingTaskAdapter(Context activity, List<SpinnerModel> arrData, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_task_ongoing, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        SpinnerModel model = arrData.get(i);


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final SpinnerModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, OnGoingTaskAdapter.class.getSimpleName()));
    }


    @Override
    public int getItemCount() {
        return arrData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgTask)
        CircleImageView imgTask;
        @BindView(R.id.txtDesc)
        AnyTextView txtDesc;
        @BindView(R.id.txtTitle)
        AnyTextView txtTitle;
        @BindView(R.id.txtDuration)
        AnyTextView txtDuration;
        @BindView(R.id.contParentLayout)
        LinearLayout contParentLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
