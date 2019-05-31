package com.tekrevol.papp.adapters.recyleradapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.papp.R;
import com.tekrevol.papp.callbacks.OnItemClickListener;
import com.tekrevol.papp.libraries.imageloader.ImageLoaderHelper;
import com.tekrevol.papp.models.general.SpinnerModel;
import com.tekrevol.papp.models.receiving_model.TaskReceivingModel;
import com.tekrevol.papp.widget.AnyTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final OnItemClickListener onItemClick;
    private final int status;

    private View itemView = null;


    private Context activity;
    private List<TaskReceivingModel> arrData;


    public TaskAdapter(Context activity, List<TaskReceivingModel> arrData, int status, OnItemClickListener onItemClickListener) {
        this.arrData = arrData;
        this.activity = activity;
        this.onItemClick = onItemClickListener;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(activity)
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {
        TaskReceivingModel model = arrData.get(i);

        holder.txtTitle.setText(model.getTitle());
        holder.txtDesc.setText("Reward: " + model.getRewardPoints() + " points");
        ImageLoaderHelper.loadImageWithAnimationsByPath(holder.imgProfile, model.getIcon(), false);


        setListener(holder, model);
    }

    private void setListener(final ViewHolder holder, final TaskReceivingModel model) {
        holder.contParentLayout.
                setOnClickListener(view -> onItemClick.onItemClick(holder.getAdapterPosition(), model, itemView, status));
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
